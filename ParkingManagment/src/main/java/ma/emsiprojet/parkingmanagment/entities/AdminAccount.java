package ma.emsiprojet.parkingmanagment.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "admin_account")
public class AdminAccount extends Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_adminacc")
    private Long idAdminacc;

    @ManyToOne
    @JoinColumn(name = "id_admin", nullable = false)
    private Admin idAdmin;

    public Long getIdAdminacc() {
        return idAdminacc;
    }

    public Admin getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdminacc(Long idAdminacc) {
        this.idAdminacc = idAdminacc;
    }

    public void setIdAdmin(Admin idAdmin) {
        this.idAdmin = idAdmin;
    }
}

