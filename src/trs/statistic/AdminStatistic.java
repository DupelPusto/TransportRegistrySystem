package trs.statistic;

import trs.entity.enums.ActionEvent;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminStatistic implements Observer{

    private Map<ActionEvent, Integer> counters = new HashMap<>();
    private List<String> logs = new ArrayList<>();

    @Override
    public void onEvent(LocalDateTime timestamp, ActionEvent event, String id, String addInfo) {
        counters.put(event, counters.getOrDefault(event, 0) + 1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        String dateTime = timestamp.format(formatter);
        String log = switch (event) {
            case ADDED_TO_SYSTEM ->
                // date, vin, vehicle model
                    String.format("[%s] Подія: Зареєстровано ТЗ, VIN: %s, %s", dateTime, id, addInfo);
            case VIOLATION ->
                // date, vin, description of violation
                    String.format("[%s] Подія: Зареєстровано правопорушення для ТЗ, VIN: %s --> %s", dateTime, id, addInfo);
            case STATUS_CHANGED ->
                // date, vin, vehicle model
                    String.format("[%s] Подія: Змінено статус ТЗ, VIN: %s, %s", dateTime, id, addInfo);
            case TECHNICAL_INSPECTION ->
                // date, vin, description of violation
                    String.format("[%s] Подія: Зареєстровано технічний огляд для ТЗ, VIN: %s --> %s", dateTime, id, addInfo);
            case OWNER_CHANGED ->
                // date, vin, owner phone
                    String.format("[%s] Подія: Зміна власника ТЗ, VIN: %s --> %s", dateTime, id, addInfo);
            case OWNER_DELETED ->
                // date, owner phone, owner full name
                    String.format("[%s] Подія: Власника ТЗ видалено, %s, %s", dateTime, id, addInfo);
            case VEHICLE_DELETED ->
                // date, vin, vehicle model
                    String.format("[%s] Подія: ТЗ видалено, VIN: %s, %s", dateTime, id, addInfo);
            case OWNER_REGISTRATION ->
                // date, owner phone, owner full name
                    String.format("[%s] Подія: Зареєстровано власника, %s, %s", dateTime, id, addInfo);
            case ASSIGNED_REG_NUMBER ->
                // date, vin, assigned number
                    String.format("[%s] Подія: Видано номерний знак, VIN: %s --> %s", dateTime, id, addInfo);
            case OWNER_UPDATE_PHONE ->
                // date, old phone, new phone
                    String.format("[%s] Подія: Змінено номер телефона власника, %s --> %s", dateTime, id, addInfo);
        };

        logs.add(log);
    }

    public String getStatistic(){
        StringBuilder stat = new StringBuilder();
        stat.append("Статистика системи за весь час:\n");
        stat.append("Зареєстровано ТЗ: ").append(counters.getOrDefault(ActionEvent.ADDED_TO_SYSTEM, 0)).append("\n");
        stat.append("Зареєстровано власників: ").append(counters.getOrDefault(ActionEvent.OWNER_REGISTRATION, 0)).append("\n");
        stat.append("Оновлень даних власників: ").append(counters.getOrDefault(ActionEvent.OWNER_UPDATE_PHONE, 0)).append("\n");
        stat.append("Видано номерних знаків: ").append(counters.getOrDefault(ActionEvent.ASSIGNED_REG_NUMBER, 0)).append("\n");
        stat.append("Змінено власників у ТЗ: ").append(counters.getOrDefault(ActionEvent.OWNER_CHANGED, 0)).append("\n");
        stat.append("Зареєстровано технічних оглядів: ").append(counters.getOrDefault(ActionEvent.TECHNICAL_INSPECTION, 0)).append("\n");
        stat.append("Зареєстровано правопорушень: ").append(counters.getOrDefault(ActionEvent.VIOLATION, 0)).append("\n");
        stat.append("Змін статусів ТЗ: ").append(counters.getOrDefault(ActionEvent.STATUS_CHANGED, 0)).append("\n");
        stat.append("Видалено власників: ").append(counters.getOrDefault(ActionEvent.OWNER_DELETED, 0)).append("\n");
        stat.append("Видалено ТЗ: ").append(counters.getOrDefault(ActionEvent.VEHICLE_DELETED, 0)).append("\n");
        return stat.toString();
    }

    public List<String> getLogJournal(){
        return logs;
    }

    public Map<ActionEvent, Integer> getCounters() { return counters; }

    public void setCounters(Map<ActionEvent, Integer> counters) { this.counters = counters; }

    public void setLogs(List<String> logs) { this.logs = logs; }

}
