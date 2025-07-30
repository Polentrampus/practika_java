package hotel.personal;

import hotel.exeption.HotelException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Person {
    String name;
    String surname;
    String patronymic;
    LocalDate date_of_birth;
    private final int id;

    public String getName() {
        return name;
    }

    public LocalDate getDate_of_birth() {
        return date_of_birth;
    }

    public String getSurname() {
        return surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public Person(Builder<?> builder) {
        this.name = builder.name;
        this.surname = builder.surname;
        this.patronymic = builder.patronymic;
        this.date_of_birth = builder.date_of_birth;
        this.id = builder.id;
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

    public abstract static class Builder <T extends Builder<T>> {
        private String name;
        private String surname;
        private String patronymic;
        private LocalDate date_of_birth;
        private int id;

        public T name(String name) {
            this.name = Objects.requireNonNull(name, "Обязательное поле!");
            return self();
        }

        public T surname(String surname) {
            this.surname = Objects.requireNonNull(surname, "Обязательное поле!");
            return self();
        }

        public T patronymic(String patronymic) {
            this.patronymic = Objects.requireNonNull(patronymic, "Обязательное поле!");
            return self();
        }

        public T date_of_birth(LocalDate date_of_birth) {
            this.date_of_birth = date_of_birth;;
            return self();
        }

        public T id(int id) {
            this.id = id;
            return self();
        }
        protected abstract T self();

        public abstract Person build() throws HotelException;

    }
}
