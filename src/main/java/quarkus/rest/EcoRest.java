package quarkus.rest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;

import java.util.Optional;

@Path("/saludar")
public class EcoRest {

    @GET
    public String saludar(@QueryParam("mensaje") String mensaje) {
        return Optional.ofNullable(mensaje)
                .map(n -> "> " + n)
                .orElse("No se muy bien que decir aqui!");
    }
    @GET
    @Path("/{nombre}")
    public String saludo(@PathParam("nombre") String nombre) {
        return "Hola, " + nombre;
    }
    @GET
    @Path("/{nombre}/mayusculas")
    public String gritar(@PathParam("nombre") String nombre) {
        return "HOLA, " + nombre.toUpperCase();
    }

}
