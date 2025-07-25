package hotel.personal;

public class Person {
    String name;
    String surname;
    String patronymic;
    int date_of_birth;
    int month_of_birth;
    int year_of_birth;
    private static int lastId = 0;
    private final int id;

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public Person(String name, String surname, String patronymic, int date_of_birth, int month_of_birth, int year_of_birth) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.date_of_birth = date_of_birth;
        this.month_of_birth = month_of_birth;
        this.year_of_birth = year_of_birth;
        this.id = lastId++;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Person{" +
                "patronymic='" + patronymic + '\'' +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
