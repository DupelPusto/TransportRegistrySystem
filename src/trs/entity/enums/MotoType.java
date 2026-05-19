package trs.entity.enums;

public enum MotoType {

    SPORT("СПОРТИВНИЙ"),
    CRUISER("КРУЇЗЕР"),
    ENDURO("ЕНДУРО"),
    CHOPPER("ЧОППЕР"),
    SCOOTER("СКУТЕР"),
    TOURING("ТУРИСТИЧНИЙ"),
    STREET("ДОРОЖНІЙ");


    private String description;

    MotoType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
