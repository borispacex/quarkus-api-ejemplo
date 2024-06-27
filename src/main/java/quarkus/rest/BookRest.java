package quarkus.rest;

import io.quarkus.panache.common.Sort;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import quarkus.entity.Book;
import quarkus.entity.BookPanache;
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

    @GET
    @Path("/filter")
    public List<Book> indexFilter(@QueryParam("numPages") Integer numPages) {
        if (numPages == null) return bookRepository.listAll(Sort.by("pubDate", Sort.Direction.Descending));
        return bookRepository.list("numPages >= ?1", numPages);
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
