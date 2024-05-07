package ma.emsiprojet.parkingmanagment;

import ma.emsiprojet.parkingmanagment.entities.Admin;
import ma.emsiprojet.parkingmanagment.entities.Person;
import ma.emsiprojet.parkingmanagment.entities.User;
import ma.emsiprojet.parkingmanagment.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ParkingManagmentApplication implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(ParkingManagmentApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        /*User user = new User();
        user.setFirstName("aaa");
        user.setLastName("bbbb");
        user.setPhone("07687668");
        userRepository.save(user);*/

        User usr = userRepository.findById(Long.valueOf(3)).get();
        System.out.println("***********"+usr.getFirstName());
    }
}


