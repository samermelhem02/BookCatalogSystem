package mobi.foo.bookCatalog.book.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class BookDTO implements Serializable {
    private long id;
    private String title;
    private String author;
    private String publicationYear;

    public BookDTO(){}
}
