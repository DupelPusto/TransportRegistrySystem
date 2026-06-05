package trs;

import trs.controller.RegistrationManager;
import trs.memento.FileCaretaker;
import trs.memento.SystemMemento;
import trs.memento.SystemOriginator;
import trs.statistic.AdminStatistic;
import trs.view.ConsoleMenu;

public class Main {
    public static void main(String[] args) {
        RegistrationManager manager = RegistrationManager.getInstance();
        AdminStatistic statistic = new AdminStatistic();
        manager.addSubscriber(statistic);

        SystemOriginator originator = new SystemOriginator(statistic);
        FileCaretaker caretaker = new FileCaretaker();

        SystemMemento savedState = caretaker.loadState();
        if (savedState != null) {
            originator.restoreFromMemento(savedState);
            System.out.println("Дані успішно завантажено з файлу.");
        } else {
            System.out.println("Попередні дані не знайдено. Запуск із чистою базою.");
        }

        ConsoleMenu menu = new ConsoleMenu(statistic, originator, caretaker);
        menu.start();
    }
}