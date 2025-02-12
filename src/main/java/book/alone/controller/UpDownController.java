package book.alone.controller;

import book.alone.dto.UploadFileDTO;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.parsing.PassThroughSourceExtractor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@Slf4j
public class UpDownController {

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String upload(UploadFileDTO uploadFileDTO) {
        log.info("{}", uploadFileDTO);
        if (uploadFileDTO.getFiles() != null) {
            uploadFileDTO.getFiles().forEach(multipartFile -> {
                String originalFilename = multipartFile.getOriginalFilename();
                log.info("{}", originalFilename);
                String uuid = UUID.randomUUID().toString();
                Path savePath = Paths.get(uploadPath, uuid + "_" + originalFilename);
                try {
                    multipartFile.transferTo(savePath);
                    if (Files.probeContentType(savePath).startsWith("image")) {
                        File thumbFile = new File(uploadPath, "s_" + uuid + "_" + originalFilename);
                        Thumbnailator.createThumbnail(savePath.toFile(), thumbFile, 200, 200);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });
        }
        return null;
    }


}
