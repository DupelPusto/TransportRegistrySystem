package trs.entity;

import trs.entity.enums.MotoType;

public class Motorcycle extends Vehicle{

    private boolean hasSidecar;
    private MotoType type;

    public Motorcycle(String vinCode, String engineCode, String color, Owner owner,
                      boolean hasSidecar, MotoType type) {
        super(vinCode, engineCode, color, owner);
        this.hasSidecar = hasSidecar;
        this.type = type;
    }
}
