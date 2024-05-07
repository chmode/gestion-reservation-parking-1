package ma.emsiprojet.parkingmanagment.repositories;

import jakarta.transaction.Transactional;
import ma.emsiprojet.parkingmanagment.entities.Place;
import ma.emsiprojet.parkingmanagment.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("select r from Reservation r where r.user.idUser = :userid")
    List<Reservation> findByUserId(Long userid);


/*    @Query("SELECT p " +
            "FROM Place p " +
            "LEFT JOIN Reservation r ON p.idPlace = r.place.idPlace " +
            "WHERE r.idReservation IS NULL AND " +
            "      :parkingId NOT BETWEEN r.dateIn AND r.dateOut AND " +
            "      :dateIn NOT BETWEEN r.dateIn AND r.dateOut AND " +
            "      :dateOut NOT BETWEEN r.dateIn AND r.dateOut")
    List<Place> findAvailablePlaces(@Param("parkingId") Long parkingId,
                                    @Param("dateIn") Date dateIn,
                                    @Param("dateOut") Date dateOut);*/

    @Query("SELECT p FROM Place p " +
            "LEFT JOIN Reservation r ON p.idPlace = r.place.idPlace " +
            "WHERE p.parking.idParking = :parkingId " +
            "AND ((r.dateOut < :dateIn OR r.dateIn > :dateOut) " +
            "OR r.idReservation IS NULL)")
    List<Place> findAvailablePlaces(@Param("parkingId") Long parkingId,
                                    @Param("dateIn") LocalDateTime dateIn,
                                    @Param("dateOut") LocalDateTime dateOut);


    @Transactional
    @Modifying
    @Query("update UserAccount ua set ua.reservationNumber = ua.reservationNumber + 1 where ua.idUser.idUser = :userid")
    void userReservationIncrement(Long userid);

    @Query("select r.dateIn, r.dateOut, r.place, r.parking.type, i.totalAmount from Reservation r join Invoice i on i.reservation.idReservation = r.idReservation join User u on u.idUser = r.user.idUser where u.idUser = :userid")
    List<Object[]> findReservationDetailsWithInvoice(Long userid);











}