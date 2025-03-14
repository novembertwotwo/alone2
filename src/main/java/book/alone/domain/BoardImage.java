package book.alone.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.util.Comparator;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "board")
public class BoardImage implements Comparable<BoardImage> {

    @Id
    private String uuid;

    private String fileName;

    private int ord;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;


    @Override
    public int compareTo(BoardImage other) {
        return this.ord - other.ord;
    }

    public void changeBoard(Board board) {
        this.board = board;
    }
}
