package trs.dto;

import trs.entity.Owner;
import trs.entity.enums.BodyType;

public class CarDto extends VehicleDto{

    private BodyType bodyType;

    public CarDto(String vinCode, Owner owner) {
        super(vinCode, owner);
    }

    public void setBodyType(BodyType bodyType) {
        this.bodyType = bodyType;
    }

    public BodyType getBodyType() {
        return bodyType;
    }
}
