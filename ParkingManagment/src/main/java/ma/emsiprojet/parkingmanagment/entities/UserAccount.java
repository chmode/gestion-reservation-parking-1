package ma.emsiprojet.parkingmanagment.entities;

import  jakarta.persistence.*;

@Entity
@Table(name = "user_account")
public class UserAccount extends Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_account")
    private Long idAccount;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User idUser;

    @Column(name = "reservation_nbr")
    private Integer reservationNumber;

    public Long getIdAccount() {
        return idAccount;
    }

    public User getIdUser() {
        return idUser;
    }

    public Integer getReservationNumber() {
        return reservationNumber;
    }

    public void setIdAccount(Long idAccount) {
        this.idAccount = idAccount;
    }

    public void setIdUser(User idUser) {
        this.idUser = idUser;
    }

    public void setReservationNumber(Integer reservationNumber) {
        this.reservationNumber = reservationNumber;
    }
}
