package trs.entity;

public class Truck extends Vehicle{

    private double loadCapacity;

    public Truck(String vinCode, String engineCode, String color, Owner owner,
                 double loadCapacity) {
        super(vinCode, engineCode, color, owner);
        this.loadCapacity = loadCapacity;
    }
}
