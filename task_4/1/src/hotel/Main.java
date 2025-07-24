package hotel;

import hotel.model.Hotel;
import hotel.model.filter.FilterClient;
import hotel.model.filter.FilterRoom;
import hotel.model.filter.FilterServices;
import hotel.model.room.*;
import hotel.model.service.Services;
import hotel.personal.client.Client;
import hotel.personal.employee.admin.Admin;
import hotel.personal.employee.reception.Reception;
import hotel.personal.employee.service.Maid;
import hotel.personal.employee.service.Mender;
import hotel.personal.employee.service.ServicePersonnelHotel;

import java.time.LocalDate;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Hotel hotel = Hotel.getInstance();

        Admin admin = new Admin("Вася", "Васин", "Васильевич", 27, 4, 2005);
        Reception reception = new Reception("Иван", "Иванов", "Иванович", 17, 3, 1990);
        ServicePersonnelHotel maid_1 = new Maid("Лена", "Рыжова", "Еленина", 2, 7, 2001);
        ServicePersonnelHotel mender = new Mender("Петя", "Кек", "Кекович", 4, 2, 1998);

        admin.addPersonal(Arrays.asList(reception, maid_1, mender));
        admin.addRoom(RoomCategory.ECONOMY, RoomStatus.AVAILABLE, RoomType.STANDARD, 3, 203, 13000);
        admin.addService("Мини бар", "В стоимость номера будет включен мини бар", 100.3);


        Client client_1 = new Client("Володя","Аол","Лолович", 12,
                12, 1987, LocalDate.of(2025, 7, 4),
                LocalDate.of(2025, 8, 12));
        Client client_2 = new Client("Пипип","Гка","авмва", 12,
                12, 1987, LocalDate.of(2025, 5, 3),
                LocalDate.of(2025, 6, 23));
        Client client_3 = new Client("нрнрн","Вррт","авипаип", 12,
                12, 1987, LocalDate.of(2024, 6, 2),
                LocalDate.of(2024, 9, 24));
        Client client_4 = new Client("цыцыы","Бфйф","яфяфяф", 12,
                12, 1987, LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 10, 25));

        reception.fillsForm(client_1, RoomCategory.ECONOMY, Arrays.asList(Services.findService("Хранение багажа"),
                Services.findService("Мини бар")));
        reception.fillsForm(client_2, RoomCategory.BUSINESS, Arrays.asList(Services.findService("Фитнес-центр"),
                Services.findService("Мини бар")));
        reception.fillsForm(client_3, RoomCategory.ECONOMY, Arrays.asList(Services.findService("Трансфер"),
                Services.findService("Мини бар")));
        reception.fillsForm(client_4, RoomCategory.BUSINESS, null);

        hotel.update(admin);                                    //обновляем информацию каждый божий день выселяя клиентов которые отжили
        admin.requestListServices(FilterServices.PRICE);        //лист услуг
        admin.requestListAvailableRoom(FilterRoom.PRICE);       //список свободных комнат (сортировка по цене)
        admin.requestListServicesClient(FilterServices.PRICE, client_2.getId());        //лист услуг конкретного человека
        admin.requestLastThreeClient();                         //последние три клиента
        admin.getInfoAboutClient(client_2.getId());             //информация о конкретном клиенте
        admin.requestListClient(FilterClient.DATECHECKUP);      //лист клиентов(сортировка по дате отъезда)
        //Свободные номера к конкретной дате
        hotel.listAvailableRoomsByDate(FilterRoom.ID, LocalDate.of(2025, 6, 12));
    }
}