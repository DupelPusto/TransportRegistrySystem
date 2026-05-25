package trs.controller;

import trs.db.OwnerDatabase;
import trs.db.UserDatabase;
import trs.db.VehicleDatabase;
import trs.dto.CarDto;
import trs.dto.MotoDto;
import trs.dto.TruckDto;
import trs.dto.VehicleDto;
import trs.entity.Owner;
import trs.entity.Vehicle;
import trs.entity.enums.ActionEvent;
import trs.entity.enums.VehicleStatus;
import trs.entity.user.User;
import trs.entity.user.UserRole;
import trs.exception.AuthorizationException;
import trs.exception.DatabaseException;
import trs.factory.CarFactory;
import trs.factory.MotoFactory;
import trs.factory.TruckFactory;
import trs.factory.VehicleFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class RegistrationManager {

    private static RegistrationManager instance;
    private final Map<String, String> toDoVehicles = new HashMap<>();
    private final VehicleDatabase vehicleBase = VehicleDatabase.getInstance();
    private final OwnerDatabase ownerBase = OwnerDatabase.getInstance();
    private final UserDatabase userBase = UserDatabase.getInstance();
    private VehicleFactory factory;


    private RegistrationManager() {}

    public static RegistrationManager getInstance(){

        if (instance == null){
            instance = new RegistrationManager();
        }
        return instance;
    }

    public Vehicle registerVehicle(VehicleDto dto) throws DatabaseException{

        Vehicle vehicle = null;
        Owner owner = findOwner(dto.getOwnerPhone());
        if (dto instanceof CarDto){
            factory = new CarFactory();
            vehicle = factory.createVehicle(dto, owner);
            factory = null;
        }else if (dto instanceof TruckDto){
            factory = new TruckFactory();
            vehicle = factory.createVehicle(dto, owner);
            factory = null;
        } else if (dto instanceof MotoDto) {
            factory = new MotoFactory();
            vehicle = factory.createVehicle(dto, owner);
            factory = null;
        }

        vehicleBase.addVehicle(vehicle);
        vehicle.addHistory(ActionEvent.ADDED_TO_SYSTEM);
        vehicle.updateStatus(VehicleStatus.WAITING_FOR_REG_NUMBER);
        toDoVehicles.put(vehicle.getVinCode(), String.format("%s(VIN - %s) очікує на видачу номерного знаку", vehicle.getModel(), vehicle.getVinCode()));
        vehicle.addHistory(ActionEvent.STATUS_CHANGED, String.format("--> %s", VehicleStatus.WAITING_FOR_REG_NUMBER.getDescription()));

        return vehicle;

    }

    public Owner registerOwner(String name, String surname, String phone, String email){
        Owner owner = new Owner(name, surname, phone, email);
        ownerBase.addOwner(owner);
        return owner;
    }

    public Owner findOwner(String phoneNum){
        return ownerBase.findByPhone(phoneNum);
    }

    public boolean isOwnerRegistered(String phoneNum){
        return ownerBase.findByPhone(phoneNum) != null;
    }

    public void updateOwnerPhone(String current, String newPhone){
        ownerBase.updatePhone(current, newPhone);
    }

    public Owner removeOwner(String phoneNum){

        ArrayList<Vehicle> temp = vehicleBase.findAllByOwnerNumber(phoneNum);
        VehicleStatus oldStatus;
        for (Vehicle veh : temp){
            oldStatus = veh.getStatus();
            veh.setOwner(null);
            veh.updateStatus(VehicleStatus.WITHOUT_OWNER);
            String addInfo = String.format("'%s' --> '%s'", oldStatus.getDescription(), VehicleStatus.WITHOUT_OWNER.getDescription());
            veh.addHistory(ActionEvent.STATUS_CHANGED, addInfo);
            toDoVehicles.put(veh.getVinCode(), String.format("%s(VIN - %s) очікує переоформлення на нового власника", veh.getModel(), veh.getVinCode()));
        }

        return ownerBase.removeOwner(phoneNum);
    }

    public boolean isVehicleEngineCodeExists(String engineCode){

        ArrayList<Vehicle> temp = vehicleBase.findAllByEngineCode(engineCode);
        return !temp.isEmpty();
    }

    public boolean isVehicleGovNumberExists(String govNumber){

        ArrayList<Vehicle> temp = vehicleBase.findAllByGovNumber(govNumber);
        return !temp.isEmpty();

    }

    public boolean isVehicleRegistered(String vinCode){
        return vehicleBase.findByVinCode(vinCode) != null;
    }

    public void updateVehicleOwner(String vin, String phoneNumber){
        Vehicle veh = vehicleBase.findByVinCode(vin);
        Owner oldOwner = veh.getOwner();
        Owner newOwner = ownerBase.findByPhone(phoneNumber);
        veh.setOwner(newOwner);
        veh.addHistory(ActionEvent.OWNER_CHANGED, String.format("'%s' --> '%s'", oldOwner, newOwner));

        toDoVehicles.remove(vin);
    }

    public Vehicle removeVehicle(String vinCode){
        toDoVehicles.remove(vinCode);
        return vehicleBase.removeVehicle(vinCode);

    }

    public void assignGovNumberToVehicle(String vinCode, String govNumber){

        Vehicle veh = vehicleBase.findByVinCode(vinCode);
        veh.assignGovNumber(govNumber);
        veh.addHistory(ActionEvent.ASSIGNED_REG_NUMBER, "--> " + govNumber);
        VehicleStatus oldStatus = veh.getStatus();
        veh.updateStatus(VehicleStatus.NORMAL);
        veh.addHistory(ActionEvent.STATUS_CHANGED, String.format("%s --> %s", oldStatus.getDescription(), VehicleStatus.NORMAL.getDescription()));
        toDoVehicles.remove(vinCode);
    }

    public void addUser(String login, String password, UserRole role) throws DatabaseException {
        userBase.registerUser(new User(login, password, role));
    }

    public User authUser(String login, String password) throws AuthorizationException{
        User tempUser = userBase.getUser(login);
        if (tempUser == null || !tempUser.getPassword().equals(password)){
            throw new AuthorizationException("Помилка авторизації, перевірте правильність введених даних!");
        }
        return tempUser;
    }


    public Map<String, String> getToDoList(){
        return toDoVehicles;
    }

}
