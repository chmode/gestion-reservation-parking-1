package ma.emsiprojet.parkingmanagment.repositories;

import ma.emsiprojet.parkingmanagment.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

}