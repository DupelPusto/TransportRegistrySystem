package trs.controller;

import trs.db.OwnerDatabase;
import trs.db.UserDatabase;
import trs.db.VehicleDatabase;
import trs.dto.VehicleDto;
import trs.entity.Owner;
import trs.entity.user.User;
import trs.entity.user.UserRole;
import trs.exception.AuthorizationException;
import trs.exception.DatabaseException;


public class RegistrationManager {

    private static RegistrationManager instance;
    private final VehicleDatabase vehicleBase = VehicleDatabase.getInstance();
    private final OwnerDatabase ownerBase = OwnerDatabase.getInstance();
    private final UserDatabase userBase = UserDatabase.getInstance();

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
