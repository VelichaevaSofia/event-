package ru.university.velichaeva.exception;

import java.util.UUID;

public class EventNotFoundException extends BusinessException {

    public EventNotFoundException(UUID id) {
        super("Событие не найдено: id = " + id);
    }
}