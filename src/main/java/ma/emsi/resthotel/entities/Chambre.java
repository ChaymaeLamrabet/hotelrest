package ma.emsi.resthotel.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Chambre {
    @Id
    private Long id;
    @Enumerated(EnumType.STRING)
    private TypeChambre type;
    private Double prix;
    private Boolean disponible;
    @OneToMany(mappedBy = "chambre", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude // Exclude the reservations field from toString
    private List<Reservation> reservations;
}
