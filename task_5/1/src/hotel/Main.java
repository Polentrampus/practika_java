package hotel;

import hotel.controller.AdminController;
import hotel.controller.ReceptionController;
import hotel.model.Hotel;
import hotel.model.filter.FilterClient;
import hotel.model.filter.FilterRoom;
import hotel.model.filter.FilterServices;
import hotel.model.room.*;
import hotel.model.service.Services;
import hotel.model.service.ServicesService;
import hotel.personal.client.Client;
import hotel.personal.client.ClientService;
import hotel.personal.employee.admin.Admin;
import hotel.personal.employee.reception.Reception;
import hotel.personal.employee.service.Maid;
import hotel.personal.employee.service.Mender;
import hotel.personal.employee.service.ServicePersonnelHotel;
import hotel.view.AdminView;
import hotel.view.ReceptionView;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Hotel hotel = Hotel.getInstance();

        Admin admin = new Admin("Вася", "Васин", "Васильевич", 27, 4, 2005);
        Reception reception = new Reception("Иван", "Иванов", "Иванович", 17, 3, 1990);
        ServicePersonnelHotel maid_1 = new Maid("Лена", "Рыжова", "Еленина", 2, 7, 2001);
        ServicePersonnelHotel mender = new Mender("Петя", "Кек", "Кекович", 4, 2, 1998);

        Scanner scanner = new Scanner(System.in);

        RoomService roomService = new RoomService(admin);
        ClientService clientService = new ClientService(admin);
        ServicesService servicesService = new ServicesService(admin);

        AdminController adminController = new AdminController(roomService, clientService, servicesService, admin);
        ReceptionController receptionController = new ReceptionController(roomService, clientService, servicesService, admin);

        adminController.addPersonal(Arrays.asList(reception, maid_1, mender));
        adminController.addRoom(RoomCategory.ECONOMY, RoomStatus.AVAILABLE, RoomType.STANDARD, 3, 203, 13000);

        Client client_1 = new Client("Володя","Аол","Лолович", 12,
                12, 1987, LocalDate.of(2025, 7, 4),
                LocalDate.of(2025, 8, 12));
        Client client_2 = new Client("Пипип","Гка","авмва", 12,
                12, 1987, LocalDate.of(2025, 5, 3),
                LocalDate.of(2025, 6, 23));
        Client client_3 = new Client("нрнрн","Вррт","авипаип", 12,
                12, 1987, LocalDate.of(2024, 6, 2),
                LocalDate.of(2024, 9, 24));
        Client client_4 = new Client("цыцыы","Бфйф","яфяфяф", 12,
                12, 1987, LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 10, 25));

        adminController.addService("Мини бар", "В стоимость номера будет включен мини бар", 100.3);
        receptionController.fillsForm(client_1, RoomCategory.ECONOMY, Arrays.asList(Services.findService("Хранение багажа"),
                Services.findService("Мини бар")));
        receptionController.fillsForm(client_2, RoomCategory.BUSINESS, Arrays.asList(Services.findService("Фитнес-центр"),
                Services.findService("Мини бар")));
        receptionController.fillsForm(client_3, RoomCategory.ECONOMY, Arrays.asList(Services.findService("Трансфер"),
                Services.findService("Мини бар")));
        receptionController.fillsForm(client_4, RoomCategory.BUSINESS, null);

        AdminView adminView = new AdminView(adminController);
        ReceptionView receptionView = new ReceptionView(receptionController, scanner);


        while (true) {
            System.out.println("=== Система управления отелем ===");
            System.out.println("\nВыберите режим работы:");
            System.out.println("1. Администратор");
            System.out.println("2. Ресепшн");
            System.out.println("0. Выход");

            int choice = getIntInput(scanner, "Ваш выбор: ", 0, 2);
            switch (choice) {
                case 1 -> {
                    adminView.showAdminMenu();
                }
                case 2 -> {
                    receptionView.showReceptionMenu();
                }
                case 0 -> {
                    System.out.println("Завершение работы системы...");
                    return;
                }
            }
        }
    }

    private static int getIntInput(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = scanner.nextInt();
                scanner.nextLine(); // Очистка буфера
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.printf("Введите число от %d до %d%n", min, max);
            } catch (Exception e) {
                scanner.nextLine(); // Очистка буфера
                System.out.println("Ошибка ввода. Пожалуйста, введите число.");
            }
        }
    }
}
