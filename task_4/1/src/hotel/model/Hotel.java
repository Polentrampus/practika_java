package hotel.model;
import hotel.model.filter.FilterClient;
import hotel.model.filter.FilterRoom;
import hotel.model.filter.FilterServices;
import hotel.model.room.*;
import hotel.model.service.Services;
import hotel.personal.client.Client;
import hotel.personal.employee.admin.Admin;
import hotel.personal.employee.service.Maid;
import hotel.personal.employee.service.Mender;
import hotel.personal.Person;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Hotel {
    private static final Hotel instance = new Hotel();
    private static final Map<Integer, Room> roomMap = new HashMap<>();
    private final Map<Integer, Person> personalMap = new HashMap<>();
    private static final Map<Integer, Client> clientMap = new HashMap<>();
    private final Map<String, Services> servicesMap = Services.getSERVICES();
    private static LocalDate currentDate = LocalDate.now();

    static{
        roomMap.put(100, new Room(RoomCategory.ECONOMY, RoomStatus.AVAILABLE, RoomType.SUITE,2, 100, 5000));
        roomMap.put(101, new Room(RoomCategory.BUSINESS, RoomStatus.AVAILABLE, RoomType.STANDARD, 3, 101, 4000));
        roomMap.put(102, new Room(RoomCategory.PREMIUM, RoomStatus.CLEANING, RoomType.FAMILY, 2, 102, 7000));
        roomMap.put(200, new Room(RoomCategory.ECONOMY, RoomStatus.AVAILABLE, RoomType.APARTMENT, 4, 200, 5000));
        roomMap.put(201, new Room(RoomCategory.PREMIUM, RoomStatus.AVAILABLE, RoomType.STANDARD, 6, 201, 2400));
        roomMap.put(202, new Room(RoomCategory.BUSINESS, RoomStatus.AVAILABLE, RoomType.PRESIDENTIAL, 2, 202, 2100));
    }

    private Hotel(){
        System.out.println("Здравствуйте, это отель ***!");
    }
    public static Hotel getInstance(){
        return instance;
    }

    public List<Maid> getAvailableMaids() {
        List<Maid> maids = new ArrayList<>();
        for (Person person : personalMap.values()) {
            if (person instanceof Maid) {
                maids.add((Maid) person);
            }
        }
        return maids;
    }

    public List<Mender> getAvailableMender() {
        List<Mender> menders = new ArrayList<>();
        for (Person person : personalMap.values()) {
            if (person instanceof Mender) {
                menders.add((Mender) person);
            }
        }
        return menders;
    }

    public Map<Integer, Room> getRoomMap() {
        return roomMap;
    }
    public Map<Integer, Person> getPersonalMap() {
        return personalMap;
    }
    public Map<Integer, Client> getClientMap() {
        return clientMap;
    }
    public Map<String, Services> getServicesMap() {return servicesMap;}


    public void countClient(){
        System.out.println("Общее число постояльцев составляет: "+ clientMap.size());
    }

    public List<Room> listAvailableRooms(FilterRoom filter){
        List<Room> roomsAvailable = new ArrayList<>();
        sortRooms(filter);
        for (Room room : roomMap.values()){
            if (room.getStatus() == RoomStatus.AVAILABLE) {
                roomsAvailable.add(room);
            }
        }
        return roomsAvailable;
    }

    public List<Room> listAvailableRoomsByDate(FilterRoom filter, LocalDate date){
        List<Room> roomsAvailable = listAvailableRooms(filter);
        if (roomsAvailable.isEmpty())
            return null;

        System.out.printf("Список свободных к %tF дате комнат:\n", date);
        List<Room> result = new ArrayList<>();
        for (Room room : roomsAvailable) {
            if (room.getStatus() == RoomStatus.AVAILABLE) {
                result.add(room);
                System.out.println(room.toString());
            }
        }
        return result;
    }

    public List<Room> sortRooms(FilterRoom filter){
        return roomMap.values().stream()
                .sorted(filter.getComparator())
                .collect(Collectors.toList());
    }

    public List<Services> sortServices(FilterServices filter){
        return servicesMap.values().stream()
                .sorted(filter.getComparator())
                .collect(Collectors.toList());
    }

    public List<Client> sortClient(FilterClient filter){
        return clientMap.values().stream()
                .sorted(filter.getComparator())
                .collect(Collectors.toList());
    }

    public void update(Admin admin){
        Iterator<Client> iterator = clientMap.values().iterator();
        while (iterator.hasNext()) {
            Client client = iterator.next();
            if (client.getCheckOutDate().isAfter(currentDate)) {
                admin.evict(client.getId());
                iterator.remove();
            }
        }
    }
}

