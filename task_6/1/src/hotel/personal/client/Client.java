package hotel.personal.client;

import hotel.model.room.RoomCategory;
import hotel.model.service.Services;
import hotel.personal.Person;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Client extends Person {
    private final List<Services> servicesList;
    private final int idRoom;
    private final LocalDate checkOutDate;
    private final LocalDate departureDate;

    public List<Services> getServicesList() {
        return servicesList;
    }

    public Client(ClientBuilder builder){
        super(builder);
        this.idRoom = builder.idRoom;
        System.out.println("Вы пригласили клиента");
        this.checkOutDate = builder.checkOutDate;
        this.departureDate = builder.departureDate;
        this.servicesList = new ArrayList<>(builder.servicesList);
    }

    public void addService(String nameService){
        Services service = Services.findService(nameService);
        if (service != null && !servicesList.contains(service)) {
            servicesList.add(service);
        }
    }

    public int getId() {
        return super.getId();
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

    @Override
    public String toString() {
        return "Client{" +
                "name=" + getName() + '\'' +
                ", surname=" + getSurname() + '\'' +
                ", patronymic=" + getPatronymic() + '\'' +
                ", birthDay=" + getDate_of_birth() + '\'' +
                ", servicesList=" + servicesList + '\'' +
                ", idRoom=" + idRoom + '\'' +
                ", checkOutDate=" + checkOutDate + '\'' +
                ", departureDate=" + departureDate + '\'' +
                '}';
    }

    public ClientBuilder toBuilder() {
        return new ClientBuilder()
                .name(getName())
                .surname(getSurname())
                .patronymic(getPatronymic())
                .date_of_birth(getDate_of_birth())
                .departureDate(departureDate)
                .checkOutDate(checkOutDate)
                .services(servicesList)
                .idRoom(idRoom);
    }

    public static class ClientBuilder extends Person.Builder<ClientBuilder>{
        private List<Services> servicesList = new ArrayList<>();
        private int idRoom;
        private LocalDate checkOutDate;
        private LocalDate departureDate;

        public ClientBuilder checkOutDate(LocalDate date){
            this.checkOutDate = date;
            return this;
        }

        public ClientBuilder idRoom(int idRoom) {
            this.idRoom = idRoom;
            return this;
        }

        public ClientBuilder departureDate(LocalDate departureDate){
            this.departureDate = departureDate;
            return this;
        }

        public ClientBuilder services(Collection<Services> services){
            this.servicesList.clear(); // Очищаем перед добавлением
            if (services != null) {
                for (Services service : services) {
                    if (service != null && !this.servicesList.contains(service)) {
                        this.servicesList.add(service);
                    }
                }
            }
            return this;
        }

        @Override
        protected ClientBuilder self() {
            return this;
        }

        @Override
        public Client build() {
            return new Client(this);
        }
    }

//    public List<Services> sortServices(FilterServices filter){
//        return servicesList.stream()
//                .sorted(filter.getComparator())
//                .collect(Collectors.toList());
//    }

}
