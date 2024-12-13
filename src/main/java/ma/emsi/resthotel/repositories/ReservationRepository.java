package ma.emsi.resthotel.repositories;

import ma.emsi.resthotel.entities.Chambre;
import ma.emsi.resthotel.entities.Client;
import ma.emsi.resthotel.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByChambre(Chambre chambre);
    List<Reservation> findByClient(Client chambre);

}
