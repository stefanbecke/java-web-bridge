package de.trion.sample.controller;

import de.trion.sample.model.Contract;
import de.trion.sample.service.ContractService;
import de.trion.sample.swing.forms.ContractForm;

public class ContractController
{
    private ContractService contractService;
    private ContractForm listener;

    public ContractController(ContractService contractService)
    {
        this.contractService = contractService;
    }
    
    public void registerChangeListener(ContractForm listener)
    {
        this.listener = listener;
    }
    
    public void enableContract(Contract contract)
    {
        contractService.enableContract(contract);
        listener.modelUpdate();
    }
    
    public void disableContract(Contract contract)
    {
        contractService.disableContract(contract);
        listener.modelUpdate();
    }
}
