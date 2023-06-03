package by.itacademy.railway.entity;


import by.itacademy.railway.entity.embedded.TrainInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "trains", schema = "public")
public class Train implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "code", column = @Column(nullable = false, unique = true)),
            @AttributeOverride(name = "type", column = @Column(nullable = false))
    })
    private TrainInfo trainInfo;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id", referencedColumnName = "id", nullable = false, unique = true)
    private Route route;

    @Builder.Default
    @OneToMany(mappedBy = "train", cascade = CascadeType.ALL)
    private List<Wagon> wagons = new ArrayList<>();

    public void addWagons(Wagon... wagons) {
        try {
            for (Wagon wagon : wagons) {
                this.wagons.add(wagon);
                wagon.setTrain(this);
            }
        } catch (NullPointerException exception) {
            exception.printStackTrace();
        }
    }

}
