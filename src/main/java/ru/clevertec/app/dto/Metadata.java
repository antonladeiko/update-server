package ru.clevertec.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Metadata {

    private String name;

    private String version;

    private String buildNumber;

}
