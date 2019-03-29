package es.leanmind.barbershop.helpers;

import es.leanmind.barbershop.domain.AppointmentService;
import es.leanmind.barbershop.domain.EstablishmentService;
import es.leanmind.barbershop.infrastructure.PostgresEstablishmentRepository;
import es.leanmind.barbershop.infrastructure.PostgresAppointmentRepository;

public class TestFactory {

    public static EstablishmentService establishmentService(String connection) {
        return new EstablishmentService(new PostgresEstablishmentRepository(connection));
    }

    public static AppointmentService appointmentService(String connection) {
        return new AppointmentService(new PostgresAppointmentRepository(connection));
    }
}
