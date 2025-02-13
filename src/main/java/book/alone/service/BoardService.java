package book.alone.service;

import book.alone.domain.Board;
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

    default Board dtoToEntity(BoardDTO boardDTO) {
        Board board = Board.builder()
                .bno(boardDTO.getBno())
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .writer(boardDTO.getWriter())
                .build();
        if (boardDTO.getFileNames() != null) {
            boardDTO.getFileNames().forEach(fileName->{
                String[] arr = fileName.split("_");
                board.addImage(arr[0],arr[1]);
            });
        }
        return board;
    }



}
