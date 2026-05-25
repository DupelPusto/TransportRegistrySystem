package trs.entity.enums;

public enum VehicleStatus {
    WAITING_FOR_REG_NUMBER("Зареєстровано. Очікує видачі номерного знака"),
    NORMAL("В експлуатації"),
    WANTED("У розшуку"),
    WITHOUT_OWNER("Без власника. Очікує на переоформлення");

    private String description;

    VehicleStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
