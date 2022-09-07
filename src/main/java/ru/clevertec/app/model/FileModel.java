package ru.clevertec.app.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import javax.persistence.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Document(collection = "files")
@RequiredArgsConstructor
public class FileModel {

    @Id
    @Field(targetType = FieldType.OBJECT_ID)
    private String id;

    private String name;

    private String type;

    private String path;

    private FileMetadata metadata;

    public FileModel(String name, String type, String path) {
        this.name = name;
        this.type = type;
        this.path = path;
    }
}
