package trs.dto;

import trs.entity.Owner;
import trs.entity.enums.BodyType;

public class CarDto extends VehicleDto{

    private BodyType bodyType;

    public CarDto(String vinCode, String model, String ownerPhone) {
        super(vinCode, model, ownerPhone);
    }

    public void setBodyType(BodyType bodyType) {
        this.bodyType = bodyType;
    }

    public BodyType getBodyType() {
        return bodyType;
    }
}
