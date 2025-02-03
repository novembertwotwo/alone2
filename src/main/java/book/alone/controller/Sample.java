package book.alone.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class Sample {
    int id;
    String string;

    public Sample(int id, String string) {
        this.id = id;
        this.string = string;
    }
}
