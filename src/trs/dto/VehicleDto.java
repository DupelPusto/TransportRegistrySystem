package trs.dto;

import trs.entity.Owner;

public class VehicleDto {

    private String vinCode;
    private String engineCode;
    private String color;
    private String ownerPhone;

    public VehicleDto(String vinCode, String ownerPhone) {
        this.vinCode = vinCode;
        this.ownerPhone = ownerPhone;
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

    public String getOwnerPhone() {
        return ownerPhone;
    }
}
