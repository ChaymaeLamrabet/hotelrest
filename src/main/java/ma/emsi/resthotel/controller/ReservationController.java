package ma.emsi.resthotel.controller;

import lombok.RequiredArgsConstructor;
import ma.emsi.resthotel.services.ReservationService;
import ma.emsi.resthotel.dto.Req.ReservationReqDTO;
import ma.emsi.resthotel.dto.Res.ReservationResDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    public ResponseEntity<List<ReservationResDTO>> getAllReservations() {
        List<ReservationResDTO> reservations = reservationService.findAll();
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<ReservationResDTO> createReservation(@RequestBody ReservationReqDTO reservationReqDTO) {
        Optional<ReservationResDTO> savedReservation = reservationService.save(reservationReqDTO);
        return savedReservation.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationResDTO> updateReservation(@PathVariable Long id,
                                                               @RequestBody ReservationReqDTO reservationReqDTO) {
        Optional<ReservationResDTO> updatedReservation = reservationService.update(reservationReqDTO, id);
        return updatedReservation.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReservationResDTO> deleteReservation(@PathVariable Long id) {
        Optional<ReservationResDTO> deletedReservation = reservationService.delete(id);
        return deletedReservation.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResDTO> getReservationById(@PathVariable Long id) {
        Optional<ReservationResDTO> reservationResponseDTO = reservationService.findById(id);
        return reservationResponseDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }


}
