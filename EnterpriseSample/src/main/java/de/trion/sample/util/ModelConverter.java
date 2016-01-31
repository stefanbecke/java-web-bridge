package de.trion.sample.util;

import de.trion.sample.model.Contract;
import netscape.javascript.JSObject;

public class ModelConverter
{

    public static Contract contractFrom (JSObject contract)
    {
        Contract converted = new Contract();
        
        converted.setName((String)contract.getMember("name"));
        converted.setCompany((String)contract.getMember("company"));
        converted.setEnabled((Boolean) contract.getMember("active"));
        
        return converted;
    }
}
