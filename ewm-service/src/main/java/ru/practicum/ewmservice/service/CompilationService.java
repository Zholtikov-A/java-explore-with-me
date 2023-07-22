package ru.practicum.ewmservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.dto.CompilationDto;
import ru.practicum.ewmservice.dto.EventShortDto;
import ru.practicum.ewmservice.dto.NewCompilationDto;
import ru.practicum.ewmservice.dto.UpdateCompilationRequest;
import ru.practicum.ewmservice.exceptions.ValidationIdException;
import ru.practicum.ewmservice.mapper.CompilationMapper;
import ru.practicum.ewmservice.mapper.EventMapper;
import ru.practicum.ewmservice.model.Compilation;
import ru.practicum.ewmservice.model.Event;
import ru.practicum.ewmservice.repository.CompilationRepository;
import ru.practicum.ewmservice.repository.EventRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompilationService {

    private final EventRepository eventRepository;
    private final CompilationRepository compilationRepository;

    public CompilationDto create(NewCompilationDto compilationDto) {

        List<Event> eventList = eventRepository.findAllByIdIn(compilationDto.getEvents() == null ? new HashSet<>() : compilationDto.getEvents());
        Compilation compilation = CompilationMapper.toCompilation(compilationDto, eventList);
        Compilation compilationSave = compilationRepository.save(compilation);
        List<EventShortDto> eventShortDtoList = compilationSave.getEvents()
                .stream().map(EventMapper::toEventShortDto).collect(Collectors.toList());

        return CompilationMapper.toCompilationDto(compilationSave, eventShortDtoList);
    }

    public void delete(Long compId) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new ValidationIdException("Compilation with id=" + compId + " was not found"));
        compilationRepository.delete(compilation);
    }

    public CompilationDto update(Long compId, UpdateCompilationRequest updateCompilationRequest) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new ValidationIdException("Compilation with id=" + compId + " was not found"));

        if (updateCompilationRequest.getTitle() != null) {
            compilation.setTitle(updateCompilationRequest.getTitle());
        }
        if (updateCompilationRequest.getTitle() != null) {
            compilation.setTitle(updateCompilationRequest.getTitle());
        }
        if (updateCompilationRequest.getEvents() != null) {
            List<Event> eventList = eventRepository.findAllByIdIn(updateCompilationRequest.getEvents());
            compilation.setEvents(eventList);
        }
        Compilation updateCompilation = compilationRepository.save(compilation);
        List<EventShortDto> eventShortDtoList = updateCompilation.getEvents()
                .stream().map(EventMapper::toEventShortDto).collect(Collectors.toList());

        return CompilationMapper.toCompilationDto(updateCompilation, eventShortDtoList);
    }

    public List<CompilationDto> get(Boolean pinned, Integer from, Integer size) {
        List<Compilation> compilations = new ArrayList<>();

        if (pinned != null) {
            compilations = compilationRepository.findAllByPinned(pinned, PageRequest.of(from, size));
        } else {
            compilations = compilationRepository.findAll(PageRequest.of(from, size)).getContent();
        }

        return compilations.stream()
                .map(compilation -> {
                    List<EventShortDto> eventShortDtoList = compilation.getEvents()
                            .stream().map(EventMapper::toEventShortDto).collect(Collectors.toList());
                    return CompilationMapper.toCompilationDto(compilation, eventShortDtoList);
                }).collect(Collectors.toList());

    }

    public CompilationDto getById(Long compId) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new ValidationIdException("Compilation with id=" + compId + " was not found"));
        List<EventShortDto> eventShortDtoList = compilation.getEvents()
                .stream().map(EventMapper::toEventShortDto).collect(Collectors.toList());
        return CompilationMapper.toCompilationDto(compilation, eventShortDtoList);
    }
}
