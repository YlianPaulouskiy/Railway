package by.itacademy.railway.entity;

public enum SeatType {

    LOWER("Нижнее"),
    UPPER("Верхнее"),
    LOWER_SIDE("Нижнее боковое"),
    UPPER_SIDE("Верхнее боковое");

    private final String string;

    SeatType(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }

}
