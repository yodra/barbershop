package es.leanmind.barbershop.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public void create(String user, Date appointmentDate) throws SQLException {
        Timestamp appointmentTime = new Timestamp(appointmentDate.getTime());

        appointmentRepository.create(user, appointmentTime);
    }

    public boolean haveAppointment(String user) throws SQLException {
        return appointmentRepository.haveAppointment(user);
    }
}
