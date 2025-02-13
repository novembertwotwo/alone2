package book.alone.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Setter
public class BoardListAllDTO {
    private Long bno;

    private String title;

    private String writer;

    private LocalDateTime regDate;

    private Long replyCount;

    private List<BoardImageDTO> boardLImages;

    @QueryProjection
    public BoardListAllDTO(Long bno, String title, String writer, LocalDateTime regDate, Long replyCount) {
        this.bno = bno;
        this.title = title;
        this.writer = writer;
        this.regDate = regDate;
        this.replyCount = replyCount;
        this.boardLImages = null;
    }
}
