package hotel.controller.service;

import hotel.exeption.HotelException;
import hotel.model.Hotel;
import hotel.model.filter.FilterClient;
import hotel.model.filter.FilterServices;
import hotel.model.room.Room;
import hotel.model.service.Services;
import hotel.personal.client.Client;
import hotel.personal.employee.HotelEmployee;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ClientService {
    protected final Hotel hotel = Hotel.getInstance();
    private final HotelEmployee employee;

    public ClientService(HotelEmployee employee) {
        this.employee = employee;
    }

    public void getCountClient(){
        System.out.println("Общее число постояльцев составляет: "+ hotel.getClientMap().size());
    }

    public List<Client> readClients(String filePath) throws IOException {
        return Files.lines(Path.of(filePath))
                .skip(1)
                .map(this::parseLine)
                .collect(Collectors.toList());
    }

    private Client parseLine(String line) {
        String[] parts = line.split(",\\s*");

        try {
            return new Client.ClientBuilder()
                    .id(Integer.parseInt(parts[0]))
                    .surname(parts[1])
                    .patronymic(parts[2])
                    .date_of_birth(LocalDate.parse(parts[4].trim(), DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                    .services(Arrays.stream(parts[8].split(",\\s*")).map(String::trim)
                                    .map(serviceName -> Services.findService(serviceName)).collect(Collectors.toList()))
                    .build();
        } catch (HotelException e) {
            throw new RuntimeException(e);
        }
    }

    public void getInfoAboutClient(int idClient){
        Client client = hotel.getClientMap().get(idClient);
        Room room = hotel.getRoomMap().get(client.getIdRoom());
        System.out.printf(client.toString() + " проживает в комнате %d типа %s категории %s и по цене %d рублей\n", room.getId(),
                room.getType(), room.getCategory(), room.getPrice());

    }

    public List<Client> sortClient(FilterClient filter){
        return hotel.getClientMap().values().stream()
                .sorted(filter.getComparator())
                .collect(Collectors.toList());
    }

    public List<Client> requestListClient(FilterClient filter){
        for (Client client : sortClient(filter)){
            String formatted = String.format("Клиент %s живет в комнате %d и его дата проживания в номере: %tF - %tF", client.toString(), client.getIdRoom(), client.getDepartureDate(), client.getCheckOutDate());
            System.out.println(formatted);
        }
        return sortClient(filter);
    }

    public void requestLastThreeClient(){
        System.out.printf("%s сделал запрос на список последних трех человек:\n", employee.getPosition());
        List<Client> clientList = sortClient(FilterClient.ID);
        for (int i = clientList.size() - 1; (clientList.size() - 3 <= 0 ? i <= 0 : i >= clientList.size() - 3); i--){
            System.out.println(clientList.get(i).toString());
        }
    }

    public void requestListServicesClient(FilterServices filter, int idClient){
        if(hotel.getClientMap().get(idClient) == null){
            System.out.println("Такого клиента не существует!");
            return;
        }
        System.out.printf("%s сделал запрос на список услуг %s :\n", employee.getPosition(), hotel.getClientMap().get(idClient).toString());
        for (Services service : hotel.getClientMap().get(idClient).getServicesList()){
            System.out.println(service.toString());
        }
    }
}
