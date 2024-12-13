package ma.emsi.resthotel.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    @ToString.Exclude // Exclude the reservations field from toString

    private Client client;
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude // Exclude the reservations field from toString
    @JoinColumn(name = "chambre_id", nullable = false)
    private Chambre chambre;
    @Temporal(TemporalType.DATE)
    private LocalDate datefin;
    @Temporal(TemporalType.DATE)
    private LocalDate datedebut;
    private String preferences;
}
