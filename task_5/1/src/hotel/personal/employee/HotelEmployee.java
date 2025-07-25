package hotel.personal.employee;

import hotel.model.Hotel;
import hotel.model.room.Room;
import hotel.model.room.RoomStatus;
import hotel.model.service.Services;
import hotel.personal.Person;
import hotel.personal.client.Client;
import hotel.personal.employee.service.Maid;
import hotel.personal.employee.service.Mender;

import java.util.List;

public abstract class HotelEmployee extends Person {
    protected final Hotel hotel = Hotel.getInstance();

    public HotelEmployee(String name, String surname, String patronymic,
                         int date_of_birth, int month_of_birth, int year_of_birth) {
        super(name, surname, patronymic, date_of_birth, month_of_birth, year_of_birth);
    }
    public abstract String getPosition();
}