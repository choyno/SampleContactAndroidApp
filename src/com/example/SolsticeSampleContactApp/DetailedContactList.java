package com.example.SolsticeSampleContactApp;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by neel on 2/22/14.
 */
public class DetailedContactList extends Activity {

    ImageView photo,favorite;
    TextView name,company,address1,address2,home_phone,work_phone,mobile_phone,birthday,email;
    String url;
    Contact person = null;
    DetailedContact detailedContact = null;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_contact_item);
        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            person = (Contact)extras.get("person");
        }

        url = person.getDetailURL();

        new getDetails().execute();

    }

    private class getDetails extends AsyncTask <Void,Void,Void> {


        @Override
        protected void onPreExecute(){
            retrieveViews();

            company.setText(person.getCompany());

            //set phone numbers
            home_phone.setText("Home #: "+ person.getPhoneHome());
            work_phone.setText("Work #: "+person.getPhoneWork());
            mobile_phone.setText("Mobile #: "+person.getPhoneMobile());

            //set birthday
            int time = Integer.parseInt(person.getBirthDate());
            Timestamp stamp = new Timestamp(time);
            Date date = new Date(stamp.getTime());
            SimpleDateFormat bday_formatter = new SimpleDateFormat("MMMM dd, yyyy");

            birthday.setText(bday_formatter.format(date));

        }

        @Override
        protected Void doInBackground(Void... params) {

            //send request and get back detailed contact object
            JSONObject obj = TestRestClient.getDetailedContact(url);

            try {
                detailedContact = DetailedContact.detailedContactFromJSON(obj);
            } catch (JSONException e) {

            }
            return null;
        }


        @Override
        protected void onPostExecute(Void response) {

            //link to website
            name.setText(person.getName());
            String linkText ="<a href='"+detailedContact.getWebsite()+"'>"+person.getName()+"</a>";
            name.setText(Html.fromHtml(linkText));
            name.setMovementMethod(LinkMovementMethod.getInstance());


            Picasso.with(getApplicationContext())
                    .load(detailedContact.getLargeImageURL())
                    .into(photo);


            //load picture if person is a favorite
            if(detailedContact.getFavorite())
            {
                Picasso.with(getApplicationContext())
                        .load(R.drawable.favorite_icon)
                        .into(favorite);
            }

            HashMap<String,String> address = detailedContact.getAddress();
            String street = address.get("street");
            String city = address.get("city");
            String state = address.get("state");
            String country = address.get("country");
            String zip = address.get("zip");
            String lat = address.get("latitude");
            String ln = address.get("longitude");

            //PARSING PROBLEMS
            //Double latitude = Double.parseDouble(lat);
            //Double longitude = Double.parseDouble(ln);

            address1.setText(street);
            address2.setText(city+ ", " + state.toUpperCase() + " " + zip + " " + country);
            email.setText(detailedContact.getEmail());

        }
    }


    public void retrieveViews(){
        photo = (ImageView)findViewById(R.id.photo);
        favorite = (ImageView)findViewById(R.id.favorite);
        name = (TextView)findViewById(R.id.name_text);
        company = (TextView)findViewById(R.id.company_text);
        address1 = (TextView)findViewById(R.id.address_info1);
        address2 = (TextView)findViewById(R.id.address_info2);
        home_phone = (TextView)findViewById(R.id.home_phone);
        work_phone = (TextView)findViewById(R.id.work_phone);
        mobile_phone = (TextView)findViewById(R.id.mobile_phone);
        birthday = (TextView)findViewById(R.id.birthday);
        email = (TextView)findViewById(R.id.email);
    }


}