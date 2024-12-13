package ma.emsi.resthotel.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.emsi.resthotel.dto.Res.ClientResDTO;
import ma.emsi.resthotel.entities.Client;
import ma.emsi.resthotel.entities.Reservation;
import ma.emsi.resthotel.map.ClientMap;
import ma.emsi.resthotel.repositories.ReservationRepository;
import ma.emsi.resthotel.dto.Req.ClientReqDTO;
import ma.emsi.resthotel.repositories.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ClientService implements ServiceMetier<Client, ClientResDTO, ClientReqDTO> {

    private final ClientRepository clientRepository;
    private final ReservationRepository reservationRepository;

    @Override
    public Optional<ClientResDTO> findById(Long id) {
        log.info("Fetching client with id: {}", id);
        Client client = clientRepository.findById(id).orElseThrow(() -> {
            log.warn("Client with id {} not found", id);
            return new RuntimeException("Client not found");
        });
        log.info("Client found: {}", client.getId());
        return Optional.of(ClientMap.toResponseDTO(client));
    }

    public List<ClientResDTO> findByEmailOrName(String keyword) {
        log.info("Searching clients by name or email with keyword: {}", keyword);
        List<Client> clients = clientRepository.findByNomContainingIgnoreCaseOrEmailContainingIgnoreCase(keyword, keyword);
        if (clients.isEmpty()) {
            log.warn("No clients found for keyword: {}", keyword);
        } else {
            log.info("{} client(s) found for keyword: {}", clients.size(), keyword);
        }
        return clients.stream()
                .map(ClientMap::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClientResDTO> findAll() {
        log.info("Fetching all clients");
        List<Client> clients = clientRepository.findAll();
        log.info("Found {} clients", clients.size());
        return clients.stream()
                .map(ClientMap::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ClientResDTO> save(ClientReqDTO clientReqDTO) {
        log.info("Attempting to save client: {}", clientReqDTO);
        Client client = ClientMap.toEntity(clientReqDTO);
        Client savedClient = clientRepository.save(client);
        log.info("Client saved successfully: {}", savedClient);
        return Optional.of(ClientMap.toResponseDTO(savedClient));
    }

    @Override
    public Optional<ClientResDTO> update(ClientReqDTO clientReqDTO, Long id) {
        log.info("Attempting to update client with id: {}", id);
        Client existingClient = clientRepository.findById(id).orElseThrow(() -> {
            log.error("Client with id {} not found for update", id);
            return new RuntimeException("Client not found");
        });
        if(!clientReqDTO.nom().isEmpty()){
            existingClient.setNom(clientReqDTO.nom());

        }
        if(!clientReqDTO.email().isEmpty()){
            existingClient.setEmail(clientReqDTO.email());

        }
        if(!clientReqDTO.tel().isEmpty()){
            existingClient.setTel(clientReqDTO.tel());

        }

        Client updatedClient = clientRepository.save(existingClient);
        log.info("Client updated successfully: {}", updatedClient);
        return Optional.of(ClientMap.toResponseDTO(updatedClient));
    }

    @Override
    public Optional<ClientResDTO> delete(Long id) {
        log.info("Attempting to delete client with id: {}", id);

        Client client = clientRepository.findById(id).orElseThrow(() -> {
            log.error("Client with id {} not found for deletion", id);
            return new RuntimeException("Client not found");
        });

        List<Reservation> reservations = reservationRepository.findByClient(client);
        log.info("Found {} reservations associated with client id: {}", reservations.size());

        // Suppression des réservations associées
        for (Reservation reservation : reservations) {
            log.info("Deleting reservation with id: {}", reservation.getId());
            reservationRepository.delete(reservation);
        }

        clientRepository.delete(client);
        log.info("Client deleted successfully: {}", client);
        return Optional.of(ClientMap.toResponseDTO(client));
    }
}
