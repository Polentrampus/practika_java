package hotel.personal.employee.reception;

import hotel.personal.client.Client;

public interface ReceptionOperations {
    void cleaningRequest(int roomId);
    void repairRequest(int roomId);
}
