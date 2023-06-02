package by.itacademy.railway.entity;

public enum DocumentType {

    PASSPORT("Пасспорт"),
    DRIVER_LICENSE("Водительское удостоверение"),
    WORK_ID_CARD("Рабочее удостоверение"),
    STUDENT_ID_CARD("Студенческий");

    private final String type;

    DocumentType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
