package hotel.model;
import hotel.controller.BasicController;
import hotel.model.filter.FilterClient;
import hotel.model.filter.FilterRoom;
import hotel.model.filter.FilterServices;
import hotel.model.room.*;
import hotel.model.service.Services;
import hotel.personal.client.Client;
import hotel.personal.employee.HotelEmployee;
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
    private final Map<Integer, HotelEmployee> personalMap = new HashMap<>();
    private static final Map<Integer, Client> clientMap = new HashMap<>();
    private final Map<String, Services> servicesMap = Services.getSERVICES();
    private static LocalDate currentDate = LocalDate.now();

    static{
        roomMap.put(100, new Room(RoomCategory.ECONOMY, RoomStatus.MAINTENANCE, RoomType.SUITE,2, 100, 5000));
        roomMap.put(101, new Room(RoomCategory.BUSINESS, RoomStatus.AVAILABLE, RoomType.STANDARD, 3, 101, 4000));
        roomMap.put(102, new Room(RoomCategory.PREMIUM, RoomStatus.AVAILABLE, RoomType.FAMILY, 2, 102, 7000));
        roomMap.put(200, new Room(RoomCategory.ECONOMY, RoomStatus.AVAILABLE, RoomType.APARTMENT, 4, 200, 5000));
        roomMap.put(201, new Room(RoomCategory.PREMIUM, RoomStatus.AVAILABLE, RoomType.STANDARD, 6, 201, 2400));
        roomMap.put(202, new Room(RoomCategory.BUSINESS, RoomStatus.AVAILABLE, RoomType.PRESIDENTIAL, 2, 202, 2100));
        roomMap.put(300, new Room(RoomCategory.ECONOMY, RoomStatus.AVAILABLE, RoomType.STANDARD, 2, 300, 2500));
        roomMap.put(301, new Room(RoomCategory.BUSINESS, RoomStatus.AVAILABLE, RoomType.DELUXE, 1, 301, 5500));
        roomMap.put(302, new Room(RoomCategory.ECONOMY, RoomStatus.AVAILABLE, RoomType.STANDARD, 2, 302, 2550));
        roomMap.put(303, new Room(RoomCategory.PREMIUM, RoomStatus.AVAILABLE, RoomType.APARTMENT, 3, 303, 2800));
        roomMap.put(304, new Room(RoomCategory.BUSINESS,RoomStatus.AVAILABLE,RoomType.DELUXE,4,304,1200));
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
    public Map<Integer, HotelEmployee> getPersonalMap() {
        return personalMap;
    }
    public Map<Integer, Client> getClientMap() {
        return clientMap;
    }
    public Map<String, Services> getServicesMap() {return servicesMap;}


    public void update(BasicController controller){
        Iterator<Client> iterator = clientMap.values().iterator();
        while (iterator.hasNext()) {
            Client client = iterator.next();
            if (client.getCheckOutDate().isAfter(currentDate)) {
                controller.evict(client.getId());
                iterator.remove();
            }
        }
    }
}

