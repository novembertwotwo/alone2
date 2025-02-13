package book.alone.dto;

import book.alone.domain.BoardImage;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@Getter
public class BoardDTONew {
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

    private Set<BoardImage> imageSet;
    @QueryProjection
    public BoardDTONew(Long bno, String title, String content, String writer, LocalDateTime regDate, LocalDateTime modDate, Set<BoardImage> imageSet) {
        this.bno = bno;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.regDate = regDate;
        this.modDate = modDate;
        this.imageSet = imageSet;
    }
}
