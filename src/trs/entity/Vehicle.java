package trs.entity;

import trs.entity.enums.ActionEvent;
import trs.entity.enums.VehicleStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class Vehicle {

    private String vinCode;
    private String engineCode;
    private String color;
    private String govNumber;
    private Owner owner;
    private String model;
    private VehicleStatus status;
    private List<HistoryElement> history = new ArrayList<>();

    public Vehicle(String vinCode, String engineCode, String color, Owner owner, String model) {
        this.vinCode = vinCode;
        this.engineCode = engineCode;
        this.color = color;
        this.owner = owner;
        this.model = model;
    }

    public void addHistory(ActionEvent event){
        this.history.add(new HistoryElement(LocalDateTime.now(), event));
    }

    public void addHistory(ActionEvent event, String addInfo){
        this.history.add(new HistoryElement(LocalDateTime.now(), event, addInfo));
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

    public String getModel() {
        return model;
    }
}
