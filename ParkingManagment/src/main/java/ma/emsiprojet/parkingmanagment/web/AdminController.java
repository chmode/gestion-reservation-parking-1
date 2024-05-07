package ma.emsiprojet.parkingmanagment.web;

import ma.emsiprojet.parkingmanagment.repositories.AdminRepository;
import ma.emsiprojet.parkingmanagment.repositories.AdminAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AdminAccountRepository adminAccountRepository;

    @GetMapping("/admin/welcome")
    public String welcomPage() {
        return "welcomeadmin";
    }

    @GetMapping("/admin/login")
    public String adminMainLogin() {
        return "adminlogin";
    }



    @PostMapping("/admin/login")
    public String adminLogin(@RequestParam String email, @RequestParam String password, RedirectAttributes redirectAttributes) {
        boolean adminStatus = adminAccountRepository.findByEmailAndPassword(email, password);
        if (adminStatus) {
            return "redirect:/admin/welcome";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Email ou mot de passe invalide. Veuillez réessayer.");
            return "redirect:/admin/login";
        }
    }




}
