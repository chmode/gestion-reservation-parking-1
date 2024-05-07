package ma.emsiprojet.parkingmanagment.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "parking")
public class Parking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_parking")
    private Long idParking;

    @Column(name = "type", length = 50)
    private String type;

    @Column(name = "place_nbr")
    private Integer placeNumber;

    public Long getIdParking() {
        return idParking;
    }

    public String getType() {
        return type;
    }

    public Integer getPlaceNumber() {
        return placeNumber;
    }

    public void setIdParking(Long idParking) {
        this.idParking = idParking;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPlaceNumber(Integer placeNumber) {
        this.placeNumber = placeNumber;
    }
}
