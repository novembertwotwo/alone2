package book.alone.dto;

import com.querydsl.core.annotations.QueryProjection;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@Getter
public class BoardDto {
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
    @QueryProjection
    public BoardDto(Long bno, String title, String content, String writer, LocalDateTime regDate, LocalDateTime modDate) {
        this.bno = bno;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.regDate = regDate;
        this.modDate = modDate;
    }
}
