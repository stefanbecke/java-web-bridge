package de.trion.sample.controller;

import de.trion.sample.model.Invoice;
import de.trion.sample.service.InvoiceService;
import de.trion.sample.swing.forms.InvoiceForm;

public class InvoiceController
{
    private InvoiceForm listener;
    private InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService)
    {
        this.invoiceService = invoiceService;
    }
    
    public void registerChangeListener(InvoiceForm listener)
    {
        this.listener = listener;
    }
    
    public void markPaid(Invoice invoice)
    {
        invoiceService.markPaid(invoice);
        listener.updateModel();
    }

}
