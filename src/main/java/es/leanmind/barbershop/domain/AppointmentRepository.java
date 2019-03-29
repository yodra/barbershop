package es.leanmind.barbershop.domain;

import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.sql.Timestamp;

@Repository
public interface AppointmentRepository {

    void create(String user, Timestamp appointmentTime) throws SQLException;

    boolean haveAppointment(String user) throws SQLException;
}
