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
        ownerBase.put("380998887766", new Owner("qq", "ww", "380998887766", "email"));
    }

    public static OwnerDatabase getInstance(){
        if (instance == null){
            instance = new OwnerDatabase();
        }
        return instance;
    }

    public void addOwner(Owner owner){

        ownerBase.put(owner.getPhone(), owner);
    }

    public Owner removeOwner(String phone){

       return ownerBase.remove(phone);
    }

    public void updatePhone(String current, String newPhone) {

        Owner updatedOwner = ownerBase.remove(current);
        updatedOwner.setPhone(newPhone);
        ownerBase.put(newPhone, updatedOwner);
    }

    public Owner findByPhone(String phoneNum){
        return ownerBase.get(phoneNum);
    }

}
