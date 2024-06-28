package quarkus.dto;

import jakarta.validation.constraints.NotBlank;
import quarkus.entity.Genre;

public record CreateGenreDto (
        @NotBlank
        String name
){

}
