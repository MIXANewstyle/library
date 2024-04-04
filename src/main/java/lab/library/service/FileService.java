package lab.library.service;

import lab.library.model.File;
import lab.library.model.dto.FileDto;

import java.util.Optional;

public interface FileService {
    File save (FileDto fileDto);
    Optional<FileDto> getFileById(int id);
    boolean deleteById(int id);
}
