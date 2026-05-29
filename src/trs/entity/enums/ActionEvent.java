package trs.entity.enums;

public enum ActionEvent {
    ADDED_TO_SYSTEM("Транспортний засіб зареєстровано в системі"),
    ASSIGNED_REG_NUMBER("Транспортному засобу присвоєно державний номер"),
    OWNER_CHANGED("Змінено власника транспортного засобу"),
    STATUS_CHANGED("Статус транспортного засобу змінено"),
    TECHNICAL_INSPECTION("Технічний огляд транспортного засобу"),
    VIOLATION("Скоєно правопорушення"),
    OWNER_REGISTRATION("Зареєстровано нового власника"),
    OWNER_DELETED("Видалено власника"),
    VEHICLE_DELETED("Транспортний засіб видалено"),
    OWNER_UPDATE_PHONE("Оновлено номер власника");


    private String description;

    ActionEvent(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
