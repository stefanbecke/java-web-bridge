package de.trion.sample.controller;

import de.trion.sample.model.Contract;
import de.trion.sample.service.ContractService;
import de.trion.sample.service.InvoiceService;
import de.trion.sample.swing.forms.AboutForm;
import de.trion.sample.startup.LegacySwing;
import de.trion.sample.swing.forms.ContractForm;
import de.trion.sample.swing.forms.InvoiceForm;
import de.trion.sample.swing.forms.SendMailForm;

public class NavigationController
{
    LegacySwing ui;
    
    private final InvoiceController invoiceController;
    private final InvoiceService invoiceService;
    final ContractController contractController;
    final ContractService contractService;

    public NavigationController()
    {
        invoiceService = new InvoiceService();
        invoiceController = new InvoiceController(invoiceService);

        contractService = new ContractService();
        contractController = new ContractController(contractService);
    }
    

    public LegacySwing getUi()
    {
        return ui;
    }

    public void setUi(LegacySwing ui)
    {
        this.ui = ui;
    }
    
    public void showContracts()
    {
        ContractForm contractForm = new ContractForm(contractService, contractController, this);
        ui.content(contractForm);
    }
    
    public void showInvoices()
    {
        InvoiceForm invoicesForm = new InvoiceForm(invoiceService, invoiceController);
        ui.content(invoicesForm);
    }
    
    public void showAbout()
    {
        AboutForm aboutForm = new AboutForm();
        
        ui.content(aboutForm);
    }

    public void sendMail(Contract contract)
    {
        SendMailForm sendMailForm = new SendMailForm(contract, this);
        sendMailForm.pack();
        sendMailForm.setVisible(true);
    }
    
    public void statusUpdate(String status)
    {
        ui.statusUpdate(status);
    }
}
