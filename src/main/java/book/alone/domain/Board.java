package book.alone.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Length;

import javax.lang.model.element.NestingKind;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Board extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "board_id")
    private Long bno;

    @Column(length = 500,nullable = false)
    private String title;
    @Column(length = 20000,nullable = false)
    private String content;
    @Column(length = 50,nullable = false)
    private String writer;

    public void change(String title,String content) {
        this.title = title;
        this.content = content;
    }
}
