package book.alone.service;

import book.alone.domain.Board;
import book.alone.dto.BoardDto;
import book.alone.dto.PageRequestDto;
import book.alone.dto.PageResponseDto;

public interface BoardService {
    Long register(BoardDto boardDto);

    BoardDto read(Long bno);

    void modify(BoardDto boardDto);

    void remove(Long bno);

    PageResponseDto<BoardDto> list(PageRequestDto pageRequestDto);
    PageResponseDto<BoardDto> list2(PageRequestDto pageRequestDto);

}
