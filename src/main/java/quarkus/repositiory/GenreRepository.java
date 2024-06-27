package quarkus.repositiory;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import quarkus.entity.Genre;

@ApplicationScoped
public class GenreRepository  implements PanacheRepository<Genre> {



}
