package mobi.foo.bookCatalog.book.repository;

import mobi.foo.bookCatalog.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
