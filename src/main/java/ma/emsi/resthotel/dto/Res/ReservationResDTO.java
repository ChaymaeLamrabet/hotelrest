package ma.emsi.resthotel.dto.Res;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationResDTO {
    private Long id;
    private ClientResDTO client;
    private ChambreResDTO chambre;
    private String dateDebut;
    private String dateFin;
    private String preferences;
}