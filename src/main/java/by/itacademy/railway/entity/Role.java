package by.itacademy.railway.entity;

import lombok.*;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "roles", schema = "public")
public class Role implements BaseEntity<Integer>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String role;

    @ToString.Exclude
    @Builder.Default
    @OneToMany(mappedBy = "role", cascade = CascadeType.REMOVE)
    private List<User> users = new ArrayList<>();

}
