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
    public Reception(String name, String surname, String patronymic, int date_of_birth, int month_of_birth, int year_of_birth) {
        super(name, surname, patronymic, date_of_birth, month_of_birth, year_of_birth);
        System.out.println("Вы пригласили ресепшиониста");
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
}
