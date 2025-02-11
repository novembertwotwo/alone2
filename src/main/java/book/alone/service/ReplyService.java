package book.alone.service;

import book.alone.domain.Reply;
import book.alone.dto.*;

public interface ReplyService {

    Long register(ReplyDTO replyDTO);


    void remove(Long rno);

    void modify(ReplyDTO replyDTO);

    ReplyDTO read(Long rno);

    PageResponseDTO<ReplyDTO> getListOfBoard(Long bno, PageRequestDTO pageRequestDTO);

}
