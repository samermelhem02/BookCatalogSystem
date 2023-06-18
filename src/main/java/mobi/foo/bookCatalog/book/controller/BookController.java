package mobi.foo.bookCatalog.book.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mobi.foo.bookCatalog.FooResponse;
import mobi.foo.bookCatalog.book.dto.BookDTO;
import mobi.foo.bookCatalog.book.entity.Book;
import mobi.foo.bookCatalog.book.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RequiredArgsConstructor
@RestController
@Validated
@Tag(name = "Book Controller", description = "API endpoints for the Book Catalog")
public class BookController {

    private final BookService bookService;

    @Operation(summary = "Get a Book by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/book/{id}")
    public ResponseEntity<FooResponse> findBookById(@PathVariable long id)
    {
        BookDTO bookDTO = bookService.findBookById(id);
        FooResponse fooResponse = FooResponse.builder().data(bookDTO)
                .message("Found a book of id : "+ id).statues(true).build();
        return ResponseEntity.ok(fooResponse);
    }

    @Operation(summary = "Get all Books")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Books retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/books")
    public ResponseEntity<FooResponse> findAllBooks()
    {
        List<BookDTO> bookDTOList = bookService.findAllBooks();
        FooResponse fooResponse = FooResponse.builder().data(bookDTOList)
                .message("Found all books").statues(true).build();
        return ResponseEntity.ok(fooResponse);
    }

    @Operation(summary = "Create OR Update a Book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book saved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/book/create")
    public ResponseEntity<FooResponse> save(@Valid @RequestBody Book book)
    {
        bookService.save(book);
        FooResponse fooResponse = FooResponse.builder().data(book)
                .message("saved a book").statues(true).build();
        return ResponseEntity.ok(fooResponse);
    }

    @Operation(summary = "Delete a book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/book/delete/{id}")
    public ResponseEntity<FooResponse> delete(@PathVariable long id)
    {
        bookService.delete(id);
        FooResponse fooResponse = FooResponse.builder()
                .message("Deleted a book of id : " + id).statues(true).build();
        return ResponseEntity.ok(fooResponse);
    }



}
