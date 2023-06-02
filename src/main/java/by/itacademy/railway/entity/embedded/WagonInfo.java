package by.itacademy.railway.entity.embedded;

import by.itacademy.railway.entity.WagonType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class WagonInfo {

    private Short no;
    @Enumerated(EnumType.STRING)
    private WagonType type;

}
