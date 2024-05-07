package ma.emsiprojet.parkingmanagment.web;

import ma.emsiprojet.parkingmanagment.entities.*;
import ma.emsiprojet.parkingmanagment.repositories.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
public class ReservationController {


        @Autowired
        private ReservationRepository reservationRepository;

        @Autowired
        private ParkingRepository parkingRepository;

        @Autowired
        private PlaceRepository placeRepository;

        @Autowired
        private InvoiceRepository invoiceRepository;

        @Autowired
        private UserAccountRepository userAccountRepository;

    @GetMapping("/user/profile/history/{userid}")
    /*public String showReservationDetails(@PathVariable Long userid, Model model) {
        //List<Reservation> reservations = reservationRepository.findByUserId(userid);
        Object reservations = reservationRepository.findReservationDetailsWithInvoice();
        model.addAttribute("reservations", reservations);
        return "reservationhestory";
    }*/
    public String showReservationDetails(@PathVariable Long userid, Model model) {
        List<ReservationDetailsHelper> reservations = new ArrayList<ReservationDetailsHelper>();
        List<Object[]> reservationObjects = reservationRepository.findReservationDetailsWithInvoice(userid);
        for (Object[] res : reservationObjects) {
            LocalDateTime arrivalDateTime = (LocalDateTime) res[0];
            LocalDateTime departureDateTime = (LocalDateTime) res[1];
            Duration duration = Duration.between(arrivalDateTime, departureDateTime);
            int hours = (int)duration.toHours();
            int minutes = duration.toMinutesPart();
            ReservationDetailsHelper reservationDetails = new ReservationDetailsHelper(res, hours, minutes);
            reservations.add(reservationDetails);
        }
        model.addAttribute("reservations", reservations);
        return "reservationhestory";
    }





    @GetMapping("/user/profile/resarvation/f/{userid}")
    public String displayReservationFirstTime(@PathVariable Long userid, Model model) {
        List<Parking> parkingOptions = parkingRepository.findAll();
        model.addAttribute("parkingOptions", parkingOptions);
        model.addAttribute("userid", userid);
        return "makereservation";
    }

    /*@GetMapping("/user/profile/resarvation/{userid}")
    public String displayReservation(@PathVariable Long userid,
                                     @PathVariable Long parkingid,
                                     Model model) {
        List<Parking> parkingOptions = parkingRepository.findAll();
        model.addAttribute("parkingOptions", parkingOptions);
        model.addAttribute("userid", userid);
        model.addAttribute("parkingid", parkingid);
        return "makereservation";
    }*/

    @GetMapping("/user/profile/reservation/s/{userid}")
    public String makeReservationPage(@PathVariable Long userid, Model model) {
        List<Parking> parkingOptions = parkingRepository.findAll();
        model.addAttribute("parkingOptions", parkingOptions);
        model.addAttribute("userid", userid);
        return "makereservation";
    }

    @PostMapping("/user/profile/reservation/s/{userid}")
    public String makeReservation(@PathVariable Long userid,
                                  @RequestParam("dateIn") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime dateIn,
                                  @RequestParam("dateOut") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime dateOut,
                                  @RequestParam("parking") Long parkingid,
                                  RedirectAttributes redirectAttributes,
                                  Model model) {

        List<Place> availablePlaces = reservationRepository.findAvailablePlaces(parkingid, dateIn, dateOut);
        model.addAttribute("availablePlaces", availablePlaces);
        model.addAttribute("userid", userid);
        model.addAttribute("dateInFromserver", dateIn);
        model.addAttribute("dateOutFromserver", dateOut);
        model.addAttribute("parking", parkingid);

        List<Parking> parkingOptions = parkingRepository.findAll();
        model.addAttribute("parkingOptions", parkingOptions);
        model.addAttribute("userid", userid);


        /*if (!availablePlaces.isEmpty()) {
            System.out.println(availablePlaces.get(0).getName());
        } else {
            System.out.println("No available places found");
        }*/
        System.out.println(dateIn);
        return "makereservation";
    }

    @PostMapping("/user/profile/reservation/validation")
    public String reservationValidation(@RequestParam("userid") Long userid,
                                        @RequestParam("parkingid") Long parkingid,
                                        @RequestParam("placeId") Long placeid,
                                        @RequestParam("placeprice") Float placeprice,
                                        @RequestParam("vdatein") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime dateIn,
                                        @RequestParam("vdateout") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime dateOut,
                                        Model model) {

        Parking parking = new Parking();
        parking.setIdParking(parkingid);

        User user = new User();
        user.setIdUser(userid);


        Place place = new Place();
        place.setIdPlace(placeid);

        Duration duration = Duration.between(dateIn, dateOut);
        int hours = (int) duration.toHours();
        int nbrminute = (int)duration.minusHours(hours).toMinutes();

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setParking(parking);
        reservation.setPlace(place);
        reservation.setDateIn(dateIn);
        reservation.setDateOut(dateOut);
        reservation.setHourNumber(hours);

        reservationRepository.save(reservation);


        reservationRepository.userReservationIncrement(userid);

        float totalPriceForHours = hours * placeprice;
        float pricePerMinute = (placeprice / 60);
        float totalPriceForMinutes = nbrminute * pricePerMinute;
        BigDecimal totalPriceBigDecimal = new BigDecimal(Float.toString(totalPriceForHours + totalPriceForMinutes));
        totalPriceBigDecimal = totalPriceBigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
        float totalPrice = totalPriceBigDecimal.floatValue();

        Invoice invoice = new Invoice();
        invoice.setReservation(reservation);
        invoice.setTotalAmount(totalPrice);

        invoiceRepository.save(invoice);

        model.addAttribute("dateInFromserver", dateIn);
        model.addAttribute("dateOutFromserver", dateOut);
        model.addAttribute("parkingName", parkingRepository.findById(parkingid).get().getType());
        model.addAttribute("placeName", placeRepository.findById(placeid).get().getName());
        model.addAttribute("nbrhours", reservation.getHourNumber());
        model.addAttribute("nbrminute", nbrminute);
        model.addAttribute("totalPrice", totalPrice);

        return "invoice";
    }




}


