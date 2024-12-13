package ma.emsi.resthotel.dto.Res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChambreResDTO {
    private Long id;
    private String type;
    private Double prix;
    private Boolean disponible;
}