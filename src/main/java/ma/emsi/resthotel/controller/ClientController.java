package ma.emsi.resthotel.controller;

import lombok.RequiredArgsConstructor;
import ma.emsi.resthotel.dto.Res.ClientResDTO;
import ma.emsi.resthotel.services.ClientService;
import ma.emsi.resthotel.dto.Req.ClientReqDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping
    public ResponseEntity<List<ClientResDTO>> getAllClients() {
        List<ClientResDTO> clients = clientService.findAll();
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResDTO> getClientById(@PathVariable Long id) {
        Optional<ClientResDTO> clientResponseDTO = clientService.findById(id);
        return clientResponseDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }


    @PostMapping
    public ResponseEntity<ClientResDTO> createClient(@RequestBody ClientReqDTO clientReqDTO) {
        Optional<ClientResDTO> savedClient = clientService.save(clientReqDTO);
        return savedClient.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResDTO> updateClient(@PathVariable Long id,
                                                     @RequestBody ClientReqDTO clientReqDTO) {
        Optional<ClientResDTO> updatedClient = clientService.update(clientReqDTO, id);
        return updatedClient.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ClientResDTO> deleteClient(@PathVariable Long id) {
        Optional<ClientResDTO> deletedClient = clientService.delete(id);
        return deletedClient.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }
}
