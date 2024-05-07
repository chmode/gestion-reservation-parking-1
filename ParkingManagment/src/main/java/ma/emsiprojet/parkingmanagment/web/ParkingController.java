package ma.emsiprojet.parkingmanagment.web;

import ma.emsiprojet.parkingmanagment.entities.Parking;
import ma.emsiprojet.parkingmanagment.repositories.ParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ParkingController {

    @Autowired
    private ParkingRepository parkingRepo;

    @Autowired
    private PlaceController placeController;

    @GetMapping(path = "/parkings")
    public String getAllParking(@RequestParam(name = "idparking", required = false) Long selectedParkingId, Model model) {
        List<Parking> allParking = parkingRepo.findAllParking();
        model.addAttribute("parkings", allParking);
        //if (!allParking.isEmpty()) {
            placeController.fetchPlacesForParking(selectedParkingId, model);
        //}
        return "parkingdetails";
    }
}
