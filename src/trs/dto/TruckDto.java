package trs.dto;

import trs.entity.Owner;

public class TruckDto extends VehicleDto{

    private double loadCapacity;

    public TruckDto(String vinCode, Owner owner) {
        super(vinCode, owner);
    }

    public void setLoadCapacity(double loadCapacity) {
        this.loadCapacity = loadCapacity;
    }

    public double getLoadCapacity() {
        return loadCapacity;
    }

}
