package by.itacademy.railway.dto.train;

import lombok.Value;

import java.time.LocalDate;

@Value
public class TrainSearchDto {

    String from;
    String to;
    LocalDate date;

}
