package by.itacademy.railway.dto.menu;

import lombok.Value;

import java.time.LocalDate;

@Value
public class SearchDto {

    String from;
    String to;
    LocalDate when;

}
