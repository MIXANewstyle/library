package lab.library.controller;

import lab.library.service.NewsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final NewsService newsService;

    public HomeController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping
    public String getHomePage(Model model) {
        model.addAttribute("listNews", newsService.getAllNews());
        return "homePage/homePage";
    }


}
