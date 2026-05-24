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

    public static String getTypes(){
        StringBuilder types = new StringBuilder();
        for (MotoType type : MotoType.values()){
            types.append(type.ordinal()+1).append(" - ").append(type.getDescription()).append("\n");
        }
        return types.toString();
    }
}
