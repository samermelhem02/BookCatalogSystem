package mobi.foo.bookCatalog.book.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Table(name = "Book")
@Entity
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Book title is required")
    private String title;

    @NotNull(message = "Book author is required")
    private String author;

    @NotNull(message = "Book publication year is required")
    private String publicationYear;



}
