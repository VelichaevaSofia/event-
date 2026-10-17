package ru.university.velichaeva.exception;

public class InterlocutorEmptyException extends BusinessException {

    public InterlocutorEmptyException() {
        super("Собеседник не должен быть пустым");
    }
}