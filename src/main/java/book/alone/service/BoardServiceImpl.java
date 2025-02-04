package book.alone.service;

import book.alone.domain.Board;
import book.alone.dto.BoardDto;
import book.alone.dto.PageRequestDto;
import book.alone.dto.PageResponseDto;
import book.alone.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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


    @Override
    public Long register(BoardDto boardDto) {
        Board board = modelMapper.map(boardDto, Board.class);
        Long bno = boardRepository.save(board).getBno();
        return bno;
    }

    @Override
    public BoardDto read(Long bno) {
        Optional<Board> result = boardRepository.findById(bno);
        Board board = result.orElseThrow();
        BoardDto boardDto = modelMapper.map(board, BoardDto.class);
        return boardDto;
    }

    @Override
    public void modify(BoardDto boardDto) {
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
    public PageResponseDto<BoardDto> list(PageRequestDto pageRequestDto) {
        String[] types = pageRequestDto.getTypes();
        String keyword = pageRequestDto.getKeyword();
        Pageable pageable = pageRequestDto.getPageable();
        Page<Board> result = boardRepository.searchAll(types, keyword, pageable);
        List<BoardDto> dtoList = result.getContent().stream()
                .map(board -> modelMapper.map(board, BoardDto.class))
                .collect(Collectors.toList());
        return PageResponseDto.<BoardDto>withAll()
                .pageRequestDto(pageRequestDto)
                .dtoList(dtoList)
                .total((int) result.getTotalElements())
                .build();
    }


}
