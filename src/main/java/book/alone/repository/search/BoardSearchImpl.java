package book.alone.repository.search;

import book.alone.domain.Board;
import book.alone.domain.QBoard;
import book.alone.dto.BoardDto;
import book.alone.dto.QBoardDto;
import com.querydsl.core.BooleanBuilder;
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
        long count = queryFactory.selectFrom(board).where(booleanBuilder).where(board.bno.gt(0L)).fetchCount();

        return new PageImpl<>(list, pageable, count);
    }
    @Override
    public Page<BoardDto> searchAll2(String[] types, String keyword, Pageable pageable) {

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

        List<BoardDto> content = queryFactory
                .select(new QBoardDto(board.bno, board.title, board.content, board.writer, board.regDate, board.modDate))
                .from(board)
                .where(booleanBuilder)
                .where(board.bno.gt(0L))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(board.bno.asc())
                .fetch();
        JPAQuery<BoardDto> countQuery = queryFactory
                .select(new QBoardDto(board.bno, board.title, board.content, board.writer, board.regDate, board.modDate))
                .from(board)
                .where(booleanBuilder)
                .where(board.bno.gt(0L));
        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }


}
