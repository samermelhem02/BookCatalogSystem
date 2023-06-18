package mobi.foo.bookCatalog.book.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import mobi.foo.bookCatalog.Exception.BookNotFoundException;
import mobi.foo.bookCatalog.book.dto.BookDTO;
import mobi.foo.bookCatalog.book.entity.Book;
import mobi.foo.bookCatalog.book.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;


    private final ObjectMapper objectMapper;


    public BookDTO convertToBookDTO(Book book)
    {
        return objectMapper.convertValue(book,BookDTO.class);

    }


    public BookDTO findBookById(long id)
    {
        Optional<Book> book = bookRepository.findById(id);

        if (book.isEmpty()) {
            throw new BookNotFoundException("Book not found with ID: " + id);
        }
        //return new BookDTO((book.get()).getId(),(book.get()).getTitle(),(book.get()).getAuthor(),(book.get()).getPublicationYear());
        return convertToBookDTO(book.get());

    }

    @Cacheable("CachedBooks")
    public List<BookDTO> findAllBooks()
    {
        List<Book> books = bookRepository.findAll();
        List<BookDTO> booksDTO = new ArrayList<>();
//        books.forEach(b -> {
//            //booksDTO.add(new BookDTO(b.getId(),b.getTitle(),b.getAuthor(),b.getPublicationYear()));
//            booksDTO.add(convertToBookDTO(b));
//        });

        for(Book b : books)
        {
            booksDTO.add(convertToBookDTO(b));
        }

        return booksDTO;
    }

    @CacheEvict(value = "CachedBooks", allEntries = true)
    public void save(Book book)
    {

        bookRepository.save(book);
    }

    @CacheEvict(value = "CachedBooks", allEntries = true)
    public void delete(long id)
    {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty()) {
            throw new BookNotFoundException("Book to be deleted not found with ID: " + id);
        }
        bookRepository.deleteById(id);
    }





}
