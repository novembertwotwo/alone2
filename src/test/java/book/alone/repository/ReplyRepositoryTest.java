package book.alone.repository;

import book.alone.domain.Board;
import book.alone.domain.Reply;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;




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

    @Test
    public void testBoardReplies() {
        Long bno = 100L;

        Pageable pageable = PageRequest.of(0, 10, Sort.by("rno").descending());

        Page<Reply> replies = replyRepository.listOfBoard(bno, pageable);
        replies.getContent().forEach(reply -> log.info("{}", reply));

    }


}