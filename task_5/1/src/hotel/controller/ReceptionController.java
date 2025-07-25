package hotel.controller;

import hotel.model.room.Room;
import hotel.model.room.RoomCategory;
import hotel.model.room.RoomService;
import hotel.model.room.RoomStatus;
import hotel.model.service.Services;
import hotel.model.service.ServicesService;
import hotel.personal.client.Client;
import hotel.personal.client.ClientService;
import hotel.personal.employee.HotelEmployee;

import java.util.Collection;

public class ReceptionController extends BasicController{
    public ReceptionController(RoomService roomService, ClientService clientService, ServicesService servicesService, HotelEmployee employee) {
        super(roomService, clientService, servicesService, employee);
    }

    public void fillsForm(Client client1, RoomCategory roomCategory, Collection<Services> services) {
        addClient(client1);
        for (Room room : hotel.getRoomMap().values()){
            if(room.getStatus() == RoomStatus.AVAILABLE) {
                settle(client1.getId(), room.getId());
                break;
            }
        }
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
    }


}
