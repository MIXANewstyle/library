package lab.library.service;

import lab.library.model.News;
import lab.library.model.dto.FileDto;
import lab.library.model.dto.NewsDto;

import java.util.List;
import java.util.Optional;

public interface NewsService {
    News createNews(News news, FileDto fileDto);
    Optional<NewsDto> getNewsById(Integer id);
    List<NewsDto> getAllNews();
    Optional<News> deleteNewsById(Integer id);
    Optional<News> updateNewsById(NewsDto news, Integer id, FileDto fileDto);
}
