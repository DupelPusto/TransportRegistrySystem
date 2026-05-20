package trs.view;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleMenu {

    private static Scanner scanner = new Scanner(System.in);

    public void start(){

        while (true) {
            System.out.println("СИСТЕМА ОБЛІКУ-РЕЄСТРАЦІЇ ТЗ");
            System.out.println("1. Увійти в систему");
            System.out.println("2. Завершити роботу");
            System.out.println("Введіть номер пункту меню: ");

            int action;

            try {
                action = scanner.nextInt();

                switch (action) {

                    case 1:

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

    public void adminMenu(){

        System.out.println("[АДМІНІСТРАТОР]СИСТЕМА ОРТЗ: ");
        System.out.println("1. Зареєструвати власника");
        System.out.println("2. Оновити номер власника");
        System.out.println("3. Видалити власника");
        System.out.println("Введіть номер пункту меню або натисніть 0 для виходу з системи: ");
    }

    public static void userMenu(){

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
