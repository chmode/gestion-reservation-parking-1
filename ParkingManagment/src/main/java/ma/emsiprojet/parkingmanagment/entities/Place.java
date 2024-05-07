package ma.emsiprojet.parkingmanagment.entities;

import  jakarta.persistence.*;

@Entity
@Table(name = "place")
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_place")
    private Long idPlace;

    @ManyToOne
    @JoinColumn(name = "id_parking", nullable = false)
    private Parking parking;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "price")
    private Float price;

    @Column(name = "state", length = 50)
    private String state;

    public Long getIdPlace() {
        return idPlace;
    }

    public Parking getParking() {
        return parking;
    }

    public String getName() {
        return name;
    }

    public Float getPrice() {
        return price;
    }

    public String getState() {
        return state;
    }

    public void setIdPlace(Long idPlace) {
        this.idPlace = idPlace;
    }

    public void setParking(Parking parking) {
        this.parking = parking;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public void setState(String state) {
        this.state = state;
    }

}
