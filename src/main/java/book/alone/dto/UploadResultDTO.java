package book.alone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class UploadResultDTO {
    private String uuid;
    private String fileName;
    private boolean img;
    public String getLink() {
        if (img) {
            return "s_" + uuid + "_" + fileName;
        } else {
            return uuid + "_" + fileName;
        }
    }
    @Builder(builderMethodName = "withAll")
    public UploadResultDTO(String uuid, String fileName, boolean img) {
        this.uuid = uuid;
        this.fileName = fileName;
        this.img = img;
    }

}
