package hotel.personal.employee.service;

import hotel.personal.Observer;
import hotel.personal.Person;
import hotel.personal.employee.HotelEmployee;

import java.time.LocalDate;

public abstract class ServicePersonnelHotel  extends HotelEmployee implements Observer {
    public ServicePersonnelHotel(HotelEmployeeBuilder builder) {
        super(builder);
    }

    @Override
    public abstract void update(int roomId);
}
