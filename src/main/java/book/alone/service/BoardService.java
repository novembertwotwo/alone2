package book.alone.service;

import book.alone.dto.*;

public interface BoardService {
    Long register(BoardDTO boardDto);

    BoardDTO read(Long bno);

    void modify(BoardDTO boardDto);

    void remove(Long bno);

    PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDto);
    PageResponseDTO<BoardDTO> list2(PageRequestDTO pageRequestDto);

    PageResponseDTO<BoardListReplyCountDTO> listWithReplyCount(PageRequestDTO pageRequestDto);

    PageResponseDTO<BoardListAllDTO> listWithAll(PageRequestDTO pageRequestDTO);



}
