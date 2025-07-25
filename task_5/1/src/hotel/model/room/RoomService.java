package hotel.model.room;

import hotel.model.Hotel;
import hotel.model.filter.FilterRoom;
import hotel.personal.employee.HotelEmployee;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RoomService {
    protected final Hotel hotel = Hotel.getInstance();
    HotelEmployee employee;

    public RoomService(HotelEmployee employee) {
        this.employee = employee;
    }

    public List<Room> listAvailableRooms(FilterRoom filter){
        List<Room> roomsAvailable = new ArrayList<>();
        sortRooms(filter);
        for (Room room : hotel.getRoomMap().values()){
            if (room.getStatus() == RoomStatus.AVAILABLE) {
                roomsAvailable.add(room);
                System.out.println(room.toString());
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
        return hotel.getRoomMap().values().stream()
                .sorted(filter.getComparator())
                .collect(Collectors.toList());
    }

    public void changeRoomPrice(int idRoom, int newPrice){
        System.out.println("Админ изменил цену комнаты номер: " + idRoom +
                " \nс "+hotel.getRoomMap().get(idRoom).getPrice() + " на " + newPrice);
        hotel.getRoomMap().get(idRoom).setPrice(newPrice);
    }

    public void addRoom(RoomCategory category, RoomStatus status, RoomType type,int capacity,  int roomIndex, int price){
        hotel.getRoomMap().put(roomIndex, new Room(category, status, type, capacity, roomIndex, price));
        System.out.println("Админ добавил новую комнату: " + hotel.getRoomMap().get(roomIndex).toString());
    }

    public void requestListRoom(FilterRoom filter){
        for (Room room : sortRooms(filter)){
            System.out.println(room.toString());
        }
    }

    public void setStatusRoom(int roomId, RoomStatus status){
        System.out.printf("%s изменил состояние комнаты %d с %s на %s\n", employee.getPosition(), roomId, hotel.getRoomMap().get(roomId).getStatus(), status);
        hotel.getRoomMap().get(roomId).setStatus(status);
    }
}
