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

    public static String getTypes(){
        StringBuilder types = new StringBuilder();

        for (BodyType type : BodyType.values()){
            types.append(type.ordinal()+1).append(" - ").append(type.getDescription()).append("\n");
        }
        return types.toString();
    }
}
