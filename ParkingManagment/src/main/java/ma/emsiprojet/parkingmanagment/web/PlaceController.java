package ma.emsiprojet.parkingmanagment.web;

import ma.emsiprojet.parkingmanagment.entities.Parking;
import ma.emsiprojet.parkingmanagment.entities.Place;
import ma.emsiprojet.parkingmanagment.repositories.ParkingRepository;
import ma.emsiprojet.parkingmanagment.repositories.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class PlaceController {

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private ParkingRepository parkingRepository;


    public void fetchPlacesForParking(Long parkingId, Model model) {
        List<Place> allPlaces = placeRepository.findAllplaces(parkingId);
        model.addAttribute("places", allPlaces);
    }

    @GetMapping("/places/delete/{idplace}")
    public String deletePlace(@PathVariable Long idplace) {
        Long parkingId = placeRepository.findParkingByPlaceId(idplace);
        placeRepository.deleteByIdPlace(idplace);
        return "redirect:/parkings?idparking="+parkingId;
    }



    @GetMapping("/places/update/{idplace}")
    public String showUpdateForm(@PathVariable Long idplace, Model model) {
        Optional<Place> placeOptional = placeRepository.findById(idplace);
        if (placeOptional.isPresent()) {
            model.addAttribute("place", placeOptional.get());
            return "placeupdate";
        } else {
            return "error";
        }
    }
    @PostMapping("/places/updated/{idplace}")
    public String updatePlace(@PathVariable Long idplace, @RequestParam String name, @RequestParam double price, @RequestParam String state, RedirectAttributes redirectAttributes) {
        Long parkingId = placeRepository.findParkingByPlaceId(idplace);
        boolean placeExists = placeRepository.existsByNameAndParkingIdAndNotCurrentPlaceId(name, parkingId, idplace);
        if(placeExists) {
            redirectAttributes.addFlashAttribute("errorMessage", "Une place du même nom existe déjà dans le parking sélectionné.");
            return "redirect:/places/update/" + idplace + "?error";
        } else {
            placeRepository.updatePlace(idplace, name, price, state);
            return "redirect:/parkings?idparking=" + parkingId;
        }
    }


    @GetMapping("/places/add")
    public String showAddPlaceForm(Model model) {
        List<Parking> parkings = parkingRepository.findAll();
        model.addAttribute("parkings", parkings);
        return "addplaceform";
    }

    @PostMapping("/places/add")
    public String addPlace(@RequestParam Long parkingId, @RequestParam String name, @RequestParam Float price,
                           @RequestParam String state, RedirectAttributes redirectAttributes) {
        boolean placeExists = placeRepository.existsByNameAndParkingId(name, parkingId);
        if (placeExists) {
            redirectAttributes.addFlashAttribute("errorMessage", "Une place du même nom existe déjà dans le parking sélectionné.");
            return "redirect:/places/add";
        } else {
            Place newPlace = new Place();
            newPlace.setName(name);
            newPlace.setPrice(price);
            newPlace.setState(state);

            Parking parking = new Parking();
            parking.setIdParking(parkingId);

            newPlace.setParking(parking);
            placeRepository.save(newPlace);
            return "redirect:/parkings?idparking=" + parkingId;
        }
    }

}
