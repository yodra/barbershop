package es.leanmind.barbershop.helpers;

import es.leanmind.barbershop.domain.EstablishmentService;
import es.leanmind.barbershop.infrastructure.PostgresEstablishmentRepository;

public class TestFactory {

    public static EstablishmentService establishmentService(String connection) {
        return new EstablishmentService(new PostgresEstablishmentRepository(connection));
    }
}
