package book.alone.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class SampleController {
    @GetMapping("/hello")
    public String hello(Model model) {
        log.info("hello .....");
        model.addAttribute("msg", "Hello world");
        return "/hello";
    }
    @GetMapping("/text-unescaped")
    public String textUnescaped(Model model) {
        model.addAttribute("data", "Hello <b>Spring!</b>");
        return "basic/text-unescaped";
    }
    @GetMapping("/each")
    public String each(Model model) {

        model.addAttribute("list", new String[]{"Das", "Dasdsa"});
        return "/each";
    }
}
