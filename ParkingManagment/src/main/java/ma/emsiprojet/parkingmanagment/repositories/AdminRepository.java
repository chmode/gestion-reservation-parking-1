package ma.emsiprojet.parkingmanagment.repositories;

import ma.emsiprojet.parkingmanagment.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {

}