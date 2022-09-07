package ru.clevertec.app.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.clevertec.app.model.FileModel;

public interface FileModelRepository extends MongoRepository<FileModel, String> {
}
