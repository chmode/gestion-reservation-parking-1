package ma.emsiprojet.parkingmanagment.web;
public class ReservationDetailsHelper {
    private Object reservation;
    private int hours;
    private int minutes;

    public ReservationDetailsHelper(Object reservation, int hours, int minutes) {
        this.reservation = reservation;
        this.hours = hours;
        this.minutes = minutes;
    }

    public int getHours() {
        return hours;
    }

    public Object getReservation() {
        return reservation;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setReservation(Object reservation) {
        this.reservation = reservation;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }
}
