package es.leanmind.barbershop.infrastructure;

import es.leanmind.barbershop.domain.EstablishmentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersistenceConfiguration {

    @Value("${spring.datasource.url}")
    private String connectionUrl;

    @Bean
    public EstablishmentRepository establishmentRepository(){
        return new PostgresEstablishmentRepository(connectionUrl);
    }

}
