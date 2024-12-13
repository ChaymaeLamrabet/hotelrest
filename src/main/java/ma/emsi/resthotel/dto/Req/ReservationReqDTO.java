package ma.emsi.resthotel.dto.Req;

;


public record ReservationReqDTO(
        Long clientId,
        Long chambreId,
        String dateDebut,
        String dateFin,
        String preferences
) {}
