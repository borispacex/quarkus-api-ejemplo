package quarkus.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import quarkus.model.Temperatura;
import quarkus.service.TemperaturaService;

import java.util.*;

@Path("/temperaturas")
public class TemperaturaRest {

    private TemperaturaService temperaturaService;

    @Inject
    public TemperaturaRest(TemperaturaService temperaturaService) {
        this.temperaturaService = temperaturaService;
    }

    @POST
    public Temperatura nueva(Temperatura temp) {
        this.temperaturaService.addTemperatura(temp);
        return temp;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Temperatura> list() {
        return this.temperaturaService.obtenerTemperaturas();
    }


    @GET
    @Path("/maxima")
    @Produces(MediaType.TEXT_PLAIN)
    public Response maxima() {
        if (temperaturaService.isEmpty()) {
            return Response.status(404).entity("No hay temperaturas").build();
        } else {
            int temperaturaMaxima = temperaturaService.maxima();
            return Response.ok(Integer.toString(temperaturaMaxima))
                    .header("X-Hola", "Buenos dias")
                    .build();
        }
    }

    @GET
    @Path("/{ciudad}")
    @Produces(MediaType.APPLICATION_JSON)
    public Temperatura sacar(@PathParam("ciudad") String ciudad) {
        return temperaturaService.sacarTemperatura(ciudad)
                .orElseThrow(() -> new NoSuchElementException("No hay registro para la ciudad " + ciudad));
    }


}
