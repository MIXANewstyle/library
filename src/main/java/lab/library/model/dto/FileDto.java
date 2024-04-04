package lab.library.model.dto;


import java.util.Arrays;
import java.util.Objects;

//data transfer object (opimization)
public class FileDto {
    private String name;
    private byte[] content;

    public FileDto(String name, byte[] content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileDto fileDto = (FileDto) o;
        return Objects.equals(getName(), fileDto.getName()) && Arrays.equals(getContent(), fileDto.getContent());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getName());
        result = 31 * result + Arrays.hashCode(getContent());
        return result;
    }

    @Override
    public String toString() {
        return "FileDto{" +
                "name='" + name + '\'' +
                ", content=" + Arrays.toString(content) +
                '}';
    }
}
