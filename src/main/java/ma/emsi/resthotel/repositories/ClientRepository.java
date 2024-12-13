package ma.emsi.resthotel.repositories;

import ma.emsi.resthotel.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findByNomContainingIgnoreCaseOrEmailContainingIgnoreCase(String nom, String email);
}
