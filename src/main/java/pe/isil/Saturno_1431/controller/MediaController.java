package pe.isil.Saturno_1431.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pe.isil.Saturno_1431.service.FileSystemStorageService;

import java.io.IOException;
import java.nio.file.Files;

@Controller
@Slf4j
@RequestMapping("/media")
public class MediaController {

    @Autowired
    private FileSystemStorageService fileSystemStorageService;

    @GetMapping("/{nombreArchivo}")
    ResponseEntity<Resource> getMedia(@PathVariable String nombreArchivo) throws IOException
    {
        Resource resource = fileSystemStorageService.loadAsResource(nombreArchivo);
        String contentType = Files.probeContentType(resource.getFile().toPath());

        return ResponseEntity
                .ok()
                .header("Content-Type", contentType)
                .body(resource);
    }

}
