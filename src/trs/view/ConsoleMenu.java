package trs.view;

import trs.controller.RegistrationManager;
import trs.entity.user.User;
import trs.entity.user.UserRole;
import trs.exception.AuthorizationException;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleMenu {

    private static Scanner scanner = new Scanner(System.in);
    private static RegistrationManager manager = RegistrationManager.getInstance();

    public void start(){

        while (true) {
            System.out.println("СИСТЕМА ОБЛІКУ-РЕЄСТРАЦІЇ ТЗ");
            System.out.println("1. Увійти в систему");
            System.out.println("2. Завершити роботу");
            System.out.println("Введіть номер пункту меню: ");

            int action;

            try {
                action = scanner.nextInt();
                scanner.nextLine();

                switch (action) {

                    case 1:
                        authentication();
                        break;
                    case 2:
                        System.exit(0);

                    default:
                        throw new IllegalArgumentException("Неіснуючий пункт меню, спробуйте ще раз!");
                }
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.err.println("Некоректне значення, введіть число!");
                scanner.nextLine();
            }
        }

    }

    public void authentication(){

        System.out.println("\nВведіть логін: ");
        String login = scanner.nextLine();
        System.out.println("Введіть пароль: ");
        String password = scanner.nextLine();

        User currentUser;
        UserRole sessionType = null;
        try {
            currentUser = manager.authUser(login, password);
            sessionType = currentUser.getRole();
        } catch (AuthorizationException e){
            System.err.println(e.getMessage());
            return;
        }

        switch (sessionType){

            case ADMIN:
                adminMenu();
                break;
            case USER:
                userMenu();
                break;
        }

    }

    public void adminMenu() {

        while (true) {
            System.out.println("[АДМІНІСТРАТОР]СИСТЕМА ОРТЗ: ");
            System.out.println("1. Зареєструвати власника");
            System.out.println("2. Оновити номер власника");
            System.out.println("3. Видалити власника");
            System.out.println("Введіть номер пункту меню або натисніть 0 для виходу з системи: ");
        }
    }

    public void userMenu() {

        while (true) {
            System.out.println("СИСТЕМА ОРТЗ:");
            System.out.println("1. Зареєструвати власника");
            System.out.println("2. Оновити номер власника");
            System.out.println("3. Видалити власника");
            System.out.println("4. Зареєструвати ТЗ");
            System.out.println("5. Оновити власника ТЗ");
            System.out.println("6. Поточні завдання");
            System.out.println("Введіть номер пункту меню або натисніть 0 для виходу з системи: ");
        }
    }

}
