package hotel.model.service;

import hotel.model.Hotel;
import hotel.model.filter.FilterServices;
import hotel.personal.employee.HotelEmployee;

import java.util.List;
import java.util.stream.Collectors;

public class ServicesService {
    protected final Hotel hotel = Hotel.getInstance();
    HotelEmployee employee;

    public ServicesService(HotelEmployee employee) {
        this.employee = employee;
    }

    public void changeServicePrice(String nameService, int newPrice){
        System.out.println(employee.getPosition() + " изменил цену услуги: " + nameService +
                " \nс "+ Services.findService(nameService).getPrice() + " на " + newPrice);
        Services.findService(nameService).setPrice(newPrice);
    }

    public void addService(int idClient, String name_service){
        hotel.getClientMap().get(idClient).addService(name_service);
        System.out.println(employee.getPosition() + " добавил услугу клиенту " + hotel.getClientMap().get(idClient).toString() + Services.findService(name_service).getDescription());
    }

    public void addService(String nameService, String description, double price){
        Services.addService(nameService, description, price);
        System.out.println(employee.getPosition() + " добавил новый вид услуги: " + Services.findService(nameService).getName());
    }

    public List<Services> sortServices(FilterServices filter){
        return hotel.getServicesMap().values().stream()
                .sorted(filter.getComparator())
                .collect(Collectors.toList());
    }

    public List<Services> requestListServices(FilterServices filter){
        int count = 1;
        for (Services services : sortServices(filter)){
            System.out.printf("%d) %s\n", count, services.toString());
            count++;
        }
        return sortServices(filter);
    }
}
