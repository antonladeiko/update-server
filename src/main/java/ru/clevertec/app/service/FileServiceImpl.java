package ru.clevertec.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.clevertec.app.app.FileMetadataMapper;
import ru.clevertec.app.dao.FileModelRepository;
import ru.clevertec.app.dto.Metadata;
import ru.clevertec.app.exception.ApkFileNotFoundException;
import ru.clevertec.app.model.FileMetadata;
import ru.clevertec.app.model.FileModel;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileModelRepository fileRepository;
    private final FileMetadataMapper mapper;
    private final Path root = Paths.get("D:\\uploads");

    @Override
    public List<FileModel> getFiles() {
        return fileRepository.findAll();
    }

    public void uploadInFolder(MultipartFile multipartFile) throws IOException {
        if (!Files.exists(root)){
            Files.createDirectory(root);
        }

        try {
            Files.copy(multipartFile.getInputStream(),
                    this.root.resolve(Objects.requireNonNull(multipartFile.getOriginalFilename())));
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    @Override
    public FileModel upload(MultipartFile multipartFile, Metadata metadata) throws IOException {
        uploadInFolder(multipartFile);
        FileModel fileModel = new FileModel(multipartFile.getOriginalFilename(),
                multipartFile.getContentType(), root + "\\" + multipartFile.getOriginalFilename());

        FileMetadata fileMetadata = mapper.toEntity(metadata);
        fileMetadata.setSize(multipartFile.getSize());
        fileModel.setMetadata(fileMetadata);
        return fileRepository.save(fileModel);
    }

    @Override
    public Resource getFileByVersion(String version) throws ApkFileNotFoundException, MalformedURLException {
        for (FileModel fileModel : getFiles()) {
            if (fileModel.getMetadata().getVersion().equals(version)) {
                Path file = root.resolve(fileModel.getName());
                return new UrlResource(file.toUri());
            }
        }
        throw new ApkFileNotFoundException(version);
    }

    @Override
    public List<String> getAllVersions() {
        List<String> versions = new ArrayList<>();

        for (FileModel fileModel:fileRepository.findAll()){
            versions.add(fileModel.getMetadata().getVersion());
        }

        return versions;
    }
}
