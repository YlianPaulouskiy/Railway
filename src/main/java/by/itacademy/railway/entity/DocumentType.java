package by.itacademy.railway.entity;

public enum DocumentType {

    PASSPORT("Пасспорт"),
    DRIVER_LICENSE("Водительское удостоверение"),
    WORK_ID_CARD("Рабочее удостоверение"),
    STUDENT_ID_CARD("Студенческий");

    private final String string;

    DocumentType(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }
}
