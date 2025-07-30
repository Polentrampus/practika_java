package hotel.personal.employee.admin;

import hotel.personal.employee.HotelEmployee;

public class Admin extends HotelEmployee{
    public Admin(HotelEmployeeBuilder builder) {
        super(builder);
        System.out.println("Вы пригласили администратора");
    }

    @Override
    public String getPosition() {
        return "admin";
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
