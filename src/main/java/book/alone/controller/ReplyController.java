package book.alone.controller;

import book.alone.dto.ReplyDTO;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/replies")
@Slf4j
public class ReplyController {

    @PostMapping(value="/",consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String,Long> register(@Valid @RequestBody ReplyDTO replyDTO, BindingResult bindingResult)throws Exception {
        log.info("{}", replyDTO);
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        Map<String, Long> resultMap = new HashMap<>();
        resultMap.put("rno", 11L);
        return resultMap;
    }

}
