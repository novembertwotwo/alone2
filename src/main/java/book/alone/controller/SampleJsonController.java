package book.alone.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@Slf4j
public class SampleJsonController {
    @GetMapping("/helloArr")
    public Sample hello(Model model) {
        Sample sample = new Sample(12,"Dasdsa");
        return sample;
    }

}
