package trs.entity;

import trs.entity.enums.BodyType;

public class Car extends Vehicle{

    private BodyType bodyType;

    public Car(String vinCode, String engineCode, String color, Owner owner,
               BodyType bodyType) {
        super(vinCode, engineCode, color, owner);
        this.bodyType = bodyType;
    }
}
