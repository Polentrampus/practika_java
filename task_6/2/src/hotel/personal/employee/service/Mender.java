package hotel.personal.employee.service;

import hotel.model.Hotel;
import hotel.model.room.Room;
import hotel.model.room.RoomStatus;
import hotel.personal.Person;

public class Mender extends ServicePersonnelHotel{
    private boolean isFixing = false;
    public Mender(HotelEmployeeBuilder builder) {
        super(builder);
        System.out.println("Вы пригласили мастера" );
    }

    @Override
    public String getPosition() {
        return "Мастер";
    }

    @Override
    public String toString() {
        return "Mender{" +
                "name='" + getName() + '\'' +
                ", surname='" + getSurname() + '\'' +
                ", patronymic='" + getPatronymic() + '\'' +
                '}';
    }

    public boolean isCleaning() {
        return isFixing;
    }

    @Override
    public void update(int roomId) {
        this.isFixing = true;
        System.out.printf("Мастер %s получил запрос на обслуживание номера %d\n",
                toString(), roomId);
        cleanRoom(roomId);
    }

    private void cleanRoom(int roomId) {
        System.out.printf("Мастер %s начал решать проблему номера %d\n",
                toString(), roomId);
        System.out.printf("Мастер %s закончил решать проблему номера %d\n",
                toString(), roomId);
        this.isFixing = false;

        Room room = Hotel.getInstance().getRoomMap().get(roomId);
        if (room != null) {
            room.setStatus(RoomStatus.AVAILABLE);
            System.out.printf("Номер %d теперь доступен\n", roomId);
        }
    }
}
