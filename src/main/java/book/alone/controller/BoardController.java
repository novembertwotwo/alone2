package book.alone.controller;

import book.alone.dto.BoardDTO;
import book.alone.dto.BoardListReplyCountDTO;
import book.alone.dto.PageRequestDTO;
import book.alone.dto.PageResponseDTO;
import book.alone.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

//    @GetMapping("/list")
//    public String list(@ModelAttribute PageRequestDTO pageRequestDTO, Model model) {
//
//        PageResponseDTO<BoardDTO> responseDto = boardService.list2(pageRequestDTO);
//
//        log.info("PageRequestDto: {}", pageRequestDTO);
//        log.info("ResponseDto: {}", responseDto);
//
//
//        model.addAttribute("responseDTO", responseDto);
//        model.addAttribute("pageRequestDTO", pageRequestDTO); // 추가
//
//        return "/board/list";
//    }

    @GetMapping("/register")
    public void registerGet() {

    }

    @PostMapping("/register")
    public String registerPost(@Valid BoardDTO boardDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        log.info("board POST register....");
        if (bindingResult.hasErrors()) {
            log.info("has error");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/board/register";
        }

        log.info("{}", boardDTO);
        Long bno = boardService.register(boardDTO);
        redirectAttributes.addFlashAttribute("result", bno);
        return "redirect:/board/list";
    }

    @GetMapping({"/read", "/modify"})
    public void read(Long bno, PageRequestDTO pageRequestDTO, Model model) {
        BoardDTO boardDTO = boardService.read(bno);
        log.info("{}", boardDTO);
        model.addAttribute("dto", boardDTO);
        model.addAttribute("pageRequestDTO", pageRequestDTO);
    }

    @PostMapping
    public String modify(PageRequestDTO pageRequestDTO,
                         @Valid BoardDTO boardDTO,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        log.info("board modifyPost..... {}", boardDTO);
        if (bindingResult.hasErrors()) {
            log.info("has Error");
            String link = pageRequestDTO.getLink();
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            redirectAttributes.addFlashAttribute("bno", boardDTO.getBno());
            redirectAttributes.addFlashAttribute("pageRequestDTO", pageRequestDTO);
            return "redirect:/board/modify?" + link;
        }
        boardService.modify(boardDTO);
        redirectAttributes.addFlashAttribute("result", "modified");
        redirectAttributes.addFlashAttribute("bno", boardDTO.getBno());
        redirectAttributes.addFlashAttribute("pageRequestDTO", pageRequestDTO);
        return "redirect:/board/read";
    }

    @PostMapping("/remove")
    public String remove(Long bno, RedirectAttributes redirectAttributes) {

        log.info("remove post, {}", bno);
        boardService.remove(bno);
        redirectAttributes.addFlashAttribute("result", "removed");
        return "redirect:/board/list";
    }



    @ResponseBody
    @GetMapping("/list")
    public PageResponseDTO<BoardListReplyCountDTO> list(PageRequestDTO pageRequestDTO, Model model) {
        return boardService.listWithReplyCount(pageRequestDTO);
    }
}
