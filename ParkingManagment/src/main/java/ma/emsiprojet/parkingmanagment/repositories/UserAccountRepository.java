package ma.emsiprojet.parkingmanagment.repositories;

import ma.emsiprojet.parkingmanagment.entities.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    @Query("select count(us) > 0 from UserAccount us where us.email = :email and us.password = :password")
    boolean findByEmailAndPassword(String email, String password);

    @Query("select  count(us) > 0 from UserAccount us where us.email = :email")
    boolean findByEmail(String email);

    @Query("select  count(u) > 0 from User u where u.phone = :phone")
    boolean findByPhone( String phone);

    @Query("select  count(u) > 0 from User u where u.phone = :phone and u.idUser != :currentuserid ")
    boolean findByPhoneForUpdate(String phone, Long currentuserid);

    @Query("select  count(ua) > 0 from UserAccount ua where ua.email = :email and ua.idUser.idUser != :currentuserid")
    boolean findByEmailForUpdate(String email, Long currentuserid);



    //void addUser(String email, String password, String phone, String firstname, String lastname);


}
