package by.itacademy.railway.entity;

import by.itacademy.railway.entity.embedded.WagonInfo;
import lombok.*;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "wagons", schema = "public")
public class Wagon implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @AttributeOverrides({
            @AttributeOverride(name = "no", column = @Column(nullable = false)),
            @AttributeOverride(name = "type", column = @Column(nullable = false))
    })
    private WagonInfo wagonInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinColumn(name = "train_id", referencedColumnName = "id")
    private Train train;

    @Builder.Default
    @OneToMany(mappedBy = "wagon", cascade = CascadeType.ALL)
    private List<Seat> seats = new ArrayList<>();

    public void addSeats(Seat... seats) {
        try {
            for (Seat seat : seats) {
                this.seats.add(seat);
                seat.setWagon(this);
            }
        } catch (NullPointerException exception) {
            exception.printStackTrace();
        }
    }

}
