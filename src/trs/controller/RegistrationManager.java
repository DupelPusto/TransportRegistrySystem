package trs.controller;

import trs.db.OwnerDatabase;
import trs.db.UserDatabase;
import trs.db.VehicleDatabase;
import trs.dto.CarDto;
import trs.dto.MotoDto;
import trs.dto.TruckDto;
import trs.dto.VehicleDto;
import trs.entity.HistoryElement;
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
import trs.statistic.Observer;

import java.time.LocalDateTime;
import java.util.*;


public class RegistrationManager {

    private static RegistrationManager instance;
    private final Map<String, String> toDoVehicles = new HashMap<>();
    private final VehicleDatabase vehicleBase = VehicleDatabase.getInstance();
    private final OwnerDatabase ownerBase = OwnerDatabase.getInstance();
    private final UserDatabase userBase = UserDatabase.getInstance();
    private final List<Observer> subscribers = new ArrayList<>();
    private VehicleFactory factory;


    private RegistrationManager() {}

    public static RegistrationManager getInstance(){

        if (instance == null){
            instance = new RegistrationManager();
        }
        return instance;
    }

    public Vehicle registerVehicle(VehicleDto dto){

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
        LocalDateTime timestamp1 = LocalDateTime.now();
        vehicle.addHistory(timestamp1, ActionEvent.ADDED_TO_SYSTEM);
        notifyObservers(timestamp1, ActionEvent.ADDED_TO_SYSTEM, vehicle.getVinCode(), vehicle.getModel());

        vehicle.updateStatus(VehicleStatus.WAITING_FOR_REG_NUMBER);
        LocalDateTime timestamp2 = LocalDateTime.now();
        vehicle.addHistory(timestamp2, ActionEvent.STATUS_CHANGED, String.format("'%s' --> '%s'",vehicle.getStatus().getDescription(), VehicleStatus.WAITING_FOR_REG_NUMBER.getDescription()));
        notifyObservers(timestamp2, ActionEvent.STATUS_CHANGED, vehicle.getVinCode(), vehicle.getModel());

        toDoVehicles.put(vehicle.getVinCode(), String.format("%s(VIN - %s) очікує на видачу номерного знаку", vehicle.getModel(), vehicle.getVinCode()));

        return vehicle;

    }

    public Owner registerOwner(String name, String surname, String phone, String email){
        Owner owner = new Owner(name, surname, phone, email);
        ownerBase.addOwner(owner);
        LocalDateTime timestamp = LocalDateTime.now();
        notifyObservers(timestamp, ActionEvent.OWNER_REGISTRATION, owner.getPhone(), owner.getFullName());
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
        LocalDateTime timestamp = LocalDateTime.now();
        notifyObservers(timestamp, ActionEvent.OWNER_UPDATE_PHONE, current, newPhone);
    }

    public Owner removeOwner(String phoneNum){

        ArrayList<Vehicle> temp = vehicleBase.findAllByOwnerNumber(phoneNum);
        VehicleStatus oldStatus;
        for (Vehicle veh : temp){
            oldStatus = veh.getStatus();
            veh.setOwner(null);
            LocalDateTime timestamp = LocalDateTime.now();

            veh.addHistory(timestamp, ActionEvent.OWNER_CHANGED, "--> БЕЗ ВЛАСНИКА");
            notifyObservers(timestamp, ActionEvent.OWNER_CHANGED, veh.getVinCode(), "БЕЗ ВЛАСНИКА");

            veh.updateStatus(VehicleStatus.WITHOUT_OWNER);
            LocalDateTime timestamp1 = LocalDateTime.now();
            String addInfo = String.format("'%s' --> '%s'", oldStatus.getDescription(), VehicleStatus.WITHOUT_OWNER.getDescription());
            veh.addHistory(timestamp1, ActionEvent.STATUS_CHANGED, addInfo);
            notifyObservers(timestamp1, ActionEvent.STATUS_CHANGED, veh.getVinCode(), veh.getModel());


            toDoVehicles.put(veh.getVinCode(), String.format("%s(VIN - %s) очікує переоформлення на нового власника", veh.getModel(), veh.getVinCode()));
        }

        Owner deletedOwner = ownerBase.removeOwner(phoneNum);
        LocalDateTime timestamp2 = LocalDateTime.now();
        notifyObservers(timestamp2, ActionEvent.OWNER_DELETED, deletedOwner.getPhone(), deletedOwner.getFullName());
        return deletedOwner;
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
        LocalDateTime timestamp = LocalDateTime.now();
        String oldOwnerName = (oldOwner != null) ? oldOwner.toString() : "Власник відсутній";
        veh.addHistory(timestamp, ActionEvent.OWNER_CHANGED, String.format("'%s' --> '%s'", oldOwnerName, newOwner));
        notifyObservers(timestamp, ActionEvent.OWNER_CHANGED, veh.getVinCode(), newOwner.getPhone());
        toDoVehicles.remove(vin);
    }

    public Vehicle removeVehicle(String vinCode){
        toDoVehicles.remove(vinCode);
        Vehicle veh = vehicleBase.removeVehicle(vinCode);
        LocalDateTime timestamp = LocalDateTime.now();
        notifyObservers(timestamp, ActionEvent.VEHICLE_DELETED, veh.getVinCode(), veh.getModel());
        return veh;
    }

    public void assignGovNumberToVehicle(String vinCode, String govNumber){

        Vehicle veh = vehicleBase.findByVinCode(vinCode);
        veh.assignGovNumber(govNumber);
        LocalDateTime timestamp1 = LocalDateTime.now();
        veh.addHistory(timestamp1, ActionEvent.ASSIGNED_REG_NUMBER, String.format("--> '%s'", govNumber));
        notifyObservers(timestamp1, ActionEvent.ASSIGNED_REG_NUMBER, veh.getVinCode(), veh.getGovNumber());
        VehicleStatus oldStatus = veh.getStatus();

        veh.updateStatus(VehicleStatus.NORMAL);
        LocalDateTime timestamp2 = LocalDateTime.now();
        veh.addHistory(timestamp2, ActionEvent.STATUS_CHANGED, String.format("'%s' --> '%s'", oldStatus.getDescription(), VehicleStatus.NORMAL.getDescription()));
        notifyObservers(timestamp2, ActionEvent.STATUS_CHANGED, veh.getVinCode(), veh.getModel());

        toDoVehicles.remove(vinCode);
    }

    public void addTechnicalInspection(String vinCode, String info){
        Vehicle veh = vehicleBase.findByVinCode(vinCode);
        LocalDateTime timestamp = LocalDateTime.now();
        veh.addHistory(timestamp, ActionEvent.TECHNICAL_INSPECTION, String.format("--> '%s'", info));
        notifyObservers(timestamp, ActionEvent.TECHNICAL_INSPECTION, veh.getVinCode(), info);
    }

    public void addViolation(String vinCode, String info){
        Vehicle veh = vehicleBase.findByVinCode(vinCode);
        LocalDateTime timestamp = LocalDateTime.now();
        veh.addHistory(timestamp, ActionEvent.VIOLATION, String.format("--> '%s'", info));
        notifyObservers(timestamp, ActionEvent.VIOLATION, veh.getVinCode(), info);
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

    public List<HistoryElement> getVehicleHistory(String vin){

        return vehicleBase.findByVinCode(vin).getHistory();
    }

    public void changeVehicleStatus(String vin, VehicleStatus status){
        Vehicle veh = vehicleBase.findByVinCode(vin);
        VehicleStatus oldStatus = veh.getStatus();
        veh.updateStatus(status);
        LocalDateTime timestamp = LocalDateTime.now();
        veh.addHistory(timestamp, ActionEvent.STATUS_CHANGED, String.format("'%s' --> '%s'", oldStatus.getDescription(), status));
        notifyObservers(timestamp, ActionEvent.STATUS_CHANGED, veh.getVinCode(), String.format("'%s' --> '%s'", oldStatus.getDescription(), status));

    }

    public boolean isVehicleWanted(String vin){
        return vehicleBase.findByVinCode(vin).getStatus() == VehicleStatus.WANTED;
    }

    public void notifyObservers(LocalDateTime timestamp, ActionEvent event, String id, String addInfo){
        for (Observer sub : subscribers){
            sub.onEvent(timestamp, event, id, addInfo);
        }
    }

    public List<Vehicle> getVehicles(){
        return vehicleBase.getVehicles();
    }

    public void addSubscriber(Observer sub){
        subscribers.add(sub);
    }

    public void setToDoVehicles(Map<String, String> toDoVehicles){
        this.toDoVehicles.clear();
        this.toDoVehicles.putAll(toDoVehicles);
    }
}
