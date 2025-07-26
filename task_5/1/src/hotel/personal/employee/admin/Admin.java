package hotel.personal.employee.admin;

import hotel.personal.employee.HotelEmployee;

public class Admin extends HotelEmployee{

    public Admin(String name, String surname, String patronymic, int date_of_birth, int month_of_birth,
                 int year_of_birth) {
        super(name, surname, patronymic, date_of_birth, month_of_birth, year_of_birth);

        System.out.println("Вы пригласили администратора");
    }

    @Override
    public String toString() {
        return "Admin{" +
                "name='" + getName() + '\'' +
                ", surname='" + getSurname() + '\'' +
                ", patronymic='" + getPatronymic() + '\'' +
                '}';
    }

    @Override
    public String getPosition() {
        return "Админ";
    }
}
