package trs.entity;

import trs.entity.enums.ActionEvent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HistoryElement {

    private LocalDateTime date;
    private ActionEvent action;


    public HistoryElement(LocalDateTime date, ActionEvent action) {
        this.date = date;
        this.action = action;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        String finalDate = this.date.format(formatter);
        return String.format("Дата: %s. Опис події: %s", finalDate, this.action.getDescription());
    }
}
