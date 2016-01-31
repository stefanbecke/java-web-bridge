package de.trion.sample.model;

import java.util.Date;

public class Invoice
{
    private Date date;
    private String number;
    private String company;
    private boolean paid;

    public Invoice(Date date, String number, String company)
    {
        this.date = date;
        this.number = number;
        this.company = company;
    }

    public Invoice(Date date, String number, String company, boolean paid)
    {
        this.date = date;
        this.number = number;
        this.company = company;
        this.paid = paid;
    }
    
    public Invoice()
    {
    }

    public boolean isPaid()
    {
        return paid;
    }

    public void setPaid(boolean paid)
    {
        this.paid = paid;
    }

    public String getCompany()
    {
        return company;
    }

    public void setCompany(String company)
    {
        this.company = company;
    }
    
    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public String getNumber()
    {
        return number;
    }

    public void setNumber(String number)
    {
        this.number = number;
    }
    
    

}
