package book.alone.repository;

import book.alone.domain.Board;
import book.alone.domain.Reply;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
class ReplyRepositoryTest {
    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void testInsert() {
        Long bno = 100L;
        Board board = Board.builder().bno(bno).build();

        Reply reply = Reply.builder().board(board)
                .replyText("댓글...")
                .replyer("replyer1")
                .build();
        replyRepository.save(reply);
    }

}