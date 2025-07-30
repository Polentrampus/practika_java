package hotel.controller;

import hotel.exeption.HotelException;
import hotel.model.room.Room;
import hotel.model.room.RoomCategory;
import hotel.controller.service.RoomService;
import hotel.model.room.RoomStatus;
import hotel.model.service.Services;
import hotel.controller.service.ServicesService;
import hotel.personal.client.Client;
import hotel.controller.service.ClientService;
import hotel.personal.employee.HotelEmployee;

import java.util.Collection;

public class ReceptionController extends BasicController{
    public ReceptionController(RoomService roomService, ClientService clientService, ServicesService servicesService, HotelEmployee employee) {
        super(roomService, clientService, servicesService, employee);
    }

    //добавить проверку
    public void fillsForm(Client client1, RoomCategory roomCategory, Collection<Services> services) throws HotelException {
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
        for (Room room : hotel.getRoomMap().values()){
            if(room.getStatus() == RoomStatus.AVAILABLE) {
                settle(client1, room.getId());
                break;
            }
        }
    }


}
