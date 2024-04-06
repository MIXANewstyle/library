package lab.library.controller.moderator;

import lab.library.model.News;
import lab.library.model.dto.FileDto;
import lab.library.model.dto.NewsDto;
import lab.library.service.NewsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/moderator")
public class ModeratorController {
    private final NewsService newsService;

    public ModeratorController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/create")
    public String getCreatePage() {
        return "moderator/news/create";
    }

    @PostMapping("/create")
    public String addNews(@ModelAttribute News news, @RequestParam MultipartFile multipartFile, Model model){
        try {
            newsService.createNews(news, new FileDto(multipartFile.getOriginalFilename(), multipartFile.getBytes()));
            return "redirect:/moderator";
        } catch (Exception exception) {
            model.addAttribute("message", exception.getMessage());
            return "moderator/errors/404";
        }
    }

    @GetMapping("/{id}")
    public String getNewsById(Model model, @PathVariable int id){
        Optional<NewsDto> foundNews = newsService.getNewsById(id);
        if (foundNews.isEmpty()){
            model.addAttribute("message", "Новость не найдена");
            return "moderator/errors/404";
        }
        model.addAttribute("news", foundNews.get());
        return "moderator/news/one";
    }

    @GetMapping
    public String getAllNews(Model model) {
        model.addAttribute("listNews", newsService.getAllNews());
        return "moderator/news/news";
    }

    @GetMapping("/delete/{id}")
    public String deleteNewsById(Model model, @PathVariable int id){
        Optional<News> foundNews = newsService.deleteNewsById(id);
        if (foundNews.isEmpty()){
            model.addAttribute("message", "Новость не найдена");
            return "moderator/errors/404";
        }
        return "redirect:/moderator";
    }

    @PostMapping("/update")
    public String updateById(@ModelAttribute NewsDto news, Model model, @RequestParam MultipartFile multipartFile){
        try {
            Optional<News> foundNews = newsService.updateNewsById(news, news.getId(),
                    new FileDto(multipartFile.getOriginalFilename(), multipartFile.getBytes()));
            if (foundNews.isEmpty()) {
                model.addAttribute("message", "Новость не найдена");
                return "moderator/errors/404";
            }
        } catch (IOException e) {
            model.addAttribute("message", e.getMessage());
            return "moderator/errors/404";
        }
        return "redirect:/moderator";
    }
}
