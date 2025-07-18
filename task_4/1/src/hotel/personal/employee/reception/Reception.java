package hotel.personal.employee.reception;

import hotel.model.Hotel;
import hotel.model.room.Room;
import hotel.model.room.RoomCategory;
import hotel.model.room.RoomStatus;
import hotel.model.room.RoomType;
import hotel.model.service.Services;
import hotel.personal.employee.HotelEmployee;
import hotel.personal.client.Client;

import java.time.LocalDate;
import java.util.Collection;

public class Reception extends HotelEmployee {
    private static Hotel hotel = Hotel.getInstance();

    public Reception(String name, String surname, String patronymic, int date_of_birth, int month_of_birth, int year_of_birth) {
        super(name, surname, patronymic, date_of_birth, month_of_birth, year_of_birth);
        System.out.println("Вы пригласили ресепшиониста");
    }


    public void setStatusRoom(int roomId, RoomStatus status){
        System.out.printf("%s изменил состояние комнаты %d с %s на %s\n", getPosition(), roomId, hotel.getRoomMap().get(roomId).getStatus(), status);
        hotel.getRoomMap().get(roomId).setStatus(status);
    }

    @Override
    public String getPosition() {
        return "Ресепшионист";
    }

    @Override
    public String toString() {
        return "Reception{" +
                "name='" + getName() + '\'' +
                ", surname='" + getSurname() + '\'' +
                ", patronymic='" + getPatronymic() + '\'' +
                '}';
    }

    public void fillsForm(Client client1, RoomCategory roomCategory, Collection<Services> services) {
        addClient(client1);
        for (Room room : hotel.getRoomMap().values()){
            if(room.getStatus() == RoomStatus.AVAILABLE) {
                settle(client1.getId(), room.getId());
                break;
            }
        }
        if(services == null){
            return;
        }
        int count = 1;
        System.out.println("Клиент добавил следующие услуги:");
        for(Services service : services){
            if(service == null){
                break;
            }
            System.out.printf("%d) %s\n", count, service.getDescription());
            count++;
            client1.getServicesList().add(service);
        }
    }


}
