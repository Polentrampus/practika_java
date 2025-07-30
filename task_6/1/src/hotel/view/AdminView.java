package hotel.view;

import hotel.controller.AdminController;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Scanner;
import hotel.controller.AdminController;
import hotel.model.filter.FilterClient;
import hotel.model.filter.FilterRoom;
import hotel.model.filter.FilterServices;
import hotel.model.room.RoomCategory;
import hotel.model.room.RoomStatus;
import hotel.model.room.RoomType;
import hotel.personal.Person;
import hotel.personal.client.Client;
import hotel.personal.employee.HotelEmployee;

import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

public class AdminView {
    private final AdminController controller;
    private final Scanner scanner;

    public AdminView(AdminController controller) {
        this.controller = controller;
        this.scanner = new Scanner(System.in);
    }

    public void showAdminMenu() throws FileNotFoundException {
        while (true) {
            System.out.println("\n=== Административное меню ===");
            System.out.println("1. Управление комнатами");
            System.out.println("2. Управление услугами");
            System.out.println("3. Управление персоналом");
            System.out.println("4. Работа с клиентами");
            System.out.println("5. Экспорт клиентов");
            System.out.println("6. Экспорт персонала");
            System.out.println("0. Выход");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> showRoomManagementMenu();
                case 2 -> showServicesManagementMenu();
                case 3 -> showStaffManagementMenu();
                case 4 -> showClientManagementMenu();
                case 5 -> controller.exportClientsCSV(controller.requestListClient(FilterClient.ID), "bd/client_3");
                case 6 -> controller.exportEmployeeCSV(controller.requestListPersonal(),"bd/employee_2");
                case 0 -> { return; }
                default -> System.out.println("Неверный выбор, попробуйте снова");
            }
        }
    }

    private void showRoomManagementMenu() {
        while (true) {
            System.out.println("\n=== Управление комнатами ===");
            System.out.println("1. Изменить цену комнаты");
            System.out.println("2. Добавить новую комнату");
            System.out.println("3. Просмотреть список всех комнат");
            System.out.println("4. Просмотреть список свободных комнат");
            System.out.println("0. Назад");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> changeRoomPriceDialog();
                case 2 -> addRoomDialog();
                case 3 -> {
                    System.out.println("\nСписок всех комнат:");
                    controller.requestListRoom(FilterRoom.ID);
                }
                case 4 -> {
                    System.out.println("\nСписок свободных комнат:");
                    controller.requestListAvailableRoom(FilterRoom.ID);
                }
                case 0 -> { return; }
                default -> System.out.println("Неверный выбор, попробуйте снова");
            }
        }
    }

    private void changeRoomPriceDialog() {
        System.out.print("\nВведите номер комнаты: ");
        int roomId = scanner.nextInt();
        System.out.print("Введите новую цену: ");
        int newPrice = scanner.nextInt();
        scanner.nextLine();

        controller.changeRoomPrice(roomId, newPrice);
    }

    private void addRoomDialog() {
        System.out.println("\nДобавление новой комнаты:");

        System.out.print("Номер комнаты: ");
        int roomIndex = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Выберите категорию (1-ECONOMY, 2-BUSINESS, 3-PREMIUM): ");
        RoomCategory category = RoomCategory.values()[scanner.nextInt()-1];

        System.out.println("Выберите тип (1-STANDARD, 2-DELUXE, 3-SUITE, 4-APARTMENT, 5-FAMILY, 6-PRESIDENTIAL): ");
        RoomType type = RoomType.values()[scanner.nextInt()-1];

        System.out.print("Вместимость: ");
        int capacity = scanner.nextInt();

        System.out.print("Цена: ");
        int price = scanner.nextInt();
        scanner.nextLine();

        controller.addRoom(category, RoomStatus.AVAILABLE, type, capacity, roomIndex, price);
        System.out.println("Комната успешно добавлена");
    }

    private void showServicesManagementMenu() {
        while (true) {
            System.out.println("\n=== Управление услугами ===");
            System.out.println("1. Изменить цену услуги");
            System.out.println("2. Добавить новую услугу");
            System.out.println("3. Просмотреть список услуг");
            System.out.println("0. Назад");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> changeServicePriceDialog();
                case 2 -> addServiceDialog();
                case 3 -> {
                    System.out.println("\nСписок всех услуг:");
                    controller.requestListServices(FilterServices.PRICE);
                }
                case 0 -> { return; }
                default -> System.out.println("Неверный выбор, попробуйте снова");
            }
        }
    }

    private void changeServicePriceDialog() {
        System.out.print("\nВведите название услуги: ");
        String serviceName = scanner.nextLine();
        System.out.print("Введите новую цену: ");
        int newPrice = scanner.nextInt();
        scanner.nextLine();

        controller.changeServicePrice(serviceName, newPrice);
        System.out.println("Цена услуги успешно изменена");
    }

    private void addServiceDialog() {
        System.out.println("\nДобавление новой услуги:");

        System.out.print("Название услуги: ");
        String name = scanner.nextLine();

        System.out.print("Описание: ");
        String description = scanner.nextLine();

        System.out.print("Цена: ");
        double price = scanner.nextDouble();
        scanner.nextLine();

        controller.addService(name, description, price);
        System.out.println("Услуга успешно добавлена");
    }

    private void showStaffManagementMenu() {
        while (true) {
            System.out.println("\n=== Управление персоналом ===");
            System.out.println("1. Добавить нового сотрудника");
            System.out.println("0. Назад");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addStaffDialog();
                case 0 -> { return; }
                default -> System.out.println("Неверный выбор, попробуйте снова");
            }
        }
    }

    private void addStaffDialog() {
        System.out.println("\nДобавление нового сотрудника:");

        System.out.print("Имя: ");
        String name = scanner.nextLine();

        System.out.print("Фамилия: ");
        String surname = scanner.nextLine();

        System.out.print("Отчество: ");
        String patronymic = scanner.nextLine();

        System.out.print("Дата рождения: ");
        int date_of_birth = scanner.nextInt();

        System.out.print("Месяц рождения: ");
        int month_of_birth = scanner.nextInt();

        System.out.print("Год рождения: ");
        int year_of_birth = scanner.nextInt();

        HotelEmployee newEmployee = new HotelEmployee.HotelEmployeeBuilder()
                .name(name)
                .patronymic(patronymic)
                .surname(surname)
                .build();

        controller.addPersonal(Arrays.asList(newEmployee));

        System.out.println("Сотрудник успешно добавлен");
    }

    private void showClientManagementMenu() {
        while (true) {
            System.out.println("\n=== Работа с клиентами ===");
            System.out.println("1. Получить инф. о клиенте");
            System.out.println("2. Просмотреть список клиентов");
            System.out.println("3. Просмотреть последних 3-х клиентов");
            System.out.println("4. Просмотреть услуги клиента");
            System.out.println("0. Назад");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> getClientInfoDialog();
                case 2 -> {
                    System.out.println("\nСписок всех клиентов:");
                    controller.requestListClient(FilterClient.ID);
                }
                case 3 -> {
                    System.out.println("\nПоследние 3 клиента:");
                    controller.requestLastThreeClient();
                }
                case 4 -> getClientServicesDialog();
                case 0 -> { return; }
                default -> System.out.println("Неверный выбор, попробуйте снова");
            }
        }
    }

    private void getClientInfoDialog() {
        System.out.print("\nВведите ID клиента: ");
        int clientId = scanner.nextInt();
        scanner.nextLine();

        controller.getInfoAboutClient(clientId);
    }

    private void getClientServicesDialog() {
        System.out.print("\nВведите ID клиента: ");
        int clientId = scanner.nextInt();
        scanner.nextLine();

        System.out.println("\nУслуги клиента:");
        controller.requestListServicesClient(FilterServices.PRICE, clientId);
    }
}
