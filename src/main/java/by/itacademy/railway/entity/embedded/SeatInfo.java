package by.itacademy.railway.entity.embedded;

import by.itacademy.railway.entity.SeatType;
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
public class SeatInfo {

    private Short no;
    @Enumerated(value = EnumType.STRING)
    private SeatType type;

}
