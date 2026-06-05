package trs.memento;

import trs.controller.RegistrationManager;
import trs.db.OwnerDatabase;
import trs.db.UserDatabase;
import trs.db.VehicleDatabase;
import trs.statistic.AdminStatistic;

import java.util.HashMap;
import java.util.ArrayList;

public class SystemOriginator {
    private final VehicleDatabase vehicleDb = VehicleDatabase.getInstance();
    private final OwnerDatabase ownerDb = OwnerDatabase.getInstance();
    private final UserDatabase userDb = UserDatabase.getInstance();
    private final RegistrationManager manager = RegistrationManager.getInstance();
    private final AdminStatistic statistic;

    public SystemOriginator(AdminStatistic statistic) {
        this.statistic = statistic;
    }

    public SystemMemento createMemento() {
        return new SystemMemento(
                new HashMap<>(vehicleDb.getBase()),
                new HashMap<>(ownerDb.getBase()),
                new HashMap<>(userDb.getBase()),
                new HashMap<>(manager.getToDoList()),
                new HashMap<>(statistic.getCounters()),
                new ArrayList<>(statistic.getLogJournal())
        );
    }

    public void restoreFromMemento(SystemMemento memento) {
        if (memento != null) {
            vehicleDb.setBase(memento.getVehicles());
            ownerDb.setBase(memento.getOwners());
            userDb.setBase(memento.getUsers());
            manager.setToDoVehicles(memento.getToDoList());
            statistic.setCounters(memento.getCounters());
            statistic.setLogs(memento.getLogs());
        }
    }
}