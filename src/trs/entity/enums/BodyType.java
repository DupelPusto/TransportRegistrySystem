package trs.entity.enums;

public enum BodyType {
    SEDAN("СЕДАН"),
    COUPE("КУПЕ"),
    CONVERTIBLE("КАБРІОЛЕТ"),
    ESTATE("УНІВЕРСАЛ"),
    HATCHBACK("ХЕТЧБЕК"),
    CROSSOVER("КРОСОВЕР"),
    SUV("ПОЗАШЛЯХОВИК"),
    PICKUP("ПІКАП");

    private String description;

    BodyType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
