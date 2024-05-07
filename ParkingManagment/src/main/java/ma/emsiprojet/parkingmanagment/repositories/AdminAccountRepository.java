package ma.emsiprojet.parkingmanagment.repositories;

import ma.emsiprojet.parkingmanagment.entities.AdminAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdminAccountRepository extends JpaRepository<AdminAccount, Long> {

    @Query("select count(ac) > 0 from AdminAccount ac where ac.email = :email and ac.password = :password")
    boolean findByEmailAndPassword(String email, String password);

}