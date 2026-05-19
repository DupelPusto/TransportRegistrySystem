package trs.db;

import trs.entity.Vehicle;

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

    }

    public void removeVehicle(String vinCode){

    }

    public Vehicle findByVinCode(String vinCode){
        return null;
    }
}
