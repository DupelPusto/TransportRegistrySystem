package trs.entity;

import trs.entity.enums.ActionEvent;
import trs.entity.enums.VehicleStatus;

import java.util.List;

public abstract class Vehicle {

    private String vinCode;
    private String engineCode;
    private String color;
    private String govNumber;
    private Owner owner;
    private VehicleStatus status;
    protected List<HistoryElement> history;

    public Vehicle(String vinCode, String engineCode, String color, Owner owner) {
        this.vinCode = vinCode;
        this.engineCode = engineCode;
        this.color = color;
        this.owner = owner;
    }

    protected void addHistory(ActionEvent event){

    }

    public void updateStatus(VehicleStatus status){

    }

    public void changeOwner(Owner owner){

    }

    public void assignGovNumber(String govNumber){

    }

    public String getVinCode() {
        return vinCode;
    }
}
