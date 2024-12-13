package ma.emsi.resthotel.dto.Req;


public record ChambreReqDTO(
        Long id,           // Chambre ID for updating
        String type,       // Chambre type
        Double prix,       // Chambre price
        Boolean disponible // Availability status of the chambre
) {}

