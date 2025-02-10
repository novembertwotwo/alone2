package book.alone.service;

import book.alone.domain.Board;
import book.alone.dto.BoardDto;
import book.alone.dto.PageRequestDto;
import book.alone.dto.PageResponseDto;
import book.alone.repository.BoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class BoardServiceImplTest {
    @Autowired
    private BoardService boardService;
    @Autowired
    private BoardRepository boardRepository;

    //@BeforeEach
    public void testInsert() {
        IntStream.rangeClosed(1,100).forEach(i->{
                    Board board = Board.builder()
                            .title("title..." + i)
                            .content("content..." + i)
                            .writer("user" + (i % 10)).build();
                    Board result = boardRepository.save(board);
                    log.info("BNO: " + result.getBno());
                }
        );
    }

    @Test
    public void testRegister() {
        log.info(boardService.getClass().getName());
        BoardDto boardDto = BoardDto.builder()
                .title("Simple Test")
                .content("Sample content...")
                .writer("user00")
                .build();
        Long bno = boardService.register(boardDto);
        log.info("bno: {}", bno);
    }

    @Test
    public void testModify() {
        BoardDto boardDto = BoardDto.builder()
                .bno(101L)
                .title("updated...101")
                .content("updated content 101...")
                .build();
        boardService.modify(boardDto);
    }
    @Test
    public void testList() {
        PageRequestDto pageRequestDto = PageRequestDto.builder()
                .type("tcw")
                .keyword("1")
                .page(1)
                .size(10)
                .build();
        PageResponseDto<BoardDto> responseDto = boardService.list(pageRequestDto);
        log.info("{}", responseDto);
    }
}