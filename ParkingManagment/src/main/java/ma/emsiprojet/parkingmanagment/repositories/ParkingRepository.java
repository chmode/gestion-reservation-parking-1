package ma.emsiprojet.parkingmanagment.repositories;

import ma.emsiprojet.parkingmanagment.entities.Parking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ParkingRepository extends JpaRepository<Parking, Long> {

    @Query("SELECT p from Parking p ")
    List<Parking> findAllParking();

}
