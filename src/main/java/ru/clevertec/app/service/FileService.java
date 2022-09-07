package ru.clevertec.app.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import ru.clevertec.app.dto.Metadata;
import ru.clevertec.app.exception.ApkFileNotFoundException;
import ru.clevertec.app.model.FileModel;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public interface FileService {
    List<FileModel> getFiles();
    FileModel upload(MultipartFile multipartFile, Metadata json) throws IOException;
    Resource getFileByVersion(String version) throws FileNotFoundException, ApkFileNotFoundException, MalformedURLException;
    List<String> getAllVersions();
}
