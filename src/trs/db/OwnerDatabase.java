package trs.db;

import trs.entity.Owner;
import trs.exception.DatabaseException;

import java.util.HashMap;
import java.util.Map;

public class OwnerDatabase {

    private static OwnerDatabase instance;
    private Map<String, Owner> ownerBase;

    private OwnerDatabase(){
        this.ownerBase = new HashMap<>();
    }

    public static OwnerDatabase getInstance(){
        if (instance == null){
            instance = new OwnerDatabase();
        }
        return instance;
    }

    public void addOwner(Owner owner) throws DatabaseException{

        if (ownerBase.containsKey(owner.getPhone())) throw new DatabaseException("Власник з таким номером телефону вже існує!");

        ownerBase.put(owner.getPhone(), owner);
    }

    public Owner removeOwner(String phone){

       return ownerBase.remove(phone);
    }

    public void updatePhone(String current, String newPhone) throws DatabaseException{

        if (ownerBase.containsKey(newPhone)) throw new DatabaseException("Власник з таким номером телефону вже існує!");

        Owner updatedOwner = ownerBase.remove(current);

        if (updatedOwner == null) throw new DatabaseException("Власника з таким номером телефону не знайдено!");

        updatedOwner.setPhone(newPhone);
        ownerBase.put(newPhone, updatedOwner);
    }

    public Owner findByPhone(String phoneNum){
        return ownerBase.get(phoneNum);
    }

}
