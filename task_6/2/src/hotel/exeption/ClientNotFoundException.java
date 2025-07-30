package hotel.exeption;

public class ClientNotFoundException extends HotelException{
    public ClientNotFoundException(int clientId) {
        super("Клиент с ID " + clientId + " не найден");
    }
}
