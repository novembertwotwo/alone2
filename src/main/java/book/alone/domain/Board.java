package book.alone.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Length;
import org.hibernate.annotations.BatchSize;

import javax.lang.model.element.NestingKind;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Board extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private Long bno;

    @Column(length = 500,nullable = false)
    private String title;
    @Column(length = 20000,nullable = false)
    private String content;
    @Column(length = 50,nullable = false)
    private String writer;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    @BatchSize(size = 20)
    private Set<BoardImage> imageSet = new HashSet<>();

    @Getter
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    private  List<Reply> replies = new ArrayList<>();


    public void addImage(String uuid, String fileName) {
        BoardImage boardImage = BoardImage.builder()
                .uuid(uuid)
                .fileName(fileName)
                .board(this)
                .ord(imageSet.size())
                .build();
        imageSet.add(boardImage);
    }
    public void clearImages() {
        imageSet.forEach(boardImage -> boardImage.changeBoard(null));
        this.imageSet.clear();
    }


    public void change(String title,String content) {
        this.title = title;
        this.content = content;
    }

}
