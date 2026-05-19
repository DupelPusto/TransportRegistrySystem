package trs.dto;

import trs.entity.Owner;
import trs.entity.enums.MotoType;

public class MotoDto extends VehicleDto{

    private boolean hasSidecar;
    private MotoType type;

    public MotoDto(String vinCode, Owner owner) {
        super(vinCode, owner);
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
