package trs.view;

import trs.controller.RegistrationManager;
import trs.dto.CarDto;
import trs.dto.MotoDto;
import trs.dto.TruckDto;
import trs.dto.VehicleDto;
import trs.entity.Vehicle;
import trs.entity.enums.BodyType;
import trs.entity.enums.MotoType;
import trs.entity.user.User;
import trs.entity.user.UserRole;
import trs.exception.AuthorizationException;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleMenu {

    private static Scanner scanner = new Scanner(System.in);
    private static RegistrationManager manager = RegistrationManager.getInstance();
    private static final String VEHICLE_TYPES = "1 - Легковий автомобіль\n" +
                                                "2 - Вантажний автомобіль\n" +
                                                "3 - Мотоцикл";

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

            int action;

            try{
                action = scanner.nextInt();
                scanner.nextLine();

                switch (action){

                    case 0:
                        return;
                    case 1:
                        registrateOwner();
                        break;
                    case 2:
                        updateOwnerNumber();
                        break;
                    case 3:
                        deleteOwner();
                        break;
                    case 4:
                        preregistrateVehicle();
                        break;
                    case 5:
                        updateVehicleOwner();
                        break;
                    case 6:
                    default:
                        throw new IllegalArgumentException("Неіснуючий пункт меню, спробуйте ще раз!");
                }


            }catch (InputMismatchException e){
                System.err.println("Некоректне значення, введіть число!");
                scanner.nextLine();
            } catch (IllegalArgumentException e){
                System.err.println(e.getMessage());
            }
        }
    }


    void registrateOwner(){


    }

    void updateOwnerNumber(){

    }

    void deleteOwner(){

    }

    void preregistrateVehicle(){

        System.out.println("Введіть номер телефону власника: ");
        String ownerPhone = scanner.nextLine();
        if (!manager.isOwnerRegistred(ownerPhone)){
            System.err.println("Власника за вказаним номером не знайдено! Додайте власника та спробуйте знову");
            return;
        }

        System.out.printf("Оберіть тип транспортного засобу:%n%s%n", VEHICLE_TYPES);
        System.out.println("Введіть значення:");
        int type;

        try{
            type = scanner.nextInt();

            switch (type){

                case 1:
                case 2:
                case 3:
                    scanner.nextLine();
                    registrateVehicle(type, ownerPhone);
                    return;
                default:
                    throw new IllegalArgumentException("Неіснуючий тип, спробуйте ще раз!");
            }

        }catch (InputMismatchException e){
            System.err.println("Некоректне значення, введіть число!");
            scanner.nextLine();
        }
    }

    void registrateVehicle(int type, String ownerPhone) {

        String vin;
        String engineCode;
        String color;
        String model;

        System.out.println("Введіть VIN-код: ");
        vin = scanner.nextLine().trim();
        System.out.println("Введіть код двигуна: ");
        engineCode = scanner.nextLine().trim();
        System.out.println("Введіть колір: ");
        color = scanner.nextLine().trim();
        System.out.println("Введіть марку та модель: ");
        model = scanner.nextLine();

        switch (type) {

            case 1:

                System.out.printf("Вкажіть тип кузову:%n%s", BodyType.getTypes());
                int body;
                while (true) {
                    try {
                        body = scanner.nextInt();
                        if (body < 1 || body > BodyType.values().length)
                            throw new IllegalArgumentException("Неіснуюче значення, спробуйте ще раз: ");
                        break;
                    } catch (InputMismatchException e) {
                        System.err.println("Некоректне значення, введіть число: ");
                        scanner.nextLine();
                    } catch (IllegalArgumentException e) {
                        System.err.println(e.getMessage());
                    }
                }


                CarDto carDto = new CarDto(vin, model, ownerPhone);
                carDto.setEngineCode(engineCode);
                carDto.setColor(color);
                BodyType bodyType = BodyType.values()[body - 1];
                carDto.setBodyType(bodyType);

                manager.registerVehicle(carDto);
                break;

            case 2:

                System.out.println("Введіть значення максимального навантаження(формат 22,9): ");
                double loadCap;
                while (true){
                    try{
                        loadCap = scanner.nextDouble();
                        break;
                    } catch (InputMismatchException e){
                        System.err.println("Некоректне значення, введіть число:");
                        scanner.nextLine();
                    }
                }

                TruckDto truckDto = new TruckDto(vin, model, ownerPhone);
                truckDto.setEngineCode(engineCode);
                truckDto.setColor(color);
                truckDto.setLoadCapacity(loadCap);

                manager.registerVehicle(truckDto);
                break;

            case 3:

                System.out.printf("Оберіть тип мотоцикла: %n%s%n", MotoType.getTypes());
                int motoType;
                while (true){
                    try {
                        motoType = scanner.nextInt();
                        scanner.nextLine();
                        if (motoType < 1 || motoType > MotoType.values().length){
                            throw new IllegalArgumentException("Неіснуюче значення, спробуйте ще раз:");
                        }
                        break;
                    } catch (InputMismatchException e){
                        System.err.println("Некоректне значення, введіть число: ");
                        scanner.nextLine();
                    } catch (IllegalArgumentException e){
                        System.err.println(e.getMessage());
                        scanner.nextLine();
                    }
                }


                System.out.println("Чи обладнаний мотоцикл боковою коляскою(+,-): ");
                String sidecar;
                boolean hasSidecar;
                while (true){
                    sidecar = scanner.nextLine();
                    scanner.nextLine();
                    if (sidecar.equals("-")){
                        hasSidecar = false;
                        break;
                    } else if (sidecar.equals("+")) {
                        hasSidecar = true;
                        break;
                    } else {
                        System.err.println("Невірний формат! Введіть - або +:");
                        scanner.nextLine();
                    }
                }

                MotoDto motoDto = new MotoDto(vin, model, ownerPhone);
                motoDto.setEngineCode(engineCode);
                motoDto.setHasSidecar(hasSidecar);
                motoDto.setColor(color);
                motoDto.setType(MotoType.values()[motoType]);

                manager.registerVehicle(motoDto);
                break;

        }
    }

    void updateVehicleOwner(){

    }

}
