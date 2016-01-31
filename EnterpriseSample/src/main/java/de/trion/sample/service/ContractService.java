package de.trion.sample.service;

import de.trion.sample.model.Contract;
import java.util.ArrayList;
import java.util.List;

public class ContractService
{
    private List<Contract> contracts = new ArrayList<>();

    public ContractService()
    {
        contracts.add(new Contract("Marc", "ACME corp", true));
        contracts.add(new Contract("Steve", "Wonder inc", true));
        contracts.add(new Contract("Marissa", "Sun shine", false));
    }

    public List<Contract> getContracts()
    {
        return contracts;
    }
    
    public void disableContract(Contract contract)
    {
        contract.setEnabled(false);
    }
    
    public void enableContract(Contract contract)
    {
        contract.setEnabled(true);
    }

}
