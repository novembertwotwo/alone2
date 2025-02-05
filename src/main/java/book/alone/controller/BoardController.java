package book.alone.controller;

import book.alone.dto.BoardDto;
import book.alone.dto.PageRequestDto;
import book.alone.dto.PageResponseDto;
import book.alone.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/list")
    public String list(@ModelAttribute PageRequestDto pageRequestDTO, Model model) {

        PageResponseDto<BoardDto> responseDto = boardService.list2(pageRequestDTO);

        log.info("PageRequestDto: {}", pageRequestDTO);
        log.info("ResponseDto: {}", responseDto);


        model.addAttribute("responseDTO", responseDto);
        model.addAttribute("pageRequestDTO", pageRequestDTO); // 추가

        return "/board/list";
    }
}
