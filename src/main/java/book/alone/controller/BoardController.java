package book.alone.controller;

import book.alone.domain.Board;
import book.alone.dto.BoardDto;
import book.alone.dto.PageRequestDto;
import book.alone.dto.PageResponseDto;
import book.alone.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.message.StringFormattedMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;
    private final LocalValidatorFactoryBean localValidatorFactoryBean;

    @GetMapping("/list")
    public String list(@ModelAttribute PageRequestDto pageRequestDTO, Model model) {

        PageResponseDto<BoardDto> responseDto = boardService.list2(pageRequestDTO);

        log.info("PageRequestDto: {}", pageRequestDTO);
        log.info("ResponseDto: {}", responseDto);


        model.addAttribute("responseDTO", responseDto);
        model.addAttribute("pageRequestDTO", pageRequestDTO); // 추가

        return "/board/list";
    }

    @GetMapping("/register")
    public void registerGet() {

    }

    @PostMapping("/register")
    public String registerPost(@Valid BoardDto boardDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        log.info("board POST register....");
        if (bindingResult.hasErrors()) {
            log.info("has error");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/board/register";
        }

        log.info("{}", boardDto);
        Long bno = boardService.register(boardDto);
        redirectAttributes.addFlashAttribute("result", bno);
        return "redirect:/board/list";
    }

    @GetMapping({"/read", "/modify"})
    public void read(Long bno, PageRequestDto pageRequestDTO, Model model) {
        BoardDto boardDto = boardService.read(bno);
        log.info("{}", boardDto);
        model.addAttribute("dto", boardDto);
        model.addAttribute("pageRequestDTO", pageRequestDTO);
    }

    @PostMapping
    public String modify(PageRequestDto pageRequestDto,
                         @Valid BoardDto boardDto,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        log.info("board modifyPost..... {}", boardDto);
        if (bindingResult.hasErrors()) {
            log.info("has Error");
            String link = pageRequestDto.getLink();
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            redirectAttributes.addFlashAttribute("bno", boardDto.getBno());
            redirectAttributes.addFlashAttribute("pageRequestDTO", pageRequestDto);
            return "redirect:/board/modify?" + link;
        }
        boardService.modify(boardDto);
        redirectAttributes.addFlashAttribute("result", "modified");
        redirectAttributes.addFlashAttribute("bno", boardDto.getBno());
        redirectAttributes.addFlashAttribute("pageRequestDTO", pageRequestDto);
        return "redirect:/board/read";
    }

    @PostMapping("/remove")
    public String remove(Long bno,RedirectAttributes redirectAttributes) {
        log.info("remove post, {}", bno);
        boardService.remove(bno);
        redirectAttributes.addFlashAttribute("result", "removed");
        return "redirect:/board/list";
    }
}
