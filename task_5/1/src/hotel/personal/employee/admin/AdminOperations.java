package hotel.personal.employee.admin;

import hotel.model.filter.FilterClient;
import hotel.model.filter.FilterRoom;
import hotel.model.filter.FilterServices;
import hotel.model.room.RoomCategory;
import hotel.model.room.RoomStatus;
import hotel.model.room.RoomType;
import hotel.personal.Person;

import java.util.Collection;

public interface AdminOperations {
    void changeRoomPrice(int idRoom, int newPrice);
    void changeServicePrice(String nameService, int newPrice);
    void addRoom(RoomCategory category, RoomStatus status, RoomType type, int capacity, int roomIndex, int price);
    void addService(String nameService, String description, double price);
    void addPersonal(Collection<Person> persons);
    void getInfoAboutClient(int idClient);
    void requestListRoom(FilterRoom filter);
    void requestListServices(FilterServices filter);
    void requestListClient(FilterClient filter);
    void requestLastThreeClient();
    void requestListAvailableRoom(FilterRoom filter);
    void requestListServicesClient(FilterServices filter, int idClient);
}
