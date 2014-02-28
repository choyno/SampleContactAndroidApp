package com.example.SolsticeSampleContactApp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by neel on 2/23/14.
 */
public class DetailedContact {

    private int employeeId;
    private boolean favorite;
    private String largeImageURL;
    private String email;
    private String website;
    private HashMap<String,String> address;

    public DetailedContact(int employeeId, boolean favorite, String largeImageURL, String email, String website, String street, String city, String state, String country, String zip, String latitude, String longitude){
        this.employeeId = employeeId;
        this.favorite = favorite;
        this.largeImageURL = largeImageURL;
        this.email = email;
        this.website = website;

        this.address = new HashMap<String, String>();
        this.address.put("street",street);
        this.address.put("city",city);
        this.address.put("state",state);
        this.address.put("country",country);
        this.address.put("zip",zip);
        this.address.put("lat",latitude);
        this.address.put("long",longitude);
    }

    public DetailedContact(){
        this.address = new HashMap<String, String>();
    }


    public String getLargeImageURL(){
        return this.largeImageURL;
    }

    public String getEmail(){
        return this.email;
    }

    public String getWebsite(){
        return this.website;
    }

    public boolean getFavorite(){
        return this.favorite;
    }

    public int getEmployeeId(){
        return this.employeeId;
    }

    public HashMap<String,String> getAddress(){
        return this.address;
    }

    public static DetailedContact detailedContactFromJSON(JSONObject userJSON) throws JSONException {

        DetailedContact dC = new DetailedContact();

        dC.employeeId = userJSON.getInt("employeeId");

        boolean favorite = false;

        if(userJSON.get("favorite") instanceof Integer)
        {
            int f = userJSON.getInt("favorite");
            if(f == 1)
                favorite = true;

        }
        else if(userJSON.get("favorite") instanceof Boolean)
        {
            favorite = userJSON.getBoolean("favorite");
        }

        dC.favorite = favorite;
        dC.largeImageURL = userJSON.getString("largeImageURL");
        dC.email = userJSON.getString("email");
        dC.website = userJSON.getString("website");

        JSONObject address = userJSON.getJSONObject("address");

        String street = address.getString("street");
        String city = address.getString("city");
        String state = address.getString("state");
        String country = address.getString("country");
        String zip = address.getString("zip");
        Double latitude = address.getDouble("latitude");
        Double longitude = address.getDouble("longitude");

        dC.address.put("street",street);
        dC.address.put("city",city);
        dC.address.put("state",state);
        dC.address.put("country",country);
        dC.address.put("zip",zip);
        dC.address.put("lat",latitude.toString());
        dC.address.put("long",longitude.toString());

        return dC;
    }

}
