package quarkus.rest;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import quarkus.dto.Book;
import quarkus.repositiory.BookRepository;

import java.util.List;

@Path("/books")
@Transactional
public class BookRest {

    @Inject
    private BookRepository bookRepository;

    @GET
    public List<Book> index() {
        return bookRepository.listAll();
    }

    @POST
    public Book insert(Book insertedBook) {
        assert insertedBook.getId() == null;
        bookRepository.persist(insertedBook);
        return insertedBook;
    }

}
