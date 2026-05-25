package trs.entity;

public class Truck extends Vehicle{

    private double loadCapacity;

    public Truck(String vinCode, String engineCode, String color, Owner owner,
                 String model, double loadCapacity) {
        super(vinCode, engineCode, color, owner, model);
        this.loadCapacity = loadCapacity;
    }

    @Override
    public String toString() {

        StringBuilder truckInfo = new StringBuilder(super.toString());
        truckInfo.append("Вантажопідйомність: ").append(this.loadCapacity).append("\n");
        return truckInfo.toString();
    }
}
