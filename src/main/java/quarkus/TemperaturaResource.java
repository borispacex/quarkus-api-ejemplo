package quarkus;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.Arrays;
import java.util.List;

@Path("/temperaturas")
public class TemperaturaResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Temperatura> list() {
        return Arrays.asList(
                new Temperatura("Malaga", 18, 28),
                new Temperatura("Madrid", 10, 20),
                new Temperatura("Tenerife", 20, 30),
                new Temperatura("Bogota", -4, 5)
        );
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/una")
    public Temperatura medicion() {
        return new Temperatura("Malaga", 18, 28);
    }
}
