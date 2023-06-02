package by.itacademy.railway.entity;

public enum TrainType {

    REGIONAL("Региональные линии экономкласса"),
    INTERREGIONAL("Межрегиональные линии бизнес-класса"),
    URBAN("Гордские линии"),
    INTERNATIONAL("Международные линии");

    private final String type;

    TrainType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }


    }
