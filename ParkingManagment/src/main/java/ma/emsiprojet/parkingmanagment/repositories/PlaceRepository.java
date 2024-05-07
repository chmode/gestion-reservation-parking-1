package ma.emsiprojet.parkingmanagment.repositories;

import jakarta.transaction.Transactional;
import ma.emsiprojet.parkingmanagment.entities.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Long> {


    @Query("select pl from Place pl where pl.parking.idParking = :idparking")
    List<Place> findAllplaces(@Param("idparking") Long idparking);

    @Transactional
    @Modifying
    void deleteByIdPlace(Long idplace);

    @Query("select p.parking.idParking from Place p where p.idPlace = :idplace")
    Long findParkingByPlaceId(@Param("idplace") Long idplace);

    @Transactional
    @Modifying
    @Query("UPDATE Place p SET p.name = :name, p.price = :price, p.state = :state WHERE p.idPlace = :idplace")
    void updatePlace(@Param("idplace") Long id, @Param("name") String name, @Param("price") double price, @Param("state") String state);

    @Query("SELECT COUNT(p) > 0 FROM Place p WHERE p.name = :name AND p.parking.idParking = :parkingId")
    boolean existsByNameAndParkingId(String name, Long parkingId);

    @Query("SELECT COUNT(p) > 0 FROM Place p WHERE p.name = :name AND p.parking.idParking = :parkingId AND p.idPlace != :currentPlaceId")
    boolean existsByNameAndParkingIdAndNotCurrentPlaceId(String name, Long parkingId, Long currentPlaceId);

    @Query("select p from Place p where  p.parking.idParking = :parkingid")
    List<Place> findValidPlaces(Long parkingid);


}
