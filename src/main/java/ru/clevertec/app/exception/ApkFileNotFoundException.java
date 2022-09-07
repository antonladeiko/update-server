package ru.clevertec.app.exception;

public class ApkFileNotFoundException extends Exception {
    public ApkFileNotFoundException() {
        super("Files not found");
    }

    public ApkFileNotFoundException(String version) {
        super("File with " + version + " not found.");
    }
}
