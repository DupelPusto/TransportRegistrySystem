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


public class RegistrationManager {

    private static RegistrationManager instance;
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
        vehicle.addHistory(ActionEvent.STATUS_CHANGED, String.format("--> %s", VehicleStatus.WAITING_FOR_REG_NUMBER.getDescription()));

        return vehicle;

    }

    public void registerOwner(String name, String surname, String phone, String email){
        ownerBase.addOwner(new Owner(name, surname, phone, email));
    }

    public Owner findOwner(String phoneNum){
        return ownerBase.findByPhone(phoneNum);
    }

    public boolean isOwnerRegistred(String phoneNum){
        return ownerBase.findByPhone(phoneNum) != null;
    }

    public void updateOwnerPhone(String current, String newPhone){

    }

    public void removeOwner(String phoneNum){

    }

    public void removeVehicle(String vinCode){

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


}
