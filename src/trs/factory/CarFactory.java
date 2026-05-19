package trs.factory;

import trs.dto.VehicleDto;
import trs.entity.Owner;
import trs.entity.Vehicle;

public class CarFactory implements VehicleFactory{
    @Override
    public Vehicle createVehicle(VehicleDto vehicleDto) {
        return null;
    }
}
