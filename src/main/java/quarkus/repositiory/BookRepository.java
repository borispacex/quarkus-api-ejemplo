package quarkus.repositiory;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import quarkus.entity.Book;

@ApplicationScoped
public class BookRepository implements PanacheRepository<Book> {



}
