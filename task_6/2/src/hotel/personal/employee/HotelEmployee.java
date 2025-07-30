package hotel.personal.employee;

import hotel.model.Hotel;
import hotel.personal.Person;
import hotel.personal.employee.admin.Admin;
import hotel.personal.employee.reception.Reception;
import hotel.personal.employee.service.Maid;
import hotel.personal.employee.service.Mender;

public abstract class HotelEmployee extends Person {
    protected final Hotel hotel = Hotel.getInstance();
    protected final String position;

    public HotelEmployee(HotelEmployeeBuilder builder) {
        super(builder);
        this.position = builder.position;
    }
    public abstract String getPosition();


    public static class HotelEmployeeBuilder extends Builder<HotelEmployeeBuilder>{
        private String position;

        public HotelEmployeeBuilder position(String position){
            this.position = position;
            return self();
        }

        @Override
        protected HotelEmployeeBuilder self() {
            return this;
        }

        @Override
        public HotelEmployee build() {
            if (position == null) {
                throw new IllegalStateException("Поле position не задано!");
            }

            switch (position.trim().toLowerCase()) {
                case "admin":
                    return new Admin(this);
                case "reception":
                    return new Reception(this);
                case "maid":
                    return new Maid(this);
                case "mender":
                    return new Mender(this);
                default:
                    throw new IllegalArgumentException("Неизвестная должность: " + position);
            }
        }
    }
}