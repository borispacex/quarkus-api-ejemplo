package quarkus.service;

import quarkus.model.Temperatura;

import java.util.List;
import java.util.Optional;

public interface ITemperaturaService {
    void addTemperatura(Temperatura t);
    List<Temperatura> obtenerTemperaturas();
    Optional<Temperatura> sacarTemperatura(String ciudad);
    boolean isEmpty();
    int maxima();
}