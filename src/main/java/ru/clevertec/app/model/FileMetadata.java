package ru.clevertec.app.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileMetadata {

    private String name;

    private String buildNumber;

    private String version;

    private double size;
}
