package es.leanmind.barbershop.domain;

import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public interface EstablishmentRepository {

    void create(Establishment establishment) throws SQLException;

    List<EstablishmentDTO> retrieveEstablishmentsFor(String username);

    String getOwnerHashedPassword(String username);
}
