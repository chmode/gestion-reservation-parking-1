package ma.emsiprojet.parkingmanagment.entities;

import  jakarta.persistence.*;

@Entity
@Table(name = "invoice")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_invoice")
    private Long idInvoice;

    @ManyToOne
    @JoinColumn(name = "id_reservation", nullable = false)
    private Reservation reservation;

    @Column(name = "total_amount")
    private Float totalAmount;

    public Long getIdInvoice() {
        return idInvoice;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public Float getTotalAmount() {
        return totalAmount;
    }

    public void setIdInvoice(Long idInvoice) {
        this.idInvoice = idInvoice;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public void setTotalAmount(Float totalAmount) {
        this.totalAmount = totalAmount;
    }
}