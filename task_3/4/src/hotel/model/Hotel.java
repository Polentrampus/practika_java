package hotel.model;
import hotel.model.room.Room;
import hotel.model.room.RoomCategory;
import hotel.model.room.RoomStatus;
import hotel.model.room.RoomType;
import hotel.personal.client.Client;
import hotel.personal.employee.service.Maid;
import hotel.personal.employee.service.Mender;
import hotel.personal.Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hotel {
    private static final Hotel instance = new Hotel();

    private Hotel(){
        System.out.println("Здравствуйте, это отель ***!");
    }
    public static Hotel getInstance(){
        return instance;
    }

    private static final Map<Integer, Room> roomMap = new HashMap<>();

    private final Map<Integer, Person> personalMap = new HashMap<>();
    private final Map<Integer, Client> clientMap = new HashMap<>();

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

    static{
        roomMap.put(100, new Room(RoomCategory.ECONOMY, RoomStatus.AVAILABLE, RoomType.SUITE, 100, 5000));
        roomMap.put(101, new Room(RoomCategory.BUSINESS, RoomStatus.OCCUPIED, RoomType.STANDARD, 101, 4000));
        roomMap.put(102, new Room(RoomCategory.PREMIUM, RoomStatus.CLEANING, RoomType.FAMILY, 102, 7000));
        roomMap.put(200, new Room(RoomCategory.ECONOMY, RoomStatus.AVAILABLE, RoomType.APARTMENT, 200, 5000));
        roomMap.put(201, new Room(RoomCategory.PREMIUM, RoomStatus.MAINTENANCE, RoomType.STANDARD, 201, 24000));
        roomMap.put(202, new Room(RoomCategory.BUSINESS, RoomStatus.OCCUPIED, RoomType.PRESIDENTIAL, 202, 21000));
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
}

