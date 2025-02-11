package book.alone.repository.search;

import book.alone.domain.Board;
import book.alone.domain.QBoard;
import book.alone.dto.BoardDTO;
import book.alone.dto.BoardListReplyCountDTO;
import book.alone.dto.QBoardDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static book.alone.domain.QBoard.board;
import static book.alone.domain.QReply.reply;

@Slf4j
@RequiredArgsConstructor
public class BoardSearchImpl implements BoardSearch {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Board> search1(Pageable pageable) {
        QBoard board = QBoard.board;
        List<Board> list = queryFactory.selectFrom(board)
                .where(board.title.contains("1"))
                .fetch();
        long count = list.size();
        return null;
    }

    @Override
    public Page<Board> searchAll(String[] types, String keyword, Pageable pageable) {
        QBoard board = QBoard.board;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if ((types != null&& types.length > 0) && keyword != null) {
            for (String type : types) {
                switch (type) {
                    case "t":
                        booleanBuilder.or(board.title.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(board.content.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(board.writer.contains(keyword));
                        break;
                }
            }
        }

        List<Board> list = queryFactory
                .selectFrom(board)
                .where(booleanBuilder)
                .where(board.bno.gt(0L))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(board.bno.asc())
                .fetch();
        Long count = queryFactory
                .select(board.count())
                .from(board)
                .where(booleanBuilder)
                .where(board.bno.gt(0L))
                .fetchOne();

        return new PageImpl<>(list, pageable, count);
    }
    @Override
    public Page<BoardDTO> searchAll2(String[] types, String keyword, Pageable pageable) {

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if ((types != null&& types.length > 0) && keyword != null) {
            for (String type : types) {
                switch (type) {
                    case "t":
                        booleanBuilder.or(board.title.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(board.content.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(board.writer.contains(keyword));
                        break;
                }
            }
        }

        List<BoardDTO> content = queryFactory
                .select(new QBoardDTO(board.bno, board.title, board.content, board.writer, board.regDate, board.modDate))
                .from(board)
                .where(booleanBuilder)
                .where(board.bno.gt(0L))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(board.bno.asc())
                .fetch();
        JPAQuery<Long> countQuery = queryFactory
                .select(board.count())
                .from(board)
                .where(booleanBuilder)
                .where(board.bno.gt(0L));
        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public Page<BoardListReplyCountDTO> searchWithReplyCount(String[] types, String keyword, Pageable pageable) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if ((types != null&& types.length > 0) && keyword != null) {
            for (String type : types) {
                switch (type) {
                    case "t":
                        booleanBuilder.or(board.title.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(board.content.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(board.writer.contains(keyword));
                        break;
                }
            }
        }

        List<BoardListReplyCountDTO> content = queryFactory
                .select(Projections.constructor(BoardListReplyCountDTO.class
                        ,board.bno
                        ,board.title
                        ,board.writer
                        ,board.regDate
                        ,reply.count()
                ))
                .from(board)
                .leftJoin(reply).fetchJoin()
                .on(reply.board.eq(board))
                .where(booleanBuilder)
                .where(board.bno.gt(0L))
                .groupBy(board.bno, board.title, board.writer, board.regDate)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        JPAQuery<Long> countQuery = queryFactory
                .select(board.count())
                .from(board)
                .where(booleanBuilder)
                .where(board.bno.gt(0L));
        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }
}
