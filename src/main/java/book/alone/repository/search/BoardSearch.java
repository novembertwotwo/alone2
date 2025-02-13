package book.alone.repository.search;

import book.alone.domain.Board;
import book.alone.dto.BoardDTO;

import book.alone.dto.BoardListAllDTO;
import book.alone.dto.BoardListReplyCountDTO;
import book.alone.dto.PageResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BoardSearch {
    Page<Board> search1(Pageable pageable);

    Page<Board> searchAll(String[] types, String keyword, Pageable pageable);

    Page<BoardDTO> searchAll2(String[] types, String keyword, Pageable pageable);

    Page<BoardListReplyCountDTO> searchWithReplyCount(String[] types, String keyword, Pageable pageable);

    public Board findByIdFetch(Long id) ;


    Page<BoardListAllDTO> searchWithAll(String[] types, String keyword, Pageable pageable);
}
