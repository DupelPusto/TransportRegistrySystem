package trs.factory;

import trs.dto.VehicleDto;
import trs.entity.Owner;
import trs.entity.Vehicle;

public interface VehicleFactory {

    Vehicle createVehicle(VehicleDto vehicleDto);
}
