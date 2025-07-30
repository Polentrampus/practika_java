package hotel.model.filter;

import hotel.personal.client.Client;

import java.util.Comparator;

public enum FilterClient {
    SURNAME(Comparator.comparing(Client::getSurname)),
    ID(Comparator.comparing(Client::getId)),
    IDROOM(Comparator.comparing(Client::getIdRoom)),
    DATECHECKUP(Comparator.comparing(Client::getCheckOutDate));


    private final Comparator<Client> comparator;

    FilterClient(Comparator<Client> comparator) {
        this.comparator = comparator;
    }

    public Comparator<Client> getComparator() {
        return comparator;
    }
}
