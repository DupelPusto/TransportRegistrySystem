package trs.db;

import trs.entity.Owner;

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

    public void addOwner(Owner owner){

    }

    public void removeOwner(String phone){

    }

    public void updatePhone(String current, String newPhone){

    }

    public Owner findByPhone(String phoneNum){
        return null;
    }
}
