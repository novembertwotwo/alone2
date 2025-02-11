package book.alone.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardListReplyCountDTO {
    private Long bno;
    private String title;
    private String writer;
    private LocalDateTime regDate;
    private Long replyCount;
    @QueryProjection
    public BoardListReplyCountDTO(Long bno, String title, String writer, LocalDateTime regDate, Long replyCount) {
        this.bno = bno;
        this.title = title;
        this.writer = writer;
        this.regDate = regDate;
        this.replyCount = replyCount;
    }
}

