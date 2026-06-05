package trs.memento;

import trs.entity.Owner;
import trs.entity.Vehicle;
import trs.entity.enums.ActionEvent;
import trs.entity.user.User;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class SystemMemento implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Map<String, Vehicle> vehicles;
    private final Map<String, Owner> owners;
    private final Map<String, User> users;
    private final Map<String, String> toDoList;
    private final Map<ActionEvent, Integer> counters;
    private final List<String> logs;

    public SystemMemento(Map<String, Vehicle> vehicles, Map<String, Owner> owners, Map<String, User> users,
                         Map<String, String> toDoList, Map<ActionEvent, Integer> counters, List<String> logs) {
        this.vehicles = vehicles;
        this.owners = owners;
        this.users = users;
        this.toDoList = toDoList;
        this.counters = counters;
        this.logs = logs;
    }

    public Map<String, Vehicle> getVehicles() { return vehicles; }
    public Map<String, Owner> getOwners() { return owners; }
    public Map<String, User> getUsers() { return users; }
    public Map<String, String> getToDoList() { return toDoList; }
    public Map<ActionEvent, Integer> getCounters() { return counters; }
    public List<String> getLogs() { return logs; }
}