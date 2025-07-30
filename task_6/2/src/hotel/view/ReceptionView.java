package hotel.view;

import hotel.controller.ReceptionController;
import hotel.exeption.ClientNotFoundException;
import hotel.exeption.HotelException;
import hotel.model.filter.FilterClient;
import hotel.model.filter.FilterRoom;
import hotel.model.filter.FilterServices;
import hotel.model.room.RoomCategory;
import hotel.model.service.Services;
import hotel.personal.client.Client;

import java.time.LocalDate;
import java.util.*;

public class ReceptionView{
    private final ReceptionController controler;
    private final Scanner scanner;


    public ReceptionView(ReceptionController controler, Scanner scanner) {
        this.controler = controler;
        this.scanner = scanner;
    }

    public void showReceptionMenu() throws HotelException {
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
                    try {
                        controler.evict(idClient);
                    } catch (ClientNotFoundException e) {
                        System.err.println("Ошибка: " + e.getMessage());
                    } catch (HotelException e) {
                        System.err.println("Ошибка при выселении: " + e.getMessage());
                    }
                }
                case 0 -> { return; }

            }
        }
    }

    private void checkInClient() throws HotelException {
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

        Client client_1 = new Client.ClientBuilder()
                .name(name)
                .patronymic(patronymic)
                .surname(surname)
                .date_of_birth(LocalDate.of(year_of_birth,month_of_birth,date_of_birth))
                .checkOutDate(LocalDate.of(2025,checkoutMonth,checkoutDate))
                .departureDate(LocalDate.of(2025,month_of_departure,date_of_departure))
                .build();

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
                scanner.nextLine();
            }
        }
        controler.fillsForm(client_1,category,selectedServices);
    }
}
