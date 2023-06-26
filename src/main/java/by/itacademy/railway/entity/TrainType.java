package by.itacademy.railway.entity;

public enum TrainType {

    REGIONAL("Региональные линии экономкласса"),
    INTERREGIONAL("Межрегиональные линии бизнес-класса"),
    URBAN("Гордские линии"),
    INTERNATIONAL("Международные линии");

    private final String string;

    TrainType(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }


    }
