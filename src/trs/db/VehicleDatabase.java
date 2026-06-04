package trs.db;

import trs.entity.Vehicle;
import trs.exception.DatabaseException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public void addVehicle(Vehicle vehicle) throws DatabaseException{
        if (vehicleBase.containsKey(vehicle.getVinCode())) throw new DatabaseException("Транспортний засіб з таким VIN-вже зареєстрований!");
        vehicleBase.put(vehicle.getVinCode(), vehicle);
    }

    public Vehicle removeVehicle(String vinCode){
        return vehicleBase.remove(vinCode);

    }

    public Vehicle findByVinCode(String vinCode){
        return vehicleBase.get(vinCode);
    }

    public ArrayList<Vehicle> findAllByEngineCode(String engineCode){

        ArrayList<Vehicle> temp = new ArrayList<>();
        for (Vehicle veh : vehicleBase.values()){
            if (engineCode.equals(veh.getEngineCode())){
                temp.add(veh);
            }
        }
        return temp;
    }

    public ArrayList<Vehicle> findAllByGovNumber(String govNumber){

        ArrayList<Vehicle> temp = new ArrayList<>();
        for (Vehicle veh : vehicleBase.values()){
            if (govNumber.equals(veh.getGovNumber())){
                temp.add(veh);
            }
        }
        return temp;
    }

    public ArrayList<Vehicle> findAllByOwnerNumber(String phoneNumber){

        ArrayList<Vehicle> temp = new ArrayList<>();
        for (Vehicle veh : vehicleBase.values()){
            if (veh.getOwner() != null && veh.getOwner().getPhone().equals(phoneNumber)) temp.add(veh);
        }
        return temp;
    }

    public List<Vehicle> getVehicles(){
        return new ArrayList<>(vehicleBase.values());
    }
}
