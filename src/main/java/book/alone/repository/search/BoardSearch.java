package book.alone.repository.search;

import book.alone.domain.Board;
import book.alone.dto.BoardDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardSearch {
    Page<Board> search1(Pageable pageable);

    Page<Board> searchAll(String[] types, String keyword, Pageable pageable);

    Page<BoardDto> searchAll2(String[] types, String keyword, Pageable pageable);
}
