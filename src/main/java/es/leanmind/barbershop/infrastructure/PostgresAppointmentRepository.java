package es.leanmind.barbershop.infrastructure;

import es.leanmind.barbershop.domain.AppointmentRepository;
import org.sql2o.Sql2o;

import java.sql.SQLException;
import java.sql.Timestamp;

public class PostgresAppointmentRepository implements AppointmentRepository {

    private Sql2o sql2o;

    public PostgresAppointmentRepository(String connectionUrl) {
        sql2o = new Sql2o(connectionUrl, "barbershop", "12345");
    }


    @Override
    public void create(String user, Timestamp appointmentTime) throws SQLException {

    }

    @Override
    public boolean haveAppointment(String user) throws SQLException {
        //consulta bbdd
        return false;
    }


}
