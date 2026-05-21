package trs.factory;

import trs.dto.MotoDto;
import trs.dto.VehicleDto;
import trs.entity.Motorcycle;
import trs.entity.Owner;
import trs.entity.Vehicle;

public class MotoFactory implements VehicleFactory{

    @Override
    public Vehicle createVehicle(VehicleDto vehicleDto, Owner owner) {

        MotoDto dto = (MotoDto) vehicleDto;

        return new Motorcycle(
                dto.getVinCode(), dto.getEngineCode(), dto.getColor(),
                owner, dto.isHasSidecar(), dto.getType()
        );
    }
}
