package es.leanmind.barbershop.domain;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class EstablishmentService {

    private final EstablishmentRepository establishmentRepository;

    @Autowired
    public EstablishmentService(EstablishmentRepository establishmentRepository) {
        this.establishmentRepository = establishmentRepository;
    }

    public void create(String name, Credentials webCredentials) throws SQLException {
        String hashedWebPassword = BCrypt.hashpw(webCredentials.password, BCrypt.gensalt(12));
        Owners owners = new Owners(webCredentials.username, hashedWebPassword);
        Establishment establishment = new Establishment(name, owners);
        establishmentRepository.create(establishment);
    }

    public List<EstablishmentDTO> retrieveEstablishmentsFor(String username) {
        return establishmentRepository.retrieveEstablishmentsFor(username);
    }

    public boolean isOwnerAllowed(String username, String password) {
        String retrievedPasswordHash = establishmentRepository.getOwnerHashedPassword(username);
        return BCrypt.checkpw(password, retrievedPasswordHash);
    }
}
