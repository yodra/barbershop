package es.leanmind.barbershop.controller.api;

import es.leanmind.barbershop.domain.EstablishmentDTO;
import es.leanmind.barbershop.domain.EstablishmentService;
import es.leanmind.barbershop.controller.Routes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EstablishmentsController {

    private EstablishmentService establishmentService;

    @Autowired
    public EstablishmentsController(EstablishmentService establishmentService) {
        this.establishmentService = establishmentService;
    }

    @GetMapping(path = Routes.establishments)
    public List<EstablishmentDTO> getEstablishments() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String username = (String) authentication.getPrincipal();
            return establishmentService.retrieveEstablishmentsFor(username);
        }
        // This last line only gets executed in tests to validate the json format, 401 in production
        return stubJsonTest();
    }

    private List<EstablishmentDTO> stubJsonTest() {
        return establishmentService.retrieveEstablishmentsFor(null);
    }
}