package by.itacademy.railway.entity;


import by.itacademy.railway.entity.embedded.SeatInfo;
import lombok.*;

import jakarta.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "seats", schema = "public")
public class Seat implements BaseEntity<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @AttributeOverrides({
            @AttributeOverride(name = "no", column = @Column(nullable = false)),
            @AttributeOverride(name = "type", column = @Column(nullable = false))
    })
    private SeatInfo seatInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinColumn(name = "wagon_id", referencedColumnName = "id")
    private Wagon wagon;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinColumn(name = "ticket_id", referencedColumnName = "id", unique = true)
    private Ticket ticket;

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
        this.ticket.setSeat(this);
    }

}
