package by.itacademy.railway.dto.order;

import by.itacademy.railway.dto.ticket.TicketReadDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderReadDto {

    private Long id;
    private String status;
    private LocalDateTime registrationTime;
    private LocalDateTime payedTime;
    List<TicketReadDto> tickets;

}
