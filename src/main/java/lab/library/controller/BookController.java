package lab.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/library")
public class BookController {
    public String getHomePage(){
        return "homePage/homePage";
    }
}
