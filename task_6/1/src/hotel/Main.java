package hotel;

import hotel.controller.AdminController;
import hotel.controller.ReceptionController;
import hotel.controller.service.RoomService;
import hotel.model.Hotel;
import hotel.controller.service.ServicesService;
import hotel.controller.service.ClientService;
import hotel.model.service.Services;
import hotel.personal.client.Client;
import hotel.personal.employee.HotelEmployee;
import hotel.personal.employee.admin.Admin;
import hotel.personal.employee.reception.Reception;
import hotel.view.AdminView;
import hotel.view.ReceptionView;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Hotel hotel = Hotel.getInstance();
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try (BufferedReader br = new BufferedReader(new FileReader("bd/employee.csv"))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";\\s*");
                HotelEmployee employee = new HotelEmployee.HotelEmployeeBuilder()
                        .id(Integer.parseInt(values[0]))
                        .name(values[1])
                        .surname(values[2])
                        .patronymic(values[3])
                        .date_of_birth(LocalDate.parse(values[4].trim(), DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                        .position(values[5])
                        .build();
                hotel.getPersonalMap().put(employee.getId(), employee);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scanner scanner = new Scanner(System.in);
        Admin admin = null;
        Reception reception = null;

        for (HotelEmployee employee : hotel.getPersonalMap().values()){
            if(employee.getPosition().equals("admin")){
                admin = (Admin) employee;
            }
            else if(employee.getPosition().equals("reception")){
                reception = (Reception) employee;
            }
        }

        RoomService roomService = new RoomService(admin);
        ClientService clientService = new ClientService(admin);
        ServicesService servicesService = new ServicesService(admin);

        AdminController adminController = new AdminController(roomService, clientService, servicesService, admin);
        ReceptionController receptionController = new ReceptionController(roomService, clientService, servicesService, reception);

        AdminView adminView = new AdminView(adminController);
        ReceptionView receptionView = new ReceptionView(receptionController, scanner);

        try(BufferedReader br = new BufferedReader(new FileReader("bd/client_2.csv"))) {
            String str;
            br.readLine();
            while ((str = br.readLine()) != null){
                String[] values = str.split(";\\s*");
                System.out.println((Arrays.stream(values[7].split("\\s*,\\s*"))
                        .map(String::trim)
                        .map(Services.getSERVICES()::get)
                        .collect(Collectors.toList())));
                Client client = new Client.ClientBuilder()
                        .id(Integer.parseInt(values[0]))
                        .surname(values[1])
                        .name(values[2])
                        .patronymic(values[3])
                        .date_of_birth(LocalDate.parse(values[4].trim(), DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                        .departureDate(LocalDate.parse(values[5].trim(), DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                        .checkOutDate(LocalDate.parse(values[6].trim(), DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                        .services(Arrays.stream(values[7].split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"))
                                .map(String::trim)
                                .map(Services.getSERVICES()::get)
                                .filter(Objects::nonNull)
                                .collect(Collectors.toList()))
                        .idRoom(Integer.parseInt(values[8]))
                        .build();
                receptionController.addClient(client);
                receptionController.settle(client, client.getIdRoom());
            }
        }catch (IOException e) {
            throw new RuntimeException(e);
        }

        while (true) {
            System.out.println("=== Система управления отелем ===");
            System.out.println("\nВыберите режим работы:");
            System.out.println("1. Администратор");
            System.out.println("2. Ресепшен");
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

