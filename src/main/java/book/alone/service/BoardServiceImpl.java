package book.alone.service;

import book.alone.domain.Board;
import book.alone.dto.*;

import book.alone.repository.BoardRepository;
import book.alone.repository.search.BoardSearch;
import book.alone.repository.search.BoardSearchImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.security.PrivateKey;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService{
    private final ModelMapper modelMapper;
    private final BoardRepository boardRepository;
    private final BoardSearch boardSearch;


    @Override
    public Long register(BoardDTO boardDto) {
        Board board = dtoToEntity(boardDto);
        return boardRepository.save(board).getBno();
    }

    @Override
    public BoardDTO read(Long bno) {
        Optional<Board> result = boardRepository.findById(bno);
        Board board = result.orElseThrow();
        BoardDTO boardDto = modelMapper.map(board, BoardDTO.class);
        return boardDto;
    }

    @Override
    public void modify(BoardDTO boardDto) {
        Optional<Board> result = boardRepository.findById(boardDto.getBno());
        Board board = result.orElseThrow();
        board.change(boardDto.getTitle(), boardDto.getContent());
        boardRepository.save(board);
    }

    @Override
    public void remove(Long bno) {
        boardRepository.deleteById(bno);
    }

    @Override
    public PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDto) {
        String[] types = pageRequestDto.getTypes();
        String keyword = pageRequestDto.getKeyword();
        Pageable pageable = pageRequestDto.getPageable();
        Page<Board> result = boardSearch.searchAll(types, keyword, pageable);
        List<BoardDTO> dtoList = result.getContent().stream()
                .map(board -> modelMapper.map(board, BoardDTO.class))
                .collect(Collectors.toList());
        return PageResponseDTO.<BoardDTO>withAll()
                .pageRequestDto(pageRequestDto)
                .dtoList(dtoList)
                .total((int) result.getTotalElements())
                .build();
    }
    @Override
    public PageResponseDTO<BoardDTO> list2(PageRequestDTO pageRequestDto) {
        String[] types = pageRequestDto.getTypes();
        String keyword = pageRequestDto.getKeyword();
        Pageable pageable = pageRequestDto.getPageable();
        Page<BoardDTO> list = boardSearch.searchAll2(types, keyword, pageable);

        return PageResponseDTO.<BoardDTO>withAll()
                .pageRequestDto(pageRequestDto)
                .dtoList(list.getContent())
                .total((int) list.getTotalElements())
                .build();
    }

    @Override
    public PageResponseDTO<BoardListReplyCountDTO> listWithReplyCount(PageRequestDTO pageRequestDTO) {
        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("bno");
        Page<BoardListReplyCountDTO> result = boardSearch.searchWithReplyCount(types, keyword, pageable);


        return PageResponseDTO.<BoardListReplyCountDTO>withAll().pageRequestDto(pageRequestDTO)
                .dtoList(result.getContent())
                .total((int) result.getTotalElements())
                .build();
    }

    @Override
    public PageResponseDTO<BoardListAllDTO> listWithAll(PageRequestDTO pageRequestDTO) {
        return null;
    }

}
