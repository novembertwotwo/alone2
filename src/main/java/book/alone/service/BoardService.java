package book.alone.service;

import book.alone.dto.BoardDTO;

import book.alone.dto.BoardListReplyCountDto;
import book.alone.dto.PageRequestDTO;
import book.alone.dto.PageResponseDTO;

public interface BoardService {
    Long register(BoardDTO boardDto);

    BoardDTO read(Long bno);

    void modify(BoardDTO boardDto);

    void remove(Long bno);

    PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDto);
    PageResponseDTO<BoardDTO> list2(PageRequestDTO pageRequestDto);

    PageResponseDTO<BoardListReplyCountDto> listWithReplyCount(PageRequestDTO pageRequestDto);

}
