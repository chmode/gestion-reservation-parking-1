package ma.emsiprojet.parkingmanagment.repositories;

import jakarta.transaction.Transactional;
import ma.emsiprojet.parkingmanagment.entities.User;
import ma.emsiprojet.parkingmanagment.entities.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u.idUser, u.firstName, u.lastName, u.phone, ua.email, ua.reservationNumber FROM User u JOIN UserAccount ua ON u.idUser = ua.idUser.idUser")
    List<Object[]> findAllUsersPlus();

    @Query("SELECT u.idUser, u.firstName, u.lastName, u.phone, ua.email, ua.reservationNumber " +
            "FROM User u " +
            "JOIN UserAccount ua ON u.idUser = ua.idUser.idUser " +
            "WHERE ua.email = :email")
    List<Object[]> getUserByEmail(@Param("email")String email);

    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.idUser IN (SELECT ua.idUser.idUser FROM UserAccount ua WHERE ua.email = :email)")
    void deleteByEmail(@Param("email") String email);

    @Query("select u .idUser from User u join UserAccount ua on u.idUser = ua.idUser.idUser where ua.email = :email ")
    Long findIdUserByEmail(String email);

    /*@Query("select '*' from User u join UserAccount ua on u.idUser = ua.idUser.idUser where u.idUser = :userId")
    Object fullUserById(Long userId);*/

    @Query("SELECT ua FROM UserAccount ua WHERE ua.idUser.idUser = :userId")
    UserAccount fullUserById(Long userId);



    @Transactional
    @Modifying
    @Query("update UserAccount ua set ua.email = :email, ua.password = :password where ua.idUser.idUser = :userId")
    void updateUserAccount(@Param("email") String email, @Param("password") String password, @Param("userId") Long userId);

    @Transactional
    @Modifying
    @Query("update User u set u.firstName = :firstName, u.lastName = :lastName, u.phone = :phone where u.idUser = :userId")
    void updateUser(@Param("firstName") String firstName, @Param("lastName") String lastName, @Param("phone") String phone, @Param("userId") Long userId);






}

