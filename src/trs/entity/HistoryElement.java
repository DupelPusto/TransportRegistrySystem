package trs.entity;

import trs.entity.enums.ActionEvent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HistoryElement {

    private LocalDateTime date;
    private ActionEvent action;
    private String addInfo;


    public HistoryElement(LocalDateTime date, ActionEvent action) {
        this.date = date;
        this.action = action;
        this.addInfo = "";
    }

    public HistoryElement(LocalDateTime date, ActionEvent action, String addInfo) {
        this.date = date;
        this.action = action;
        this.addInfo = addInfo;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        String finalDate = this.date.format(formatter);
        return String.format("Дата: %s. Опис події: %s %s", finalDate, this.action.getDescription(), this.addInfo);
    }
}
