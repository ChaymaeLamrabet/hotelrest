package ma.emsi.resthotel.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.emsi.resthotel.entities.Chambre;
import ma.emsi.resthotel.entities.Reservation;
import ma.emsi.resthotel.entities.TypeChambre;
import ma.emsi.resthotel.map.ChambreMap;
import ma.emsi.resthotel.repositories.ChambreRepository;
import ma.emsi.resthotel.repositories.ReservationRepository;
import ma.emsi.resthotel.dto.Req.ChambreReqDTO;
import ma.emsi.resthotel.dto.Res.ChambreResDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ChambreService implements ServiceMetier<Chambre, ChambreResDTO, ChambreReqDTO> {

    private final ChambreRepository chambreRepository;
    private final ReservationRepository reservationRepository;

    @Override
    public Optional<ChambreResDTO> findById(Long id) {
        log.info("chambre with id: {}", id);
        Chambre result=chambreRepository.findById(id).orElseThrow(() -> {
            log.error("Chambre with id {} not found", id);
            return new RuntimeException("Chambre with id " + id + " does not exist");
        });
        return Optional.of(ChambreMap.toResponseDTO(result));
    }

    @Override
    public List<ChambreResDTO> findAll() {
        log.info("all chambres");
        return chambreRepository.findAll().stream().map(ChambreMap::toResponseDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<ChambreResDTO> save(ChambreReqDTO chambre) {
        if (chambre.id() != null && chambreRepository.existsById(chambre.id())) {
            log.error("Chambre with id {} exists", chambre.id());
            throw new RuntimeException("Chambre with id " + chambre.id() + " already exists");
        }
        Chambre saved = ChambreMap.toEntity(chambre);
        log.info(" new chambre: {}", chambre);
        Chambre result=chambreRepository.save(saved);
        return Optional.of(ChambreMap.toResponseDTO(result));
    }

    @Override
    public Optional<ChambreResDTO> update(ChambreReqDTO chambre, Long id) {
        log.info("Updating chambre with id: {}", id);
        Chambre existingChambre = chambreRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Chambre with id {} not found", id);
                    return new RuntimeException("Chambre with id " + id + " does not exist");
                });
        if (chambre.prix()!=null && !chambre.prix().equals(existingChambre.getPrix())){
            existingChambre.setPrix(chambre.prix());

        }
        if (chambre.disponible()!=null && !chambre.disponible().equals(existingChambre.getDisponible())){
            existingChambre.setDisponible(chambre.disponible());

        }
        if (chambre.type() != null){
            existingChambre.setType(TypeChambre.valueOf(chambre.type()));

        }
        log.info("Chambre with id {} updated ", id);
        Chambre result=chambreRepository.save(existingChambre);
        return Optional.of(ChambreMap.toResponseDTO(result));
    }

    @Override
    public Optional<ChambreResDTO> delete(Long id) {
        log.info("Deleting chambre with id: {}", id);
        Chambre existingChambre = chambreRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Chambre with id {} not found", id);
                    return new RuntimeException("Chambre with id " + id + " does not exist");
                });
        List<Reservation> reservations = reservationRepository.findByChambre(existingChambre);
        log.info("Found {} reservations associated with chambre id: {}", reservations.size(), id);
        for (Reservation reservation : reservations) {
            log.info("Deleting reservation with id: {}", reservation.getId());
            reservationRepository.delete(reservation);
        }
        chambreRepository.delete(existingChambre);
        log.info("Chambre with id {} deleted successfully", id);
        return Optional.of(ChambreMap.toResponseDTO(existingChambre));
    }

    public List<ChambreResDTO> findByDisponibilite(boolean isDisponible) {
        log.info("chambres with dispo: {}", isDisponible);
        List<Chambre> chambres = chambreRepository.findByDisponible(isDisponible);
        log.info("Found {} chambres with dispo: {}", chambres.size(), isDisponible);
        return chambres.stream().map(ChambreMap::toResponseDTO).collect(Collectors.toList());
    }
}
