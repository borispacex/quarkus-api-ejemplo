package quarkus.rest;

import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import quarkus.entity.Genre;
import quarkus.repositiory.GenreRepository;
import quarkus.response.PaginatedResponse;

import java.net.URI;
import java.util.NoSuchElementException;

@Path("/genres")
@Transactional
public class GenreRest {

    @Inject
    private GenreRepository genreRepository;

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
    public Response create(Genre genre) {
        genreRepository.persist(genre);
        return Response.created(URI.create("/genres/" + genre.getId())).entity(genre).build();
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
    public Genre update(@PathParam("id") Long id, Genre genre) {
        Genre found = genreRepository
                .findByIdOptional(id)
                .orElseThrow(() -> new NoSuchElementException("Genre " + id + " no encontrado"));
        found.setName(genre.getName());
        genreRepository.persist(found);
        return found;
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Long id) {
        genreRepository.deleteById(id);
    }



}
