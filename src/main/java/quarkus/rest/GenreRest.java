package quarkus.rest;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import quarkus.dto.CreateGenreDto;
import quarkus.dto.ResponseGenreDto;
import quarkus.dto.UpdateGenreDto;
import quarkus.entity.Genre;
import quarkus.mapper.IGenreMapper;
import quarkus.repositiory.GenreRepository;
import quarkus.response.PaginatedResponse;
import quarkus.validator.GenreValidator;

import java.net.URI;
import java.util.NoSuchElementException;

@Path("/genres")
@Transactional
public class GenreRest {

    @Inject
    private GenreRepository genreRepository;

    @Inject
    private IGenreMapper mapper;

    @Inject
    private GenreValidator validator;

    @GET
    public PaginatedResponse<ResponseGenreDto> list(
            @QueryParam("page") @DefaultValue("1") int page,
            @QueryParam("q") String q
    ) {

        var query = genreRepository.findPage(page);
        PanacheQuery<ResponseGenreDto> present = query.project(ResponseGenreDto.class);
        if (q != null) present.filter("name.like", Parameters.with("name", "%" + q + "%"));
        return new PaginatedResponse<>(present);
    }

    @POST
    public Response create(CreateGenreDto genre) {
        var errors = this.validator.validateGenre(genre);
        if (errors.isPresent()) return Response.status(400).entity(errors.get()).build();
        var entity = mapper.fromCreate(genre);
        genreRepository.persist(entity);
        return Response.created(URI.create("/genres/" + entity.getId())).entity(entity).build();
    }

    @GET
    @Path("{id}")
    public ResponseGenreDto get(@PathParam("id") Long id) {
        return genreRepository
                .findByIdOptional(id)
                .map(mapper::present)
                .orElseThrow(() -> new NoSuchElementException("Genre " + id + " no encontrado"));
    }

    @PUT
    @Path("{id}")
    public Genre update(@PathParam("id") Long id, UpdateGenreDto genre) {
        Genre found = genreRepository
                .findByIdOptional(id)
                .orElseThrow(() -> new NoSuchElementException("Genre " + id + " no encontrado"));
        mapper.update(genre, found);
        genreRepository.persist(found);
        return found;
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Long id) {
        genreRepository.deleteById(id);
    }



}
