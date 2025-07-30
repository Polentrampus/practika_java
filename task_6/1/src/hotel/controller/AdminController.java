package hotel.controller;

import hotel.model.filter.FilterRoom;
import hotel.model.room.RoomCategory;
import hotel.controller.service.RoomService;
import hotel.model.room.RoomStatus;
import hotel.model.room.RoomType;
import hotel.controller.service.ServicesService;
import hotel.model.service.Services;
import hotel.personal.Person;
import hotel.controller.service.ClientService;
import hotel.personal.client.Client;
import hotel.personal.employee.HotelEmployee;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AdminController extends BasicController{
    public AdminController(RoomService roomService, ClientService clientService, ServicesService servicesService, HotelEmployee employee) {
        super(roomService, clientService, servicesService, employee);
    }

    //изменить цену комнаты
    public void changeRoomPrice(int idRoom, int newPrice) {
        roomService.changeRoomPrice(idRoom, newPrice);
    }

    //изменить цену услуги
    public void changeServicePrice(String nameService, int newPrice) {
        servicesService.changeServicePrice(nameService, newPrice);
    }

    //добавить комнату
    public void addRoom(RoomCategory category, RoomStatus status, RoomType type, int capacity, int roomIndex, int price) {
        roomService.addRoom(category,status,type,capacity,roomIndex,price);
    }

    //добавить услугу
    public void addService(String nameService, String description, double price) {
        servicesService.addService(nameService,description,price);
    }

    public List<HotelEmployee> requestListPersonal(){
        return hotel.getPersonalMap().values().stream().toList();
    }

    public void exportEmployeeCSV(List<HotelEmployee> employees, String file_path) throws FileNotFoundException {
        try (PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file_path), StandardCharsets.UTF_8))) {
            printWriter.println("ID;Фамилия;Имя;Отчество;ДатаРождения;Должность");
            for (HotelEmployee employee : employees) {
                printWriter.println(employeeToCSVString(employee));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String employeeToCSVString(HotelEmployee employee){
        return String.join("; ",
                String.valueOf(employee.getId()),
                employee.getName(),
                employee.getSurname(),
                employee.getPatronymic(),
                employee.getDate_of_birth().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                employee.getPosition()
        );
    }

    public void exportClientsCSV(List<Client> clients, String file_path) throws FileNotFoundException {
        try (PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file_path), StandardCharsets.UTF_8))) {
            printWriter.println("ID;Фамилия;Имя;Отчество;ДатаРождения;ДатаЗаезда;ДатаВыезда;ДополнительныеУслуги;ID комнаты");
            for (Client client : clients) {
                printWriter.println(clientToCSVString(client));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static String clientToCSVString(Client client) {
        return String.join("; ",
                String.valueOf(client.getId()),
                client.getName(),
                client.getSurname(),
                client.getPatronymic(),
                client.getDate_of_birth().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                client.getDepartureDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                client.getCheckOutDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                client.getServicesList().stream()
                        .map(Services::getName)
                        .collect(Collectors.joining(", ")),
                String.valueOf(client.getIdRoom())
        );
    }

    //добавить персонал
    public void addPersonal(Collection<HotelEmployee> persons){
        for(HotelEmployee person : persons){
            hotel.getPersonalMap().put(person.getId(), person);
            System.out.println("Добавили нового члена команды: " + person.toString());
        }
    }

    //получить информацию о клиенте
    public void getInfoAboutClient(int idClient) {
        clientService.getInfoAboutClient(idClient);
    }

    //запрос на список комнат
    public void requestListRoom(FilterRoom filter) {
        roomService.requestListRoom(filter);
    }

    //последние 3 клиента
    public void requestLastThreeClient() {
        clientService.requestLastThreeClient();
    }
}
