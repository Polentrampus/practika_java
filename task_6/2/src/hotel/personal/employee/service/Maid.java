package hotel.personal.employee.service;

import hotel.model.Hotel;
import hotel.model.room.Room;
import hotel.model.room.RoomStatus;
import hotel.personal.Person;

public class Maid extends ServicePersonnelHotel{
    private boolean isCleaning = false;

    public Maid(HotelEmployeeBuilder builder) {
        super(builder);
        System.out.println("Вы пригласили горничную" );
    }

    @Override
    public String getPosition() {
        return "Горничная";
    }

    @Override
    public String toString() {
        return "Maid{" +
                "name='" + getName() + '\'' +
                ", surname='" + getSurname() + '\'' +
                ", patronymic='" + getPatronymic() + '\'' +
                '}';
    }

    public boolean isCleaning() {
        return isCleaning;
    }

    @Override
    public void update(int roomId) {
        this.isCleaning = true;
        System.out.printf("Горничная %s получила запрос на уборку номера %d\n",
                toString(), roomId);
        cleanRoom(roomId);
    }

    private void cleanRoom(int roomId) {
        System.out.printf("Горничная %s начала уборку номера %d\n",
                toString(), roomId);
        System.out.printf("Горничная %s закончила уборку номера %d\n",
                toString(), roomId);
        this.isCleaning = false;

        // Обновляем статус комнаты
        Room room = Hotel.getInstance().getRoomMap().get(roomId);
        if (room != null) {
            room.setStatus(RoomStatus.AVAILABLE);
            System.out.printf("Номер %d теперь доступен\n", roomId);
        }
    }
}
