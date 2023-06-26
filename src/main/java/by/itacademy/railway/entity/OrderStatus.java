package by.itacademy.railway.entity;

public enum OrderStatus {

    PAYED("Оплачен"),
    NEED_PAY("Требует оплаты");

    private final String string;

    OrderStatus(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }
}
