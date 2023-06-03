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
@Table(name = "users", schema = "public")
public class User implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserPassenger> passengers = new ArrayList<>();

    public void setRole(Role role) {
        this.role = role;
        this.role.getUsers().add(this);
    }

    public void addPassengers(Passenger... passengers) {
        try {
            for (Passenger passenger : passengers) {
                this.passengers.add(UserPassenger.builder()
                        .passenger(passenger)
                        .user(this)
                        .build());
            }
        } catch (NullPointerException exception) {
            exception.printStackTrace();
        }
    }

    public void addOrders(Order... orders) {
        try {
            for (Order order : orders) {
                this.orders.add(order);
                order.setUser(this);
            }
        } catch (NullPointerException exception) {
            exception.printStackTrace();
        }
    }
}
