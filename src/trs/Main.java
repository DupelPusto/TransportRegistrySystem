package trs;

import trs.controller.RegistrationManager;
import trs.statistic.AdminStatistic;
import trs.view.ConsoleMenu;

public class Main {
    public static void main(String[] args) {
        RegistrationManager manager = RegistrationManager.getInstance();
        AdminStatistic statistic = new AdminStatistic();
        manager.addSubscriber(statistic);

        ConsoleMenu menu = new ConsoleMenu(statistic);
        menu.start();
    }
}
