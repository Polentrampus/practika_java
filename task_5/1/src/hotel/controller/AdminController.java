package hotel.controller;

import hotel.model.filter.FilterClient;
import hotel.model.filter.FilterRoom;
import hotel.model.filter.FilterServices;
import hotel.model.room.RoomCategory;
import hotel.model.room.RoomService;
import hotel.model.room.RoomStatus;
import hotel.model.room.RoomType;
import hotel.model.service.ServicesService;
import hotel.personal.Person;
import hotel.personal.client.ClientService;
import hotel.personal.employee.HotelEmployee;

import java.util.Collection;

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

    //добавить персонал
    public void addPersonal(Collection<Person> persons){
        for(Person person : persons){
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
