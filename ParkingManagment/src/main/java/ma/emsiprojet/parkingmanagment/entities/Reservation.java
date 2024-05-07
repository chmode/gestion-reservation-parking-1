package ma.emsiprojet.parkingmanagment.entities;

import  jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reservation")
    private Long idReservation;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_place", nullable = false)
    private Place place;

    @ManyToOne
    @JoinColumn(name = "id_Parking", nullable = false)
    private Parking parking;

    @Column(name = "date_in")
    private LocalDateTime dateIn;

    @Column(name = "date_out")
    private LocalDateTime dateOut;

    @Column(name = "hour_nbr")
    private Integer hourNumber;

    public void setIdReservation(Long idReservation) {
        this.idReservation = idReservation;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public void setParking(Parking parking) {
        this.parking = parking;
    }

    public void setDateIn(LocalDateTime dateIn) {
        this.dateIn = dateIn;
    }

    public void setDateOut(LocalDateTime dateOut) {
        this.dateOut = dateOut;
    }

    public void setHourNumber(Integer hourNumber) {
        this.hourNumber = hourNumber;
    }

    public Long getIdReservation() {
        return idReservation;
    }

    public User getUser() {
        return user;
    }

    public Place getPlace() {
        return place;
    }

    public Parking getParking() {
        return parking;
    }

    public LocalDateTime getDateIn() {
        return dateIn;
    }

    public LocalDateTime getDateOut() {
        return dateOut;
    }

    public Integer getHourNumber() {
        return hourNumber;
    }
}
