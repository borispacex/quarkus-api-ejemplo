package quarkus.mapper;

import quarkus.dto.CreateGenreDto;
import quarkus.dto.UpdateGenreDto;
import quarkus.entity.Genre;

public interface IGenreMapper {

    Genre fromCreate(CreateGenreDto dto);

    void update(UpdateGenreDto dto, Genre genre);

}
