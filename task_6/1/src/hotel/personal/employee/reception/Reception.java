package hotel.personal.employee.reception;

import hotel.personal.employee.HotelEmployee;

public class Reception extends HotelEmployee {
    public Reception(HotelEmployeeBuilder builder) {
        super(builder);
        builder.position("reception");
        System.out.println("Вы пригласили ресепшиониста");
    }

    @Override
    public String getPosition() {
        return "reception";
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
