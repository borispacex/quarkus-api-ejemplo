package quarkus.rest;

import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import quarkus.dto.CreateGenreDto;
import quarkus.dto.UpdateGenreDto;
import quarkus.entity.Genre;
import quarkus.mapper.IGenreMapper;
import quarkus.repositiory.GenreRepository;
import quarkus.response.PaginatedResponse;

import java.net.URI;
import java.util.NoSuchElementException;

@Path("/genres")
@Transactional
public class GenreRest {

    @Inject
    private GenreRepository genreRepository;

    @Inject
    private IGenreMapper mapper;

    @GET
    public PaginatedResponse<Genre> list(
            @QueryParam("page") @DefaultValue("1") int page,
            @QueryParam("q") String q
    ) {
        var query = genreRepository.findPage(page);
        if (q != null) query.filter("name.like", Parameters.with("name", "%" + q + "%"));
        return new PaginatedResponse<>(query);
    }

    @POST
    public Response create(CreateGenreDto genre) {
        var entity = mapper.fromCreate(genre);
        genreRepository.persist(entity);
        return Response.created(URI.create("/genres/" + entity.getId())).entity(entity).build();
    }

    @GET
    @Path("{id}")
    public Genre get(@PathParam("id") Long id) {
        return genreRepository
                .findByIdOptional(id)
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
