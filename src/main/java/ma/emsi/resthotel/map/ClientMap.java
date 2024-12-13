package ma.emsi.resthotel.map;

import ma.emsi.resthotel.dto.Res.ClientResDTO;
import ma.emsi.resthotel.entities.Client;
import ma.emsi.resthotel.dto.Req.ClientReqDTO;
import org.springframework.stereotype.Component;

@Component
public class ClientMap {

    public static ClientResDTO toResponseDTO(Client client) {
        return ClientResDTO.builder()
                .id(client.getId())
                .nom(client.getNom())
                .email(client.getEmail())
                .tel(client.getTel())
                .build();
    }

    public static Client toEntity(ClientReqDTO dto) {
        return Client.builder()
                .nom(dto.nom())
                .email(dto.email())
                .tel(dto.tel())
                .build();
    }
}
