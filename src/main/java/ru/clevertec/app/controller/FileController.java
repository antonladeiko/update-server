package ru.clevertec.app.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.clevertec.app.dto.Metadata;
import ru.clevertec.app.exception.ApkFileNotFoundException;
import ru.clevertec.app.exception.DuplicateVersionException;
import ru.clevertec.app.message.ResponseMessage;
import ru.clevertec.app.model.FileModel;
import ru.clevertec.app.service.FileServiceImpl;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileController {

    private final FileServiceImpl fileService;

    @GetMapping()
    public ResponseEntity<List<FileModel>> showFiles() throws ApkFileNotFoundException {
        List<FileModel> files = fileService.getFiles();
        if (files.isEmpty()){
            throw new ApkFileNotFoundException();
        }
        return ResponseEntity.ok(files);
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseMessage> uploadFileInDB(@RequestPart("file") MultipartFile file,
                                                          @RequestPart("meta") Metadata metadata) throws IOException, DuplicateVersionException {
        for (int i = 0; i < getVersions().size(); i++){
            if (metadata.getVersion().equals(getVersions().get(i))){
                throw new DuplicateVersionException("File with this version already uploaded");
            }
        }
        fileService.upload(file, metadata);
        if (fileService.getFiles().isEmpty()){
            throw new FileNotFoundException();
        }

        return ResponseEntity.status(HttpStatus.OK).
                body(new ResponseMessage("Uploaded the file successfully: " + file.getOriginalFilename()));
    }

    @GetMapping("/download/{version}")
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(@PathVariable String version) throws ApkFileNotFoundException, MalformedURLException {
        Resource file = fileService.getFileByVersion(version);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @GetMapping("/versions")
    public List<String> getVersions(){
        return fileService.getAllVersions();
    }
}
