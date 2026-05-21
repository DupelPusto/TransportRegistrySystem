package trs.factory;

import trs.dto.CarDto;
import trs.dto.VehicleDto;
import trs.entity.Car;
import trs.entity.Owner;
import trs.entity.Vehicle;

public class CarFactory implements VehicleFactory{

    @Override
    public Vehicle createVehicle(VehicleDto vehicleDto, Owner owner) {

        CarDto dto = (CarDto) vehicleDto;

        return new Car(
                dto.getVinCode(), dto.getEngineCode(), dto.getColor(),
                owner, dto.getBodyType()
        );
    }
}
