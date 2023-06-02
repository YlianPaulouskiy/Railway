package by.itacademy.railway.entity;

public enum Gender {

    MALE('М'),
    FEMALE('Ж');

    private final char gender;

    Gender(char gender) {
        this.gender = gender;
    }

    public char getGender() {
        return gender;
    }
}
