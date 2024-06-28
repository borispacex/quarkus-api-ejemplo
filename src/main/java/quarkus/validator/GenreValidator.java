package quarkus.validator;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Validator;
import quarkus.dto.CreateGenreDto;

import java.util.Optional;

@ApplicationScoped
public class GenreValidator {

    Validator validator;

    @Inject
    public GenreValidator(Validator validator) {
        this.validator = validator;
    }

    public Optional<String> validateGenre(CreateGenreDto genre) {
        var errors = validator.validate(genre);
        if (errors.isEmpty()) return Optional.empty();
        return Optional.of(errors.stream().findFirst().get().getMessage());
    }


}
