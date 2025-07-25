package hotel.personal.employee;

import hotel.personal.client.Client;

public interface CommonHotelOperations {
    void settle(int idClient, int idRoom);
    void evict(int idClient);
    void cleaningRequest(int idRoom);
    void repairRequest(int idRoom);
    void addClient(Client person);
    void givOutCheck(int idClient);
}
