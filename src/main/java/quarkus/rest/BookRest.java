package quarkus.rest;

import com.oracle.svm.core.annotate.Delete;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import quarkus.dto.Book;
import quarkus.dto.BookPanache;
import quarkus.repositiory.BookRepository;

import java.util.List;
import java.util.NoSuchElementException;

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

    @GET
    @Path("{id}")
    public Book retrieve(@PathParam("id") Long id) {
        var book = bookRepository.findById(id);
        if (book != null) return book;
        throw new NoSuchElementException("No hay libro con el ID " + id + ".");
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Long id) {
        bookRepository.deleteById(id);
    }

    @PUT
    @Path("{id}")
    public Book update(@PathParam("id") Long id, Book book) {
        var updateBook = bookRepository.findById(id);
        if (updateBook != null) {
            updateBook.setTitle(book.getTitle());
            updateBook.setPubDate(book.getPubDate());
            updateBook.setNumPages(book.getNumPages());
            updateBook.setDescription(book.getDescription());
            bookRepository.persist(updateBook);
            return updateBook;
        }
        throw new NoSuchElementException("No hay libro con el ID " + id + ".");
    }

    // Directo con PanacheEntity
    @GET
    @Path("/list")
    public List<BookPanache> index2() {
        return BookPanache.listAll();
    }

    @POST
    @Path("/save")
    public BookPanache insert2(BookPanache insertedBook) {
        insertedBook.persist();
        return insertedBook;
    }

}
