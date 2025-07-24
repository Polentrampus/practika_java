package hotel.personal.employee.admin;

import hotel.model.filter.FilterClient;
import hotel.model.filter.FilterRoom;
import hotel.model.filter.FilterServices;
import hotel.model.room.*;
import hotel.model.service.Services;
import hotel.personal.client.Client;
import hotel.personal.employee.HotelEmployee;
import hotel.personal.Person;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Admin extends HotelEmployee {
    public Admin(String name, String surname, String patronymic, int date_of_birth, int month_of_birth, int year_of_birth) {
        super(name, surname, patronymic, date_of_birth, month_of_birth, year_of_birth);
        System.out.println("Вы пригласили администратора");
        this.addPersonal(Arrays.asList(this));
    }

    public void changeRoomPrice(int idRoom, int newPrice){
        System.out.println("Админ изменил цену комнаты номер: " + idRoom +
                " \nс "+hotel.getRoomMap().get(idRoom).getPrice() + " на " + newPrice);
        hotel.getRoomMap().get(idRoom).setPrice(newPrice);
    }

    public void changeServicePrice(String nameService, int newPrice){
        System.out.println("Админ изменил цену услуги: " + nameService +
                " \nс "+ Services.findService(nameService).getPrice() + " на " + newPrice);
        Services.findService(nameService).setPrice(newPrice);
    }

    public void addRoom(RoomCategory category, RoomStatus status, RoomType type,int capacity,  int roomIndex, int price){
        hotel.getRoomMap().put(roomIndex, new Room(category, status, type, capacity, roomIndex, price));
        System.out.println("Админ добавил новую комнату: " + hotel.getRoomMap().get(roomIndex).toString());
    }

    public void addService(int idClient, String name_service){
        hotel.getClientMap().get(idClient).addService(name_service);
        System.out.println("Админ добавил услугу клиенту " + hotel.getClientMap().get(idClient).toString() + Services.findService(name_service).getDescription());
    }

    public void addService(String nameService, String description, double price){
        Services.addService(nameService, description, price);
        System.out.println("Админ добавил новый вид услуги: " + Services.findService(nameService).getName());
    }

    public void addPersonal(Collection<Person> persons){
        for(Person person : persons){
            hotel.getPersonalMap().put(person.getId(), person);
            System.out.println("Добавили нового члена команды: " + person.toString());
        }
    }

    @Override
    public String getPosition() {
        return "Админ";
    }

    public void getInfoAboutClient(int idClient){
        Client client = hotel.getClientMap().get(idClient);
        Room room = hotel.getRoomMap().get(client.getIdRoom());
        System.out.printf(client.toString() + " проживает в комнате %d типа %s категории %s и по цене %d рублей\n", room.getId(),
                room.getType(), room.getCategory(), room.getPrice());

    }

    public void requestListRoom(FilterRoom filter){
        for (Room room : hotel.sortRooms(filter)){
            System.out.println(room.toString());
        }
    }

    public void requestListServices(FilterServices filter){
        for (Services services : hotel.sortServices(filter)){
            System.out.println(services.toString());
        }
    }

    public void requestListClient(FilterClient filter){
        for (Client client : hotel.sortClient(filter)){
            String formatted = String.format("Клиент %s живет в комнате %d и его дата проживания в номере: %tF - %tF", client.toString(), client.getIdRoom(), client.getDepartureDate(), client.getCheckOutDate());
            System.out.println(formatted);
        }
    }

    public void requestLastThreeClient(){
        System.out.printf("%s сделал запрос на список последних трех человек:\n", getPosition());
        List<Client> clientList = hotel.sortClient(FilterClient.ID);
        for (int i = clientList.size() - 1; (clientList.size() - 3 < 0 ? i <= 0 : i <= clientList.size() - 3); i--){
            System.out.println(clientList.get(i).toString());
        }
    }

    public void requestListAvailableRoom(FilterRoom filter){
        System.out.printf("%s сделал запрос на список свободных комнат:\n", getPosition());
        for (Room room : hotel.listAvailableRooms(filter)){
            System.out.println(room.toString());
        }
    }

    public void requestListServicesClient(FilterServices filter, int idClient){
        if(hotel.getClientMap().get(idClient) == null){
            System.out.println("Такого клиента не существует!");
            return;
        }
        System.out.printf("%s сделал запрос на список услуг %s :\n", getPosition(), hotel.getClientMap().get(idClient).toString());
        for (Services service : hotel.getClientMap().get(idClient).sortServices(filter)){
            System.out.println(service.toString());
        }
    }

    @Override
    public String toString() {
        return "Admin{" +
                "name='" + getName() + '\'' +
                ", surname='" + getSurname() + '\'' +
                ", patronymic='" + getPatronymic() + '\'' +
                '}';
    }



}
