package de.trion.sample.service;

import de.trion.sample.model.Invoice;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InvoiceService
{
    private List<Invoice> invoices = new ArrayList<>();

    public InvoiceService()
    {
        invoices.add(new Invoice(new Date(), "48234", "ACME Corp"));
        invoices.add(new Invoice(new Date(), "14547", "Wonder inc"));
    }
    
    public List<Invoice> getInvoices()
    {
        return invoices;
    }

    public void markPaid(Invoice invoice)
    {
        invoice.setPaid(true);
    }

}
