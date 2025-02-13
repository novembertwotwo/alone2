package book.alone.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class BoardImageDTO {
    private String uuid;
    private String fileName;
    private int ord;
    @QueryProjection
    public BoardImageDTO(String uuid, String fileName, int ord) {
        this.uuid = uuid;
        this.fileName = fileName;
        this.ord = ord;
    }
}
