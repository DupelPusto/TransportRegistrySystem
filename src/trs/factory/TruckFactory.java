package trs.factory;

import trs.dto.TruckDto;
import trs.dto.VehicleDto;
import trs.entity.Owner;
import trs.entity.Truck;
import trs.entity.Vehicle;

public class TruckFactory implements VehicleFactory{

    @Override
    public Vehicle createVehicle(VehicleDto vehicleDto, Owner owner) {

        TruckDto dto = (TruckDto) vehicleDto;

        return new Truck(
                dto.getVinCode(), dto.getEngineCode(), dto.getColor(),
                owner, dto.getModel(), dto.getLoadCapacity()
        );
    }
}
