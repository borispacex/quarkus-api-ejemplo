package quarkus.mapper;

import jakarta.enterprise.context.RequestScoped;
import quarkus.dto.CreateGenreDto;
import quarkus.dto.UpdateGenreDto;
import quarkus.entity.Genre;

@RequestScoped
public class GenreMapper implements IGenreMapper{

    @Override
    public Genre fromCreate(CreateGenreDto dto) {
        var g = new Genre();
        g.setName(dto.name());
        return g;
    }

    @Override
    public void update(UpdateGenreDto dto, Genre genre) {
        genre.setName(dto.name());
    }

}
