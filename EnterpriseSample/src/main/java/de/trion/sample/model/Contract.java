package de.trion.sample.model;

public class Contract
{
    private String name;
    private String company;
    private boolean enabled;

    public Contract()
    {
    }

    public Contract(String name, String company, boolean enabled)
    {
        this.name = name;
        this.company = company;
        this.enabled = enabled;
    }
    
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getCompany()
    {
        return company;
    }

    public void setCompany(String company)
    {
        this.company = company;
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }
    
    

}
