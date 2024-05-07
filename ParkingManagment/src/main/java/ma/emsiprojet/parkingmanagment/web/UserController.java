package ma.emsiprojet.parkingmanagment.web;

import ma.emsiprojet.parkingmanagment.entities.Place;
import ma.emsiprojet.parkingmanagment.entities.UserAccount;
import ma.emsiprojet.parkingmanagment.repositories.UserAccountRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ma.emsiprojet.parkingmanagment.entities.User;
import ma.emsiprojet.parkingmanagment.repositories.UserRepository;
import ma.emsiprojet.parkingmanagment.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

//@RestController
@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /*@GetMapping("/usersplus")
    public List<Object[]> getAllUsersPlus() {
        return userRepository.findAllUsersPlus();
    }*/
    @GetMapping(path = "/usersplus")
    public String getAllUsersPlus(Model model) {
        List<Object[]> usersPlus = userRepository.findAllUsersPlus();
        model.addAttribute("users", usersPlus);
        return "userdetails";
    }




    @GetMapping("/users/search")
    public String getUserByEmail(@RequestParam("email") String email, Model model) {
        List<Object[]> user = userRepository.getUserByEmail(email);
        /*if (user == null) {
            return null;
        }*/
        model.addAttribute("users", user);
        return "userdetails";
    }


    @GetMapping("/user/delete/{email}")
    public String deleteUserByEmail(@PathVariable String email) {
        if (userRepository.getUserByEmail(email) != null) {
            userRepository.deleteByEmail(email);

        } else {

        }
        return "redirect:/usersplus";
    }


    @PostMapping("/user/signup")
    public String userSignup(@RequestParam String email, @RequestParam String password, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String phone ,RedirectAttributes redirectAttributes) {

        if (userAccountRepository.findByPhone(phone)) {
            redirectAttributes.addFlashAttribute("phoneError", "Un téléphone invalide existe déjà.");
            return "redirect:/user/signup";
        } else if(userAccountRepository.findByEmail(email)){
            redirectAttributes.addFlashAttribute("emailError", "Un email invalide existe déjà.");
            return "redirect:/user/signup";
        }else{
            User adduser = new User();
            adduser.setFirstName(firstName);
            adduser.setLastName(lastName);
            adduser.setPhone(phone);
            userRepository.save(adduser);

            UserAccount useraccount = new UserAccount();
            useraccount.setEmail(email);
            useraccount.setPassword(password);
            useraccount.setReservationNumber(0);
            useraccount.setIdUser(adduser);
            userAccountRepository.save(useraccount);

            return "redirect:/user/login";
        }
    }
    @GetMapping("/user/signup")
    public String userMainSignup() {
        return "usersignup";
    }



    @GetMapping("/user/login")
    public String userMainLogin() {
        return "userlogin";
    }

    @PostMapping("/user/login")
    public String userLogin(@RequestParam String email, @RequestParam String password, RedirectAttributes redirectAttributes) {
        boolean userStatus = userAccountRepository.findByEmailAndPassword(email, password);
        if (userStatus) {
            Long userId = userRepository.findIdUserByEmail(email);
            return "redirect:/user/profile/"+userId;
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Email ou mot de passe invalide.");
            return "redirect:/user/login";
        }
    }

    @GetMapping("/user/profile/{userid}")
    public String userProfile(@PathVariable Long userid, Model model) {
        return "userprofile";
    }

    @GetMapping("/user/profile/edit/{userid}")
    public String showUserEditForm(@PathVariable Long userid, Model model) {
        UserAccount fullUser = userRepository.fullUserById(userid);
        if (fullUser != null) {
            model.addAttribute("fullUser", fullUser);
            return "userprofileedit";
        } else {
            return "error";
        }
    }


    @PostMapping("/user/profile/edit/{userid}")
    public String userMainEdite(@PathVariable Long userid, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String phone, @RequestParam String email, @RequestParam String password, RedirectAttributes redirectAttributes) {
        if (userAccountRepository.findByPhoneForUpdate(phone, userid)) {
            redirectAttributes.addFlashAttribute("phoneMessage", "Le téléphone existe déjà.");
            return "redirect:/user/profile/edit/" + userid + "?phoneError";
        } else if (userAccountRepository.findByEmailForUpdate(email, userid)) {
            redirectAttributes.addFlashAttribute("emailMessage", "l'email existe déjà.");
            return "redirect:/user/profile/edit/" + userid + "?emailError";
        }
        userRepository.updateUserAccount(email, password, userid);
        userRepository.updateUser(firstName, lastName, phone, userid);
        return "redirect:/user/profile/" + userid;
    }






}
