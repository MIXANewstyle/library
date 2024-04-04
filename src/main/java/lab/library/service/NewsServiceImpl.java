package lab.library.service;

import lab.library.model.File;
import lab.library.model.News;
import lab.library.model.dto.FileDto;
import lab.library.model.dto.NewsDto;
import lab.library.repository.FileRepository;
import lab.library.repository.NewsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NewsServiceImpl implements NewsService{
    private final NewsRepository newsRepository;
    private final FileService fileService;
    private final FileRepository fileRepository;

    public NewsServiceImpl(NewsRepository newsRepository, FileService fileService, FileRepository fileRepository) {
        this.newsRepository = newsRepository;
        this.fileService = fileService;
        this.fileRepository = fileRepository;
    }

    @Override
    public News createNews(News news, FileDto fileDto) {
        File file = fileService.save(fileDto);
        news.setFile(file);
        return newsRepository.save(news);
    }

    @Override
    public Optional<NewsDto> getNewsById(Integer id) {
        Optional<News> foundNews = newsRepository.findById(id);
        if (foundNews.isEmpty()) {
            return Optional.empty();
        }
        News news = foundNews.get();
        return Optional.of(new NewsDto(news.getId(), news.getTitle(), news.getShortDescription(), news.getFullDescription(),
                news.getCreated(), news.getFile().getId()));
    }

    @Override
    public List<NewsDto> getAllNews() {
        List<News> allNews = newsRepository.findAll();
        return allNews.stream()
                .map(news -> new NewsDto(news.getId(), news.getTitle(), news.getShortDescription(),
                        news.getFullDescription(), news.getCreated(), news.getFile().getId()))
                .toList();
    }

    @Override
    public Optional<News> deleteNewsById(Integer id) {
        Optional<News> foundNews = newsRepository.findById(id);
        if (foundNews.isEmpty()) {
            return Optional.empty();
        }
        newsRepository.deleteById(id);
        return foundNews;
    }

    @Override
    @Transactional
    public Optional<News> updateNewsById(NewsDto newsDto, Integer id, FileDto fileDto) {
        boolean isNewFileEmpty = fileDto.getContent().length == 0;
        if (isNewFileEmpty) {
            Optional<File> optFile = fileRepository.findById(newsDto.getFileId());
            newsRepository.save(new News(id, newsDto.getTitle(), newsDto.getShortDescription(),
                    newsDto.getFullDescription(), newsDto.getCreated(), optFile.get()));
        }
        newsDto.setCreated(LocalDateTime.now());
//        int oldFileId = newsDto.getFileId();
        File file = fileService.save(fileDto);
        News updatedNews = new News(id, newsDto.getTitle(), newsDto.getShortDescription(),
                newsDto.getFullDescription(), newsDto.getCreated(), file);
        News save = newsRepository.save(updatedNews);
//        fileService.deleteById(oldFileId);
        return Optional.of(save);
    }


}
