package ma.emsi.resthotel.controller;

import lombok.RequiredArgsConstructor;
import ma.emsi.resthotel.services.ChambreService;
import ma.emsi.resthotel.dto.Req.ChambreReqDTO;
import ma.emsi.resthotel.dto.Res.ChambreResDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/chambres")
@RequiredArgsConstructor
public class ChambreController {

    private final ChambreService chambreService;

    @GetMapping
    public ResponseEntity<List<ChambreResDTO>> getAllChambres() {
        List<ChambreResDTO> chambres = chambreService.findAll();
        return new ResponseEntity<>(chambres, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ChambreResDTO> createChambre(@RequestBody ChambreReqDTO chambreRequestDTO) {
        Optional<ChambreResDTO> savedChambre = chambreService.save(chambreRequestDTO);
        return savedChambre.map(ResponseEntity::ok)

                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ChambreResDTO> updateChambre(@PathVariable Long id,
                                                       @RequestBody ChambreReqDTO chambreRequestDTO) {
        Optional<ChambreResDTO> updatedChambre = chambreService.update(chambreRequestDTO, id);
        return updatedChambre.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ChambreResDTO> getChambreById(@PathVariable Long id) {
        Optional<ChambreResDTO> chambreResponseDTO = chambreService.findById(id);
        return chambreResponseDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }


    @GetMapping("/disponibilite/{dispo}")
    public ResponseEntity<List<ChambreResDTO>> getChambresByDisponibilite(@PathVariable boolean isDisponible) {
        List<ChambreResDTO> chambres = chambreService.findByDisponibilite(isDisponible);
        return new ResponseEntity<>(chambres, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ChambreResDTO> deleteChambre(@PathVariable Long id) {
        Optional<ChambreResDTO> deletedChambre = chambreService.delete(id);
        return deletedChambre.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }


}
