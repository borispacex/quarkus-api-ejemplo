package quarkus.service;

import jakarta.enterprise.context.ApplicationScoped;
import quarkus.interfaces.ITemperaturaService;
import quarkus.model.Temperatura;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class TemperaturaService implements ITemperaturaService {
    private List<Temperatura> valores = new ArrayList<>();

    public void addTemperatura(Temperatura t) {
        valores.add(t);
    }

    public List<Temperatura> obtenerTemperaturas() {
        return Collections.unmodifiableList(valores);
    }

    @Override
    public Optional<Temperatura> sacarTemperatura(String ciudad) {
        return valores.stream()
                .filter(t -> t.getCiudad().equals(ciudad))
                .findAny();
    }

    @Override
    public boolean isEmpty() {
        return valores.isEmpty();
    }

    public int maxima() {
        return valores.stream().mapToInt(Temperatura::getMaxima).max().getAsInt();
    }
}
