package org.seguritech.cp.domain.predicate;

import org.seguritech.cp.service.dto.ConsultaPlacaDTO;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

public class ConsultaPlacaPredicates {

    public static Predicate<ConsultaPlacaDTO> getIssiPredicate(Long issi) {
        return p -> p.getRadioIssi().equals(issi);
    }

    public static Predicate<ConsultaPlacaDTO> getMunicipioPredicate(String mun) {
        return p -> p.getRadioMunicipio().equals(mun);
    }

    public static Predicate<ConsultaPlacaDTO> getCorporacionPredicate(String corp) {
        return p -> p.getRadioCorporacion().equals(corp);
    }

    public static Predicate<ConsultaPlacaDTO> getEstadoPredicate(Boolean estado) {
        return p -> p.isEstado().equals(estado);
    }

    public static Predicate<ConsultaPlacaDTO> getDesdePredicate(LocalDateTime desde) {
        return p -> p.getFecha().isAfter(desde) && p.getFecha().isBefore(desde);
    }

    public static Predicate<ConsultaPlacaDTO> getHastaPredicate(LocalDateTime hasta) {
        return p -> p.getFecha().isBefore(hasta) && p.getFecha().isAfter(hasta);
    }

    public static Predicate<ConsultaPlacaDTO> getDesdeHastaPredicate(LocalDateTime desde, LocalDateTime hasta) {
        return p -> p.getFecha().isAfter(desde) && p.getFecha().isBefore(hasta);
    }




    public static List<ConsultaPlacaDTO> filterConsultaPlacas (List<ConsultaPlacaDTO> consultaPlacaDTOS, Predicate<ConsultaPlacaDTO> predicate) {
        return consultaPlacaDTOS.stream().filter( predicate ).collect(Collectors.<ConsultaPlacaDTO>toList());
    }
}
