package book.alone.service;

import book.alone.domain.Reply;
import book.alone.dto.PageRequestDTO;
import book.alone.dto.PageResponseDTO;
import book.alone.dto.ReplyDTO;
import book.alone.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Slf4j
@Service
public class ReplyServiceImpl implements ReplyService{
    private final ReplyRepository replyRepository;
    private final ModelMapper modelMapper;



    @Override
    public Long register(ReplyDTO replyDTO) {
        Reply reply = modelMapper.map(replyDTO, Reply.class);
        return replyRepository.save(reply).getRno();
    }

    @Override
    public void remove(Long rno) {
        replyRepository.deleteById(rno);
    }

    @Override
    public void modify(ReplyDTO replyDTO) {
        Optional<Reply> optionalReply = replyRepository.findById(replyDTO.getRno());
        Reply reply = optionalReply.orElseThrow();
        reply.changeText(replyDTO.getReplyText());
        replyRepository.save(reply);
    }

    @Override
    public ReplyDTO read(Long rno) {
        Optional<Reply> byId = replyRepository.findById(rno);
        Reply reply = byId.orElseThrow();
        return modelMapper.map(reply, ReplyDTO.class);
    }

    @Override
    public PageResponseDTO<ReplyDTO> getListOfBoard(Long bno, PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize(), Sort.by("rno").ascending());
        Page<Reply> result = replyRepository.listOfBoard(bno, pageable);
        List<ReplyDTO> replyDTOList = result.map(o -> modelMapper.map(o, ReplyDTO.class)).toList();
        return PageResponseDTO.<ReplyDTO>withAll()
                .dtoList(replyDTOList)
                .total((int) result.getTotalElements())
                .pageRequestDto(pageRequestDTO)
                .build();

    }
}
