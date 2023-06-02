package by.itacademy.railway.entity;

public enum WagonType {

    COUPE("Купэ"),
    RESERVED("Пдацкарт"),
    SITTING("Сидячий");

    private final String  type;

    WagonType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
