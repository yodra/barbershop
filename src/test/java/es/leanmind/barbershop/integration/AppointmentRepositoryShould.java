package es.leanmind.barbershop.integration;

import es.leanmind.barbershop.infrastructure.PostgresAppointmentRepository;
import org.junit.Test;
import org.sql2o.Sql2o;

import java.sql.SQLException;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class AppointmentRepositoryShould {
    private PostgresAppointmentRepository appointmentRepository;
    private Sql2o sql2o;



    @Test
    public void not_exists_appointments_by_user() throws SQLException {
        String userName = "test";
        assertThat(appointmentRepository.haveAppointment(userName)).isFalse();
    }
}