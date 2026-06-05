package trs.view;

import trs.controller.RegistrationManager;
import trs.controller.Validator;
import trs.dto.CarDto;
import trs.dto.MotoDto;
import trs.dto.TruckDto;
import trs.entity.HistoryElement;
import trs.entity.Owner;
import trs.entity.Vehicle;
import trs.entity.enums.BodyType;
import trs.entity.enums.MotoType;
import trs.entity.enums.VehicleStatus;
import trs.entity.user.User;
import trs.entity.user.UserRole;
import trs.exception.AuthorizationException;
import trs.exception.DatabaseException;
import trs.memento.FileCaretaker;
import trs.memento.SystemOriginator;
import trs.statistic.AdminStatistic;

import java.util.*;

public class ConsoleMenu {

    private static Scanner scanner = new Scanner(System.in);
    private static final RegistrationManager manager = RegistrationManager.getInstance();
    private AdminStatistic statistic;
    private final SystemOriginator originator;
    private final FileCaretaker caretaker;
    private static final String VEHICLE_TYPES = "1 - Легковий автомобіль\n" +
                                                "2 - Вантажний автомобіль\n" +
                                                "3 - Мотоцикл";

    public ConsoleMenu(AdminStatistic statistic, SystemOriginator originator, FileCaretaker caretaker) {
        this.statistic = statistic;
        this.originator = originator;
        this.caretaker = caretaker;
    }

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
                        caretaker.saveState(originator.createMemento());
                        System.out.println("Дані збережено, вихід з системи...");
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
            System.out.println("\n[АДМІНІСТРАТОР]СИСТЕМА ОРТЗ: ");
            System.out.println("1. Переглянути історію ТЗ");
            System.out.println("2. Переглянути статистику");
            System.out.println("3. Список ТЗ");
            System.out.println("4. Додати інформацію про технічне обслуговування");
            System.out.println("5. Додати інформацію про правопорушення");
            System.out.println("6. Подати/зняти з розшуку");
            System.out.println("7. Журнал подій");
            System.out.println("Введіть номер пункту меню або натисніть 0 для виходу з системи: ");

            int action;

            try{
                action = scanner.nextInt();
                scanner.nextLine();

                switch (action){

                    case 0:
                        return;
                    case 1:
                        printVehicleHistory();
                        break;
                    case 2:
                        printStatistic();
                        break;
                    case 3:
                        printVehicles();
                        break;
                    case 4:
                        addTechnicalInspection();
                        break;
                    case 5:
                        addViolation();
                        break;
                    case 6:
                        changeVehicleStatus();
                        break;
                    case 7:
                        printLogJournal();
                        break;
                    default:
                        throw new IllegalArgumentException("Неіснуючий пункт меню, спробуйте ще раз!");

                }
            } catch (InputMismatchException e){
                System.err.println("Некоректне значення, введіть число!");
                scanner.nextLine();
            } catch (IllegalArgumentException e){
                System.err.println(e.getMessage());
            }
        }
    }

    void printVehicleHistory(){

        System.out.println("Введіть VIN-код(XXX12345XX): ");
        String vin = scanner.nextLine().trim().toUpperCase();
        if (!Validator.isVinCodeValid(vin)){
            System.err.println("Невірний формат VIN-коду! Введіть у форматі XXX12345XX");
            return;
        }
        if (!manager.isVehicleRegistered(vin)){
            System.err.println("Транспортний засіб з таким VIN-кодом не знайдено!");
            return;
        }

        for (HistoryElement el : manager.getVehicleHistory(vin)){
            System.out.println(el);
        }
    }

    void printStatistic(){
        System.out.println(statistic.getStatistic());
    }

    void printVehicles(){

        for (Vehicle veh : manager.getVehicles()){

            String ownerInfo = (veh.getOwner() != null) ? String.format("%s(%s)", veh.getOwner().getFullName(),
                    veh.getOwner().getPhone()) : "БЕЗ ВЛАСНИКА";

            String vehicle = String.format("ТЗ: %s, VIN: %s, Власник: %s, Статус: %s ", veh.getModel(), veh.getVinCode(),
                                            ownerInfo, veh.getStatus().getDescription());
            System.out.println(vehicle);
        }
    }

    void addTechnicalInspection(){

        System.out.println("Реєстрація технічного обслуговування");
        System.out.println("Введіть VIN-код транспортного засобу(XXX12345XX): ");
        String vin = scanner.nextLine().trim().toUpperCase();
        if (!Validator.isVinCodeValid(vin)){
            System.err.println("Невірний формат VIN-коду! Введіть у форматі XXX12345XX");
            return;
        }
        if (!manager.isVehicleRegistered(vin)){
            System.err.println("Транспортний засіб з таким VIN-кодом не знайдено!");
            return;
        }

        System.out.println("Введіть інформація про технічне обслуговування: ");
        String info;
        while (true){
            info = scanner.nextLine().trim();
            if (!info.isBlank()){
                break;
            }
            System.out.println("Поле не може бути порожнім або містити тільки пробіли! Введіть інформацію спочатку: ");
            scanner.nextLine();
        }

        manager.addTechnicalInspection(vin, info);
        System.out.println("Інформацію про технічний огляд успішно зареєстровано!");
    }

    void addViolation(){

        System.out.println("Реєстрація провопорушення");
        System.out.println("Введіть VIN-код транспортного засобу(XXX12345XX): ");
        String vin = scanner.nextLine().trim().toUpperCase();
        if (!Validator.isVinCodeValid(vin)){
            System.err.println("Невірний формат VIN-коду! Введіть у форматі XXX12345XX");
            return;
        }
        if (!manager.isVehicleRegistered(vin)){
            System.err.println("Транспортний засіб з таким VIN-кодом не знайдено!");
            return;
        }

        System.out.println("Введіть інформація про склад правопорушення: ");
        String info;
        while (true){
            info = scanner.nextLine().trim();
            if (!info.isBlank()){
                break;
            }
            System.out.println("Поле не може бути порожнім або містити тільки пробіли! Введіть інформацію спочатку: ");
            scanner.nextLine();
        }

        manager.addViolation(vin, info);
        System.out.println("Інформацію про правопорушення успішно зареєстровано!");
    }

    void changeVehicleStatus(){

        System.out.println("Зміна статусу ТЗ");
        System.out.println("Введіть VIN-код транспортного засобу(XXX12345XX): ");
        String vin = scanner.nextLine().trim().toUpperCase();
        if (!Validator.isVinCodeValid(vin)){
            System.err.println("Невірний формат VIN-коду! Введіть у форматі XXX12345XX");
            return;
        }
        if (!manager.isVehicleRegistered(vin)){
            System.err.println("Транспортний засіб з таким VIN-кодом не знайдено!");
            return;
        }

        System.out.println("Оберіть дію: ");
        System.out.println("1. Подати у розшук");
        System.out.println("2. Зняти з розшуку");
        int action;

        try {
            action = scanner.nextInt();
            scanner.nextLine();

            switch (action){

                case 1:
                    if (manager.isVehicleWanted(vin)) {
                        System.err.println("Цей транспортний засіб вже знаходиться в розшуку!");
                        return;
                    }
                    manager.changeVehicleStatus(vin, VehicleStatus.WANTED);
                    break;
                case 2:
                    if (!manager.isVehicleWanted(vin)) {
                        System.err.println("Цей транспортний засіб вже знято з розшуку!");
                        return;
                    }
                    manager.changeVehicleStatus(vin, VehicleStatus.NORMAL);
                    break;
                default:
                    throw new IllegalArgumentException("Неіснуючий пункт меню, спробуйте ще раз!");
            }
        } catch (InputMismatchException e){
            System.err.println("Некоректне значення, введіть число!");
            scanner.nextLine();
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    void printLogJournal(){
        System.out.println("Журнал подій:");
        for (String s : statistic.getLogJournal()){
            System.out.println(s);
        }
    }

    public void userMenu() {

        while (true) {
            System.out.println("\nСИСТЕМА ОРТЗ:");
            System.out.println("1. Зареєструвати власника");
            System.out.println("2. Оновити номер власника");
            System.out.println("3. Видалити власника");
            System.out.println("4. Зареєструвати ТЗ");
            System.out.println("5. Оновити власника ТЗ");
            System.out.println("6. Видалити ТЗ");
            System.out.println("7. Видати номерний знак");
            System.out.println("8. Поточні завдання");
            System.out.println("Введіть номер пункту меню або натисніть 0 для виходу з системи: ");

            int action;

            try{
                action = scanner.nextInt();
                scanner.nextLine();

                switch (action){

                    case 0:
                        return;
                    case 1:
                        ownerRegistration();
                        break;
                    case 2:
                        updateOwnerNumber();
                        break;
                    case 3:
                        deleteOwner();
                        break;
                    case 4:
                        startVehicleRegistration();
                        break;
                    case 5:
                        updateVehicleOwner();
                        break;
                    case 6:
                        deleteVehicle();
                        break;
                    case 7:
                        assignGovNumberToVehicle();
                        break;
                    case 8:
                        printToDoList();
                        break;
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

    void printInfo(Vehicle vehicle){
        System.out.println("Реєстрація успішна!\n" + vehicle);
    }

    void printInfo(Owner owner){
        System.out.println("Реєстрація успішна!\n" + owner);
    }

    void printToDoList(){
        int counter = 1;
        Map<String, String> toDoList = manager.getToDoList();
        for (String s : toDoList.values()){
            System.out.printf("%d Потрібна дія: %s%n", counter++, s);
        }
    }

    void ownerRegistration(){

        String phone;
        System.out.println("Реєстрація власника");
        System.out.println("Введіть номер телефону(380XXXXXXXXX): ");
        phone = scanner.nextLine().trim();
        if (!Validator.isPhoneNumberValid(phone)){
            System.err.println("Невірний формат номеру телефону! Введіть у форматі 380XXXXXXXXX");
            return;
        }
        if (manager.isOwnerRegistered(phone)){
            System.err.println("Власник з таким номером телефону вже зареєстрований!");
            return;
        }

        System.out.println("Введіть ім'я: ");
        String name = scanner.nextLine().trim();
        System.out.println("Введіть прізвище: ");
        String surname = scanner.nextLine().trim();
        System.out.println("Введіть електронну пошту: ");
        String email = scanner.nextLine().trim();

        Owner owner = manager.registerOwner(name, surname, phone, email);
        printInfo(owner);

    }

    void updateOwnerNumber(){

        System.out.println("Оновлення номеру телефону власника");
        System.out.println("Введіть зареєстрований номер телефону власника(380XXXXXXXXX): ");
        String current = scanner.nextLine().trim();
        if (!Validator.isPhoneNumberValid(current)){
            System.err.println("Невірний формат номеру телефону! Введіть у форматі 380XXXXXXXXX");
            return;
        }
        if (!manager.isOwnerRegistered(current)) {
            System.err.println("Власника з вказаним номером телефону не знайдено!");
            return;
        }
        System.out.println("Введіть новий номер телефону власника: ");
        String newNumber = scanner.nextLine().trim();
        if (!Validator.isPhoneNumberValid(newNumber)){
            System.err.println("Невірний формат номеру телефону! Введіть у форматі 380XXXXXXXXX");
            return;
        }
        if (manager.isOwnerRegistered(newNumber)) {
            System.err.println("За вказаним новим номером телефону вже зареєстровано власника!");
            return;
        }

        manager.updateOwnerPhone(current, newNumber);
        System.out.println("Номер телефону власника успішно змінено!");
    }

    void deleteOwner(){

        System.out.println("Видалення власника");
        System.out.println("Введіть номер телефону власника для видалення(380XXXXXXXXX): ");
        String number = scanner.nextLine().trim();
        if (!Validator.isPhoneNumberValid(number)){
            System.err.println("Невірний формат номеру телефону! Введіть у форматі 380XXXXXXXXX");
            return;
        }
        if (!manager.isOwnerRegistered(number)){
            System.err.println("Власника за вказаним номером телефону не знайдено!");
            return;
        }
        Owner owner = manager.removeOwner(number);
        System.out.printf("Власника %s успішно видалено!%n", owner);
    }

    void startVehicleRegistration(){

        System.out.println("Реєстрація ТЗ");
        System.out.println("Введіть номер телефону власника(380XXXXXXXXX): ");
        String ownerPhone = scanner.nextLine().trim();
        if (!Validator.isPhoneNumberValid(ownerPhone)){
            System.err.println("Невірний формат номеру телефону! Введіть у форматі 380XXXXXXXXX");
            return;
        }
        if (!manager.isOwnerRegistered(ownerPhone)){
            System.err.println("Власника за вказаним номером не знайдено! Додайте власника та спробуйте знову");
            return;
        }

        System.out.printf("Оберіть тип транспортного засобу:%n%s%n", VEHICLE_TYPES);
        System.out.println("Введіть значення:");
        int type;

        try{
            type = scanner.nextInt();
            scanner.nextLine();

            switch (type){

                case 1:
                case 2:
                case 3:
                    vehicleRegistration(type, ownerPhone);
                    return;
                default:
                    throw new IllegalArgumentException("Неіснуючий тип, спробуйте ще раз!");
            }

        }catch (InputMismatchException e){
            System.err.println("Некоректне значення, введіть число!");
            scanner.nextLine();
        }
    }

    void vehicleRegistration(int type, String ownerPhone) {

        Vehicle vehicle;
        String vin;
        String engineCode;
        String color;
        String model;


        System.out.println("Введіть VIN-код(XXX12345XX): ");
        vin = scanner.nextLine().trim().toUpperCase();
        if (!Validator.isVinCodeValid(vin)){
            System.err.println("Невірний формат VIN-коду! Введіть у форматі XXX12345XX");
            return;
        }
        if (manager.isVehicleRegistered(vin)){
            System.err.println("Транспортний засіб з таким VIN-кодом вже існує!");
            return;
        }

        System.out.println("Введіть код двигуна(XXX1234X): ");
        engineCode = scanner.nextLine().trim().toUpperCase();
        if (!Validator.isEngineCodeValid(engineCode)){
            System.err.println("Невірний формат коду двигуна! Введіть у форматі XXX1234X");
            return;
        }
        if (manager.isVehicleEngineCodeExists(engineCode)){
            System.err.println("Транспортний засіб з таким двигуном вже зареєстрований!");
            return;
        }

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
                        scanner.nextLine();
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

                try {
                    vehicle = manager.registerVehicle(carDto);
                } catch (DatabaseException e) {
                    System.err.println(e.getMessage());
                    break;
                }

                printInfo(vehicle);
                break;

            case 2:

                System.out.println("Введіть значення максимального навантаження(формат 22,9): ");
                double loadCap;
                while (true){
                    try{
                        loadCap = scanner.nextDouble();
                        scanner.nextLine();
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

                try{
                    vehicle = manager.registerVehicle(truckDto);
                } catch (DatabaseException e) {
                    System.err.println(e.getMessage());
                    break;
                }

                printInfo(vehicle);
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
                    sidecar = scanner.nextLine().trim();
                    if (sidecar.equals("-")){
                        hasSidecar = false;
                        break;
                    } else if (sidecar.equals("+")) {
                        hasSidecar = true;
                        break;
                    } else {
                        System.err.println("Невірний формат! Введіть - або +:");
                    }
                }

                MotoDto motoDto = new MotoDto(vin, model, ownerPhone);
                motoDto.setEngineCode(engineCode);
                motoDto.setHasSidecar(hasSidecar);
                motoDto.setColor(color);
                motoDto.setType(MotoType.values()[motoType - 1]);

                try{
                    vehicle = manager.registerVehicle(motoDto);
                } catch (DatabaseException e) {
                    System.err.println(e.getMessage());
                    break;
                }
                printInfo(vehicle);
                break;

        }
    }

    void updateVehicleOwner(){

        System.out.println("Зміна власника ТЗ");
        System.out.println("Введіть VIN-код ТЗ(XXX12345XX): ");
        String vin = scanner.nextLine().trim();
        if (!Validator.isVinCodeValid(vin)){
            System.err.println("Невірний формат VIN-коду! Введіть у форматі XXX12345XX");
            return;
        }
        if (!manager.isVehicleRegistered(vin)){
            System.err.println("Транспортного засобу з таким VIN-кодом не знайдено!");
            return;
        }
        System.out.println("Введіть номер телефону нового власника(380XXXXXXXXX): ");
        String phone = scanner.nextLine().trim();
        if (!Validator.isPhoneNumberValid(phone)){
            System.err.println("Невірний формат номеру телефону! Введіть у форматі 380XXXXXXXXX");
            return;
        }
        if (!manager.isOwnerRegistered(phone)){
            System.err.println("За вказаним номером власника не знайдено!");
            return;
        }
        manager.updateVehicleOwner(vin, phone);
        System.out.println("Власника ТЗ успішно змінено!");
    }

    void deleteVehicle(){

        System.out.println("Видалення ТЗ");
        System.out.println("Введіть VIN-код ТЗ: ");
        String vin = scanner.nextLine().trim();
        if (!Validator.isVinCodeValid(vin)){
            System.err.println("Невірний формат VIN-коду! Введіть у форматі XXX12345XX");
            return;
        }
        if (!manager.isVehicleRegistered(vin)){
            System.err.println("Транспортного засобу з таким VIN-кодом не існує!");
            return;
        }

        Vehicle vehicle = manager.removeVehicle(vin);
        System.out.printf("%s%nУспішно видалено!", vehicle);
    }

    void assignGovNumberToVehicle(){

        System.out.println("Видача номерного знака");
        System.out.println("Введіть VIN-код ТЗ(XXX12345XX): ");
        String vin = scanner.nextLine().trim().toUpperCase();
        if (!Validator.isVinCodeValid(vin)){
            System.err.println("Невірний формат VIN-коду! Введіть у форматі XXX12345XX");
            return;
        }
        if (!manager.isVehicleRegistered(vin)){
            System.err.println("Транспортний засіб з таким VIN-кодом не знайдено!");
            return;
        }
        System.out.println("Введіть номерний знак для видачі(XX1234XX): ");
        String govNumber;
        while (true){
            govNumber = scanner.nextLine().trim();
            if (Validator.isGovNumberValid(govNumber)) break;
            System.err.println("Невірний формат номерного знака! Введіть у форматі ХХ1234ХХ");
            scanner.nextLine();
        }
        if (manager.isVehicleGovNumberExists(govNumber)){
            System.err.println("Номерний знак належить іншому ТЗ!");
            return;
        }
        manager.assignGovNumberToVehicle(vin, govNumber);

        System.out.println("Номерний знак видано!");
    }

}
