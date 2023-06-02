package by.itacademy.railway.entity;

public enum SeatType {

    LOWER("Нижнее"),
    UPPER("Верхнее"),
    LOWER_SIDE("Нижнее боковое"),
    UPPER_SIDE("Верхнее боковое");

    private final String type;

    SeatType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
