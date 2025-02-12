package book.alone.controller;

import book.alone.dto.UploadFileDTO;
import book.alone.dto.UploadResultDTO;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.parsing.PassThroughSourceExtractor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
public class UpDownController {

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<UploadResultDTO> upload(UploadFileDTO uploadFileDTO) {
        log.info("{}", uploadFileDTO);
        if (uploadFileDTO.getFiles() != null) {
            final List<UploadResultDTO> list = new ArrayList<>();
            uploadFileDTO.getFiles().forEach(multipartFile -> {
                String originalFilename = multipartFile.getOriginalFilename();
                log.info("{}", originalFilename);
                String uuid = UUID.randomUUID().toString();
                boolean image = false;
                Path savePath = Paths.get(uploadPath, uuid + "_" + originalFilename);
                try {
                    multipartFile.transferTo(savePath);
                    if (Files.probeContentType(savePath).startsWith("image")) {
                        image = true;
                        File thumbFile = new File(uploadPath, "s_" + uuid + "_" + originalFilename);
                        Thumbnailator.createThumbnail(savePath.toFile(), thumbFile, 200, 200);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                list.add(UploadResultDTO.withAll().img(image).uuid(uuid).fileName(originalFilename).build());
            });
            return list;
        }
        return null;
    }

    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> viewFileGET(@PathVariable String fileName) {
        log.info("요청된 파일: {}", fileName);

        // 파일 이름 검증 (디렉토리 탐색 공격 방지)
        if (fileName.contains("..")) {
            log.warn("잘못된 파일 이름 요청: {}", fileName);
            return ResponseEntity.badRequest().build();
        }

        // FileSystemResource 생성
        Resource resource = new FileSystemResource(uploadPath + File.separator + fileName);

        // 파일 존재 여부 확인
        if (!resource.exists() || !resource.isReadable()) {
            log.warn("파일이 존재하지 않거나 읽을 수 없습니다: {}", fileName);
            return ResponseEntity.notFound().build();
        }

        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        try {
            headers.add("Content-Type", Files.probeContentType(resource.getFile().toPath()));
            headers.add("Content-Disposition", "inline; filename=\"" + resource.getFilename() + "\"");
        } catch (IOException e) {
            log.error("파일의 Content-Type을 결정하는 중 오류 발생: {}", fileName, e);
            return ResponseEntity.internalServerError().build();
        }

        // 응답 반환
        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }



}
