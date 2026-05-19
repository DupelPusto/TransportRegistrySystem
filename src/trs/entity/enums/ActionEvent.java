package trs.entity.enums;

public enum ActionEvent {
    ADDED_TO_SYSTEM("Транспортний засіб зареєстровано в системі"),
    ASSIGNED_REG_NUMBER("Транспортному засобу присвоєно державний номер"),
    OWNER_CHANGED("Змінено власника транспортного засобу"),
    STATUS_CHANGED("Статус транспортного засобу змінено");

    private String description;

    ActionEvent(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
