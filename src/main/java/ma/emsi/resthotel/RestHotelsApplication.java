package ma.emsi.resthotel;

import ma.emsi.resthotel.services.ChambreService;
import ma.emsi.resthotel.services.ClientService;
import ma.emsi.resthotel.services.ReservationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import lombok.RequiredArgsConstructor;
import ma.emsi.resthotel.dto.Req.ClientReqDTO;
import ma.emsi.resthotel.dto.Req.ChambreReqDTO;
import ma.emsi.resthotel.dto.Req.ReservationReqDTO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@RequiredArgsConstructor
public class RestHotelsApplication {

    private final ClientService clientService;
    private final ChambreService chambreService;
    private final ReservationService reservationService;

    public static void main(String[] args) {
        SpringApplication.run(RestHotelsApplication.class, args);
    }

    @Bean
    public CommandLineRunner run() {
        return args -> {
            for (int i = 1; i <= 5; i++) {
                clientService.save(new ClientReqDTO("NomClient" + i, "client" + i + "@gmail.com", "0612345678"));
            }

            for (int i = 1; i <= 3; i++) {
                chambreService.save(new ChambreReqDTO(Long.valueOf(i),  "DOUBLE" , 300.0 + i, true));
            }
            for (int i = 1; i <= 2; i++) {
                chambreService.save(new ChambreReqDTO(Long.valueOf(i),  "SIMPLE" , 150.0 + i, true));
            }

            for (int i = 1; i <= 4; i++) {
                reservationService.save(new ReservationReqDTO(Long.valueOf(i), Long.valueOf(i), "2024-12-01", "2024-12-05","TEXT"));
            }

        };
    }
}
