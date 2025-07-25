package hotel.controller;

import hotel.model.Hotel;
import hotel.model.filter.FilterClient;
import hotel.model.filter.FilterRoom;
import hotel.model.filter.FilterServices;
import hotel.model.room.Room;
import hotel.model.room.RoomService;
import hotel.model.room.RoomStatus;
import hotel.model.service.Services;
import hotel.model.service.ServicesService;
import hotel.personal.client.Client;
import hotel.personal.client.ClientService;
import hotel.personal.employee.HotelEmployee;
import hotel.personal.employee.service.Maid;
import hotel.personal.employee.service.Mender;

import java.util.Collection;
import java.util.List;

public class BasicController {
    protected final Hotel hotel = Hotel.getInstance();
    protected final RoomService roomService;
    protected final ClientService clientService;
    protected final ServicesService servicesService;
    protected final HotelEmployee employee;

    public BasicController(RoomService roomService, ClientService clientService,
                           ServicesService servicesService, HotelEmployee employee) {
        this.roomService = roomService;
        this.clientService = clientService;
        this.servicesService = servicesService;
        this.employee = employee;
    }

    public void addClient(Client person) {
        hotel.getClientMap().put(person.getId(), person);
        System.out.println(employee.getPosition() + " добавил клиента: " + person);
    }

    public void addClientServices(int idClient, Collection<Services> services){
        for (Services service : services){
            servicesService.addService(idClient, service.getName());
        }
    }

    public void cleaningRequest(int roomId) {
        Room room = hotel.getRoomMap().get(roomId);
        if (room != null) {
            room.setStatus(RoomStatus.CLEANING);
            System.out.printf("%s изменил статус комнаты %d на %s\n",
                    employee.getPosition(), roomId, room.getStatus().getDescription());
            List<Maid> maids = hotel.getAvailableMaids();

            if (maids.isEmpty()) {
                System.out.println("Нет доступных горничных для уборки номера " + roomId);
                return;
            }

            Maid availableMaid = null;
            for(Maid maid : maids){
                if(!maid.isCleaning())
                    availableMaid = maid;break;
            }

            if (availableMaid != null) {
                availableMaid.update(roomId);
            } else {
                System.out.println("Все горничные заняты, номер " + roomId + " будет убран позже");
            }
        }
    }

    //запрос на список услуг
    public List<Services> requestListServices(FilterServices filter) {
        return servicesService.requestListServices(filter);
    }

    //запрос на список свободных комнат
    public List<Room> requestListAvailableRoom(FilterRoom filter) {
        return roomService.listAvailableRooms(filter);
    }

    //запрос на список услуг клиента
    public void requestListServicesClient(FilterServices filter, int idClient) {
        clientService.requestListServicesClient(filter, idClient);
    }

    public void repairRequest(int roomId) {
        Room room = hotel.getRoomMap().get(roomId);
        if (room != null) {
            room.setStatus(RoomStatus.MAINTENANCE);
            System.out.printf("%s изменил статус комнаты %d на %s\n",
                    employee.getPosition(), roomId, room.getStatus().getDescription());
            List<Mender> menders = hotel.getAvailableMender();

            if (menders.isEmpty()) {
                System.out.println("Нет доступных мастеров для устранения проблемы в номере " + roomId);
                return;
            }

            Mender availableMender = null;
            for(Mender mender : menders){
                if(!mender.isCleaning())
                    availableMender = mender;break;
            }

            if (availableMender != null) {
                availableMender.update(roomId);
            } else {
                System.out.println("Все мастера заняты, номер " + roomId + " будет отремонтирован позже");
            }        }
    }

    public void settle(int idClient, int idRoom){
        if(hotel.getRoomMap().get(idRoom).getStatus() == RoomStatus.AVAILABLE){
            hotel.getRoomMap().get(idRoom).setStatus(RoomStatus.OCCUPIED);
            hotel.getClientMap().get(idClient).setIdRoom(idRoom);
            System.out.printf("%s поселил клиента %s", employee.getPosition() ,hotel.getClientMap().get(idClient).toString() +
                    " в номер:" + hotel.getClientMap().get(idClient).getIdRoom() + "\n");
            return;
        }
        System.out.println("Не удалось заселить в выбранный номер " + idRoom +
                "\nДанный номер находится в статусе: " + hotel.getRoomMap().get(idRoom).getStatus().getDescription());
    }

    public void requestListClient(FilterClient filter) {
        clientService.requestListClient(filter);
    }

    public void givOutCheck(int idClient){
        int sum = 0;
        Client client = hotel.getClientMap().get(idClient);
        int idRoom = client.getIdRoom();

        sum += hotel.getRoomMap().get(idRoom).getPrice();
        for(Services service : client.getServicesList()){
            sum += (int) service.getPrice();
        }
        System.out.printf("%s предоставил счет клиенту в размере: %d\n",employee.getPosition(), sum);
    }

    public void evict(int idClient){
        givOutCheck(idClient);
        Client client = hotel.getClientMap().get(idClient);
        if (client == null) {
            System.out.println("Клиент с ID " + idClient + " не найден");
            return;
        }

        int roomId = client.getIdRoom();
        if (roomId == 0) {
            System.out.println("У клиента " + client + " не указан номер");
            return;
        }

        Room room = hotel.getRoomMap().get(roomId);
        if (room == null) {
            System.out.println("Номер " + roomId + " не существует");
            return;
        }

        System.out.println("Клиент: " + client + " успешно выселился");
        client.setIdRoom(0);
        cleaningRequest(roomId);
    }
}
