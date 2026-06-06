package trs.dto;


import trs.entity.enums.MotoType;

public class MotoDto extends VehicleDto{

    private boolean hasSidecar;
    private MotoType type;

    public MotoDto(String vinCode, String model, String ownerPhone) {
        super(vinCode, model, ownerPhone);
    }

    public void setHasSidecar(boolean hasSidecar) {
        this.hasSidecar = hasSidecar;
    }

    public boolean isHasSidecar() {
        return hasSidecar;
    }

    public void setType(MotoType type) {
        this.type = type;
    }

    public MotoType getType() {
        return type;
    }


}
