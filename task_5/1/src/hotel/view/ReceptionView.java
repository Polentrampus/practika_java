package hotel.view;

import hotel.controller.ReceptionController;
import hotel.model.filter.FilterClient;
import hotel.model.filter.FilterRoom;
import hotel.model.filter.FilterServices;
import hotel.model.room.RoomCategory;
import hotel.model.service.Services;
import hotel.model.service.ServicesService;
import hotel.personal.Person;
import hotel.personal.client.Client;

import java.security.Provider;
import java.time.LocalDate;
import java.util.*;

public class ReceptionView{
    private final ReceptionController controler;
    private final Scanner scanner;


    public ReceptionView(ReceptionController controler, Scanner scanner) {
        this.controler = controler;
        this.scanner = scanner;
    }

    public void showReceptionMenu() {
        while (true) {
            System.out.println("\n=== Меню ресепшена ===");
            System.out.println("1. Заселить клиента");
            System.out.println("2. Выселить клиента");
            System.out.println("0. Выход");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.println("Список клиентов: ");
                    controler.requestListClient(FilterClient.SURNAME);
                    checkInClient();
                }
                case 2 -> {
                    System.out.println("Список клиентов: ");
                    controler.requestListClient(FilterClient.ID);
                    System.out.println("Введите индекс клиента:");
                    int idClient = scanner.nextInt();
                    controler.evict(idClient);
                }
                case 0 -> { return; }
            }
        }
    }

    private void checkInClient() {
        System.out.println("\nДобавление нового клиента:");

        if (controler.requestListAvailableRoom(FilterRoom.PRICE).isEmpty()) {
            System.out.println("К сожалению нет свободных комнат, " +
                    "приносим свои извинения!");
            return;
        }
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

        System.out.print("Дата заезда: ");
        int date_of_departure = scanner.nextInt();

        System.out.print("Месяц заезда: ");
        int month_of_departure = scanner.nextInt();

        System.out.print("Дата выезда: ");
        int checkoutDate = scanner.nextInt();

        System.out.print("Месяц выезда: ");
        int checkoutMonth = scanner.nextInt();

        Client client_1 = new Client(name,surname,patronymic, date_of_birth,
                month_of_birth, year_of_birth, LocalDate.of(2025, month_of_departure, date_of_departure),
                LocalDate.of(2025, checkoutMonth, checkoutDate));

        System.out.println("Выберите категорию (1-ECONOMY, 2-BUSINESS, 3-PREMIUM): ");
        RoomCategory category = RoomCategory.values()[scanner.nextInt()-1];

        List<Services> selectedServices = new ArrayList<>();
        while (true){
            System.out.print("Выберите номер услуги: ");

            List<Services> allServices = controler.requestListServices(FilterServices.PRICE);
            System.out.println("0) Завершить выбор услуг");
            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();

                if (choice == 0) {
                    break;
                }

                if (choice < 0 || choice > allServices.size()) {
                    System.out.println("Неверный номер услуги!");
                    continue;
                }

                Services selected = allServices.get(choice - 1);
                if (!selectedServices.contains(selected)) {
                    selectedServices.add(selected);
                    System.out.println("Добавлена услуга: " + selected.getName());
                } else {
                    System.out.println("Эта услуга уже выбрана!");
                }

            } catch (Exception e) {
                System.out.println("Ошибка ввода! Введите номер услуги.");
                scanner.nextLine(); // очистка буфера
            }
        }
        controler.fillsForm(client_1,category,selectedServices);
    }
}
