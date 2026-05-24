package trs.dto;

import trs.entity.Owner;

public class TruckDto extends VehicleDto{

    private double loadCapacity;

    public TruckDto(String vinCode, String model, String ownerPhone) {
        super(vinCode, model, ownerPhone);
    }

    public void setLoadCapacity(double loadCapacity) {
        this.loadCapacity = loadCapacity;
    }

    public double getLoadCapacity() {
        return loadCapacity;
    }

}
