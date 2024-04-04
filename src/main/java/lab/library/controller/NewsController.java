package lab.library.controller;

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
@RequestMapping("/news")
public class NewsController {
    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/create")
    public String getCreatePage() {
        return "news/create";
    }

    @PostMapping("/create")
    public String addNews(@ModelAttribute News news, @RequestParam MultipartFile multipartFile, Model model){
        try {
            newsService.createNews(news, new FileDto(multipartFile.getOriginalFilename(), multipartFile.getBytes()));
            return "redirect:/news";
        } catch (Exception exception) {
            model.addAttribute("message", exception.getMessage());
            return "errors/404";
        }
    }

    @GetMapping("/{id}")
    public String getNewsById(Model model, @PathVariable int id){
        Optional<NewsDto> foundNews = newsService.getNewsById(id);
        if (foundNews.isEmpty()){
            model.addAttribute("message", "Новость не найдена");
            return "errors/404";
        }
        model.addAttribute("news", foundNews.get());
        return "news/one";
    }

    @GetMapping
    public String getAllNews(Model model) {
        model.addAttribute("listNews", newsService.getAllNews());
        return "news/news";
    }

    @GetMapping("/delete/{id}")
    public String deleteNewsById(Model model, @PathVariable int id){
        Optional<News> foundNews = newsService.deleteNewsById(id);
        if (foundNews.isEmpty()){
            model.addAttribute("message", "Новость не найдена");
            return "errors/404";
        }
        return "redirect:/news";
    }

    @PostMapping("/update")
    public String updateById(@ModelAttribute NewsDto news, Model model, @RequestParam MultipartFile multipartFile){
        try {
            Optional<News> foundNews = newsService.updateNewsById(news, news.getId(),
                    new FileDto(multipartFile.getOriginalFilename(), multipartFile.getBytes()));
            if (foundNews.isEmpty()) {
                model.addAttribute("message", "Новость не найдена");
                return "errors/404";
            }
        } catch (IOException e) {
            model.addAttribute("message", e.getMessage());
            return "errors/404";
        }
        return "redirect:/news";
    }
}
