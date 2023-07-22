package ru.practicum.ewmservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.dto.EventRequestStatusUpdateRequest;
import ru.practicum.ewmservice.dto.EventRequestStatusUpdateResult;
import ru.practicum.ewmservice.dto.ParticipationRequestDto;
import ru.practicum.ewmservice.enums.StatusEventRequest;
import ru.practicum.ewmservice.enums.StatusParticipation;
import ru.practicum.ewmservice.exceptions.ConflictException;
import ru.practicum.ewmservice.exceptions.ValidationIdException;
import ru.practicum.ewmservice.mapper.RequestMapper;
import ru.practicum.ewmservice.model.Event;
import ru.practicum.ewmservice.model.Request;
import ru.practicum.ewmservice.model.User;
import ru.practicum.ewmservice.repository.EventRepository;
import ru.practicum.ewmservice.repository.RequestRepository;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;
    private final UserService userService;

    public ParticipationRequestDto addRequest(Long userId, Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ValidationIdException("Event with id=" + eventId + " was not found"));

        if (event.getState() != StatusParticipation.PUBLISHED) {
            throw new ConflictException("Нельзя участвовать в неопубликованном событии");
        }

        if (Objects.equals(event.getInitiator().getId(), userId)) {
            throw new ConflictException("Инициатор события не может добавить запрос на участие в своём событии");
        }

        if (event.getConfirmedRequests() >= event.getParticipantLimit() && event.getParticipantLimit() != 0) {
            throw new ConflictException("Достигнут лимит запросов на участие");
        }

        Request request = RequestMapper.toRequest(userId, eventId);

        if (!event.getRequestModeration() || event.getParticipantLimit() == 0) {
            request.setStatus(StatusEventRequest.CONFIRMED);
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
            eventRepository.save(event);
        } else {
            request.setStatus(StatusEventRequest.PENDING);

        }

        return RequestMapper.toParticipationRequestDto(requestRepository.save(request));
    }

    public List<ParticipationRequestDto> getRequest(Long userId) {
        User user = userService.checkUser(userId);
        List<Request> requestList = requestRepository.findAllByRequester(userId);
        return requestList.stream().map(RequestMapper::toParticipationRequestDto).collect(Collectors.toList());
    }

    public ParticipationRequestDto updateRequest(Long userId, Long requestId) {
        Request request = requestRepository.findByIdAndRequester(requestId, userId);

        if (request == null) {
            throw new ValidationIdException("Request with id=" + requestId + " was not found");
        }
        request.setStatus(StatusEventRequest.CANCELED);
        Request updateRequest = requestRepository.save(request);

        return RequestMapper.toParticipationRequestDto(updateRequest);
    }

    public List<ParticipationRequestDto> getRequestEventByUser(Long userId, Long eventId) {
        Event event = eventRepository.findByIdAndInitiatorId(eventId, userId);

        if (event == null) {
            throw new ValidationIdException("Event with id=" + eventId + " was not found");
        }

        List<Request> requestList = requestRepository.findAllByEvent(eventId);
        return requestList.stream().map(RequestMapper::toParticipationRequestDto).collect(Collectors.toList());
    }

    public EventRequestStatusUpdateResult updateRequestStatus(Long userId, Long eventId, EventRequestStatusUpdateRequest statusUpdateRequest) {
        Event event = eventRepository.findByIdAndInitiatorId(eventId, userId);

        if (event == null) {
            throw new ValidationIdException("Event with id=" + eventId + " was not found");
        }

        Set<Long> requestIds = statusUpdateRequest.getRequestIds();

        List<Request> requestList = requestRepository.findAllByEventAndIdIn(eventId, requestIds);

        EventRequestStatusUpdateResult requestStatusUpdateResult = new EventRequestStatusUpdateResult();

        if (event.getParticipantLimit() == 0 || !event.getRequestModeration()) {
            requestStatusUpdateResult.setConfirmedRequests(requestList.stream().map(RequestMapper::toParticipationRequestDto).collect(Collectors.toList()));
            return requestStatusUpdateResult;
        }

        if (event.getConfirmedRequests() >= event.getParticipantLimit()) {
            throw new ConflictException("Количество участников ограничено");
        }


        for (Request request : requestList) {
            Integer limitRequest = event.getParticipantLimit();
            Integer confirmedRequests = event.getConfirmedRequests();

            if (confirmedRequests >= limitRequest) {
                request.setStatus(StatusEventRequest.REJECTED);
                requestStatusUpdateResult.getRejectedRequests().add(RequestMapper.toParticipationRequestDto(request));
                requestRepository.save(request);
            }

            if (!request.getStatus().equals(StatusEventRequest.PENDING)) {
                throw new ConflictException("Статус можно изменить только у заявок, находящихся в состоянии ожидания");
            }

            if (statusUpdateRequest.getStatus() == StatusEventRequest.CONFIRMED) {
                request.setStatus(StatusEventRequest.CONFIRMED);
                requestRepository.save(request);
                event.setConfirmedRequests(event.getConfirmedRequests() + 1);
                event = eventRepository.save(event);
                requestStatusUpdateResult.getConfirmedRequests().add(RequestMapper.toParticipationRequestDto(request));

            } else {
                request.setStatus(StatusEventRequest.REJECTED);
                requestStatusUpdateResult.getRejectedRequests().add(RequestMapper.toParticipationRequestDto(request));
                requestRepository.save(request);
            }
        }

        return requestStatusUpdateResult;
    }
}
