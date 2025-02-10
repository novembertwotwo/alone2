//package book.alone;
//
//import book.alone.domain.Board;
//import book.alone.domain.Member;
//import book.alone.domain.Team;
//import book.alone.repository.BoardRepository;
//import jakarta.annotation.PostConstruct;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Profile;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.stream.IntStream;
//
////@Profile("local")
//@Component
//@RequiredArgsConstructor
//public class InitMember {
//    private final BoardRepository boardRepository;
//    @PostConstruct
//    public void testInsert() {
//        IntStream.rangeClosed(1, 100).forEach(i -> {
//                    Board board = Board.builder()
//                            .title("title..." + i)
//                            .content("content..." + i)
//                            .writer("user" + (i % 10)).build();
//                    Board result = boardRepository.save(board);
//                }
//        );
//    }
//}