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

    public void updateStatus(VehicleStatus newStatus){
        status = newStatus;
    }

    public Owner getOwner(){
        return this.owner;
    }

    public String getEngineCode(){
        return this.engineCode;
    }

    public String getGovNumber(){
        return this.govNumber;
    }

    public void assignGovNumber(String govNumber){
        this.govNumber = govNumber;
    }

    public void setOwner(Owner owner){
        this.owner = owner;
    }

    public VehicleStatus getStatus(){
        return this.status;
    }

    public String getVinCode() {
        return vinCode;
    }

    public String getModel() {
        return model;
    }

    public List<HistoryElement> getHistory() {
        return history;
    }

    @Override
    public String toString() {

        StringBuilder info = new StringBuilder();

        info.append("Транпортний засіб: ").append(this.model).append("\n");
        info.append("VIN-код: ").append(this.vinCode).append("\n");
        info.append("Код двигуна: ").append(this.engineCode).append("\n");
        info.append("Колір: ").append(this.color).append("\n");
        info.append("Номерний знак: ").append(this.govNumber == null ? "очікує на видачу" : this.govNumber).append("\n");
        info.append("Власник: ").append(this.owner).append("\n");

        return info.toString();
    }
}
