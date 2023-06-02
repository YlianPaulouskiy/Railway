package by.itacademy.railway.entity;

public enum OrderStatus {

    PAYED("Оплачен"),
    NEED_PAY("Требует оплаты");

    private final String status;

    OrderStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
