package ma.emsiprojet.parkingmanagment.web;

import ma.emsiprojet.parkingmanagment.entities.Invoice;
import ma.emsiprojet.parkingmanagment.repositories.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class invoiceController {

    @Autowired
    private InvoiceRepository invoiceRepository;

    public void addInvoice(Invoice invoice) {
        invoiceRepository.save(invoice);
    }
}
