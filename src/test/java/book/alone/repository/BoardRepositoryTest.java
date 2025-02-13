package book.alone.repository;

import book.alone.domain.Board;

import book.alone.dto.BoardListAllDTO;
import book.alone.dto.BoardListReplyCountDTO;
import book.alone.repository.search.BoardSearch;
import book.alone.repository.search.BoardSearchImpl;
import book.alone.service.BoardServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableArgumentResolver;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

@Slf4j
@SpringBootTest
class BoardRepositoryTest {
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private PageableArgumentResolver pageableArgumentResolver;
    @Autowired
    private BoardServiceImpl boardServiceImpl;
    @Autowired
    private BoardSearchImpl boardSearch;
    @Autowired
    private ReplyRepository replyRepository;


    @Test
    public void testInsert() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
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
    public void testSelect() {
        Long bno = 100L;
        Optional<Board> result = boardRepository.findById(bno);
        Board board = result.orElseThrow();
        log.info(String.valueOf(board));
    }

    @Test
    public void testUpdate() {
        Long bno = 100L;
        Optional<Board> result = boardRepository.findById(bno);
        Board board = result.orElseThrow();
        board.change("update...title 100", "update content 100");
        boardRepository.save(board);
    }

    @Test
    public void testDelete() {
        Long bno = 1L;
        boardRepository.deleteById(bno);
    }

    @Test
    public void testPaging() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
        Page<Board> result = boardRepository.findAll(pageable);
        log.info("total count: " + result.getTotalElements());
        log.info("total pages: " + result.getTotalPages());
        log.info("page number: " + result.getNumber());
        log.info("page size: " + result.getSize());
        List<Board> todoList = result.getContent();
        todoList.forEach(board -> log.info("{}", board));
    }

    @Test
    public void jpaTest() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
        log.info("{}", boardRepository.getTime());
        log.info("{}", boardRepository.findKeyword("title", pageable));

    }

    @Test
    public void testSearch() {
        Pageable pageable = PageRequest.of(1, 10, Sort.by("bno").descending());
        boardSearch.search1(pageable);
    }

    @Test
    public void testSearchAll() {
        String[] types = {"t", "c", "w"};
        String keyword = "1";
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
        Page<Board> boards = boardSearch.searchAll(types, keyword, pageable);
    }

    @Test
    public void testSearchReplyCount() {
        String[] types = {};
        String keyword = "";
        Pageable pageable = PageRequest.of(9, 10);
        Page<BoardListReplyCountDTO> result = boardSearch.searchWithReplyCount(types, keyword, pageable);
        log.info("{}", result.getTotalPages());
        log.info("{}", result.getSize());
        log.info("{}", result.getNumber());
        log.info("{}", result.hasNext());
        result.getContent().forEach(boardListReplyCountDto -> log.info("{}", boardListReplyCountDto));
    }
    @Test
    public void testInsertWithImages() {
        Board board = Board.builder()
                .title("Image Test")
                .content("testttttt")
                .writer("tk")
                .build();
        for (int i = 0; i < 3; i++) {
            board.addImage(UUID.randomUUID().toString(), "file" + i + ".jpg");
        }
        boardRepository.save(board);
    }
    @Test
    public void testReadWithImages() {
        Board board = boardSearch.findByIdFetch(152L);

        log.info("{}", board );
        log.info("{}", board.getImageSet() );

    }

    @Transactional
    @Commit
    @Test
    public void testModifyImages() {
        Board board = boardSearch.findByIdFetch(202L);
        board.clearImages();
    }

    @Test
    @Transactional
    @Commit
    public void testRemoveAll() {
        Long bno = 402L;
        replyRepository.deleteByBoard_Bno(bno);
        boardRepository.deleteById(bno);
    }
    @Test
    public void testInsertAll() {
        for (int i = 1; i <= 100; i++) {
            Board board = Board.builder()
                    .title("Title.." + i)
                    .content("Content.." + i)
                    .writer("writer.." + i)
                    .build();
            for (int j = 0; j < 3; j++) {
                if (i % 5 == 0) {
                    continue;
                }
                board.addImage(UUID.randomUUID().toString(), i+"file"+j+".jpg");
            }
            boardRepository.save(board);
        }
    }
    @Test
    public void testSearchImageReplyCount() {
        Pageable pageable = PageRequest.of(0, 10);

        Page<BoardListAllDTO> result = boardSearch.searchWithAll(null, null, pageable);
        log.info("---------------------");
        log.info("{}",result.getTotalElements());
        result.getContent().forEach(boardListAllDTO -> log.info("{}",boardListAllDTO));

    }




}