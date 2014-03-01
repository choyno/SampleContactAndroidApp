package com.example.SampleContactApp;

import java.io.Serializable;

/**
 * Created by neel on 2/22/14.
 */
public class Contact implements Serializable {

    private String name;
    private int employeeId;
    private String company;
    private String detailURL;
    private String smallImageURl;
    private String birthDate;
    private String phoneWork;
    private String phoneHome;
    private String phoneMobile;

    private DetailedContact detailed;

    public Contact(String name, int employeeId, String company, String detailURL, String smallImageURl, String birthDate,
                   String phoneWork, String phoneHome, String phoneMobile)
    {
        this.name = name;
        this.employeeId = employeeId;
        this.company = company;
        this.detailURL = detailURL;
        this.smallImageURl = smallImageURl;
        this.birthDate = birthDate;

        //because not all contacts have numbers for all these categories
        if(phoneWork != null)
            this.phoneWork = phoneWork;
        else
            this.phoneWork = " ";

        if(phoneHome != null)
            this.phoneHome = phoneMobile;
        else
            this.phoneHome = " ";

        if(phoneMobile != null)
            this.phoneMobile = phoneMobile;
        else
            this.phoneMobile = " ";

        this.detailed = null;

    }

    //USEFUL GETTER METHODS
    public String getName(){
        return this.name;
    }

    public String getPhoneWork(){
        return this.phoneWork;
    }

    public String getDetailURL(){
        return this.detailURL;
    }

    public String getSmallImageURl(){
        return this.smallImageURl;
    }

    public String getCompany(){
        return this.company;
    }

    public String getBirthDate(){
        return this.birthDate;
    }

    public String getPhoneHome(){
        return this.phoneHome;
    }

    public String getPhoneMobile(){
        return this.phoneMobile;
    }
}
