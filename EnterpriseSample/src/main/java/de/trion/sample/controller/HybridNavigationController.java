package de.trion.sample.controller;

import de.trion.sample.swing.web.WebForm;

public class HybridNavigationController extends NavigationController
{
    private WebForm webForm;

    public HybridNavigationController()
    {
        webForm = new WebForm(contractService, contractController, this);
    }

    @Override
    public void showContracts()
    {
        webForm.navigate("contracts");
        ui.content(webForm);
    }

}
