package trs.db;

import trs.entity.Vehicle;
import trs.exception.DatabaseException;

import java.util.HashMap;
import java.util.Map;

public class VehicleDatabase {

    private static VehicleDatabase instance;
    private Map<String, Vehicle> vehicleBase;

    private VehicleDatabase(){
        this.vehicleBase = new HashMap<>();
    }

    public static VehicleDatabase getInstance(){
        if (instance == null){
            instance = new VehicleDatabase();
        }
        return instance;
    }

    public void addVehicle(Vehicle vehicle){
        if (vehicleBase.containsKey(vehicle.getVinCode())) throw new DatabaseException("Транспортний засіб з таким VIN-вже зареєстрований!");
        vehicleBase.put(vehicle.getVinCode(), vehicle);
    }

    public Vehicle removeVehicle(String vinCode){
        return vehicleBase.remove(vinCode);

    }

    public Vehicle findByVinCode(String vinCode){
        return vehicleBase.get(vinCode);
    }
}
