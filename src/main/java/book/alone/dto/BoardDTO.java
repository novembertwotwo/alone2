package book.alone.dto;

import com.querydsl.core.annotations.QueryProjection;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Getter
public class BoardDTO {
    private Long bno;
    @NotEmpty
    @Size(min = 3,max = 100)
    private String title;
    @NotEmpty
    private String content;
    @NotEmpty
    private String writer;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
    private List<String> fileNames;

    @Builder
    @QueryProjection
    public BoardDTO(Long bno, String title, String content, String writer, LocalDateTime regDate, LocalDateTime modDate) {
        this.bno = bno;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.regDate = regDate;
        this.modDate = modDate;
    }
}
