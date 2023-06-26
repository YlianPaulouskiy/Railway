package by.itacademy.railway.entity;

public enum WagonType {

    COUPE("Купэ"),
    RESERVED("Пдацкарт"),
    SITTING("Сидячий");

    private final String string;

    WagonType(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }
}
