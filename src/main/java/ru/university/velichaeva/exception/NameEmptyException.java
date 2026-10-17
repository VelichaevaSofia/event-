package ru.university.velichaeva.exception;

public class NameEmptyException extends BusinessException {

    public NameEmptyException() {
        super("Имя не должно быть пустым");
    }
}