package hotel.personal.client;

import hotel.model.filter.FilterServices;
import hotel.model.service.Services;
import hotel.personal.Person;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Client extends Person {
    private static int lastId = 0;
    private final int id;
    private List<Services> servicesList = new ArrayList<>();
    private int idRoom;
    private LocalDate checkOutDate;
    private LocalDate departureDate;

    public List<Services> getServicesList() {
        return servicesList;
    }

    public Client(String name, String surname, String patronymic, int date_of_birth, int month_of_birth, int year_of_birth, LocalDate departureDate, LocalDate checkOutDate) {
        super(name, surname, patronymic, date_of_birth, month_of_birth, year_of_birth);
        System.out.println("Вы пригласили клиента");
        this.id = ++lastId;
        this.checkOutDate = checkOutDate;
        this.departureDate = departureDate;
    }

    public void addService(String nameService){
        Services service = Services.findService(nameService);
        if (service != null && !servicesList.contains(service)) {
            servicesList.add(service);
        }
    }

    public int getId() {
        return id;
    }

    public int getIdRoom() {
        return idRoom;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setIdRoom(int idRoom) {
        this.idRoom = idRoom;
    }

    @Override
    public String toString() {
        return "Client{" +
                "name='" + getName() + '\'' +
                ", surname='" + getSurname() + '\'' +
                ", patronymic='" + getPatronymic() + '\'' +
                '}';
    }

    public List<Services> sortServices(FilterServices filter){
        return servicesList.stream()
                .sorted(filter.getComparator())
                .collect(Collectors.toList());
    }
}
