package ru.clevertec.app.app;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.clevertec.app.dto.Metadata;
import ru.clevertec.app.model.FileMetadata;

@Mapper(componentModel = "spring")
public interface FileMetadataMapper {

    @Mapping(target = "size", ignore = true)
    FileMetadata toEntity(Metadata metadata);

}
