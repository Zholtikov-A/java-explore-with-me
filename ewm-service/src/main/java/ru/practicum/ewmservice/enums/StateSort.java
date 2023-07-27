package ru.practicum.ewmservice.enums;

import org.springframework.data.domain.Sort;

public enum StateSort {
    EVENT_DATE("eventDate"), VIEWS("views");

    private final String sortField;

    StateSort(String sortField) {
        this.sortField = sortField;
    }

    public Sort toSort(Sort.Direction direction) {
        return Sort.by(direction, this.sortField);
    }
}
