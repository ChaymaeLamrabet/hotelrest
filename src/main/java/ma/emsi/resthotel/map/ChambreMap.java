package ma.emsi.resthotel.map;

import ma.emsi.resthotel.entities.Chambre;
import ma.emsi.resthotel.entities.TypeChambre;
import ma.emsi.resthotel.dto.Req.ChambreReqDTO;
import ma.emsi.resthotel.dto.Res.ChambreResDTO;
import org.springframework.stereotype.Component;

@Component
public class ChambreMap {

    public static ChambreResDTO toResponseDTO(Chambre chambre) {
        return ChambreResDTO.builder()
                .id(chambre.getId())
                .type(chambre.getType().toString())
                .prix(chambre.getPrix())
                .disponible(chambre.getDisponible())
                .build();
    }

    public static Chambre toEntity(ChambreReqDTO dto) {
        return Chambre.builder()
                .id(dto.id())
                .type(TypeChambre.valueOf(dto.type()))
                .prix(dto.prix())
                .disponible(dto.disponible())
                .build();
    }
}

