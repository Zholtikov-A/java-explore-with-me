package ru.practicum.ewmservice.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.ewmservice.dto.CompilationDto;
import ru.practicum.ewmservice.dto.EventShortDto;
import ru.practicum.ewmservice.dto.NewCompilationDto;
import ru.practicum.ewmservice.model.Compilation;
import ru.practicum.ewmservice.model.Event;

import java.util.List;

@UtilityClass
public class CompilationMapper {

    public Compilation toCompilation(NewCompilationDto newCompilationDto, List<Event> eventList) {
        return Compilation.builder()
                .events(eventList)
                .pinned(newCompilationDto.getPinned())
                .title(newCompilationDto.getTitle())
                .build();
    }

    public static CompilationDto toCompilationDto(Compilation compilationSave, List<EventShortDto> eventShortDtos) {
        return CompilationDto.builder()
                .events(eventShortDtos)
                .id(compilationSave.getId())
                .pinned(compilationSave.getPinned())
                .title(compilationSave.getTitle())
                .build();
    }
}
