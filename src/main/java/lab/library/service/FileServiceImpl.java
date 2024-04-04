package lab.library.service;

import lab.library.model.File;
import lab.library.model.dto.FileDto;
import lab.library.repository.FileRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService{
    private final FileRepository fileRepository;
    private final String storageDirectory;

    public FileServiceImpl(FileRepository fileRepository, @Value("${file.directory}") String storageDirectory) {
        this.fileRepository = fileRepository;
        this.storageDirectory = storageDirectory;
        createStorageDirectory(storageDirectory);
    }

    @Override
    public File save (FileDto fileDto){
        String path = getNewFilePath(fileDto.getName());
        writeFileBytes(path, fileDto.getContent());
        return fileRepository.save(new File(fileDto.getName(), path));
    }

    @Override
    public Optional<FileDto> getFileById(int id) {
        Optional<File> foundFile = fileRepository.findById(id);
        if(foundFile.isEmpty()){
            return Optional.empty();
        }
        File file = foundFile.get();
        FileDto fileDto = new FileDto(file.getName(), readFileAsBytes(file.getPath()));
        return Optional.of(fileDto);
    }

    @Override
    public boolean deleteById(int id) {
        Optional<File> foundFile = fileRepository.findById(id);
        if(foundFile.isEmpty()){
            return false;
        }
        deleteFile(foundFile.get().getPath());
        fileRepository.deleteById(id);
        return true;
    }

    private void deleteFile(String path){
        try {
            Files.deleteIfExists(Path.of(path));
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    private void createStorageDirectory(String path) {
        try {
            Files.createDirectories(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getNewFilePath(String sourceName) {
        return storageDirectory + java.io.File.separator + UUID.randomUUID() + sourceName;
    }

    private byte[] readFileAsBytes(String path) {
        try {
            return Files.readAllBytes(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeFileBytes(String path, byte[] content) {
        try {
            Files.write(Path.of(path), content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
