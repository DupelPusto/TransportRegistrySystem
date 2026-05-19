package trs.controller;

import trs.db.OwnerDatabase;
import trs.db.VehicleDatabase;
import trs.dto.VehicleDto;
import trs.entity.Owner;


public class RegistrationManager {

    private static RegistrationManager instance;
    private final VehicleDatabase vehicleBase = VehicleDatabase.getInstance();
    private final OwnerDatabase ownerBase = OwnerDatabase.getInstance();

    private RegistrationManager() {}

    public static RegistrationManager getInstance(){

        if (instance == null){
            instance = new RegistrationManager();
        }
        return instance;
    }

    public void registerVehicle(VehicleDto dto){

    }

    public void registerOwner(Owner owner){

    }

    public Owner findOwner(String phoneNum){
        return null;
    }

    public void updateOwnerPhone(String current, String newPhone){

    }

    public void removeOwner(String phoneNum){

    }

    public void removeVehicle(String vinCode){

    }


}
