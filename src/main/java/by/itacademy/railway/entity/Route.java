package by.itacademy.railway.entity;

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
@Table(name = "routes", schema = "public")
public class Route implements BaseEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Builder.Default
    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL)
    private List<RouteStation> stations = new ArrayList<>();

    public void addRouteStation(RouteStation ...routeStations) {
        for (RouteStation routeStation : routeStations) {
            stations.add(routeStation);
            routeStation.setRoute(this);
        }
    }

}
