package ru.clevertec.app.exception;

public class DuplicateVersionException extends Exception{
    public DuplicateVersionException(String message) {
        super(message);
    }
}