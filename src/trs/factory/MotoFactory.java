package trs.factory;

import trs.dto.VehicleDto;
import trs.entity.Vehicle;

public class MotoFactory implements VehicleFactory{
    @Override
    public Vehicle createVehicle(VehicleDto vehicleDto) {
        return null;
    }
}
