package book.alone.service;

import book.alone.domain.Reply;
import book.alone.dto.ReplyDTO;
import book.alone.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
}
