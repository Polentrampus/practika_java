package hotel.exeption;

public class RoomNotAvailableException extends HotelException {
    public RoomNotAvailableException(int roomId) {
        super("Комната " + roomId + " не доступна для заселения");
    }
}
