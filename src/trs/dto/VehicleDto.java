package trs.dto;

import trs.entity.Owner;

public class VehicleDto {

    private String vinCode;
    private String engineCode;
    private String color;
    private Owner owner;

    public VehicleDto(String vinCode, Owner owner) {
        this.vinCode = vinCode;
        this.owner = owner;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setEngineCode(String engineCode) {
        this.engineCode = engineCode;
    }

    public String getVinCode() {
        return vinCode;
    }

    public String getEngineCode() {
        return engineCode;
    }

    public String getColor() {
        return color;
    }

    public Owner getOwner() {
        return owner;
    }
}
