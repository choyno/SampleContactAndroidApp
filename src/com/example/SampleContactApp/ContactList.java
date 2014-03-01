package com.example.SampleContactApp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.SolsticeSampleContactApp.R;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ContactList extends Activity{
    /**
     * Called when the activity is first created.
     */

    ListView contactView;
    ArrayList<Contact> contactList = new ArrayList<Contact>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_list);


        contactView = (ListView)findViewById(R.id.contact_list);

        //load contact info
        requestContactInfo();

    }


    public void requestContactInfo(){

        //IN ORDER TO GET JSON
        FakeTrustManager.allowAllSSL();

        TestRestClient.get(TestRestClient.BASIC_URL, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONArray contacts) {

                // Pull out the first event on the public timeline
                JSONObject person = null;

                for (int i = 0; i < contacts.length(); i++) {

                    try {
                        person = (JSONObject) contacts.get(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //info we need to retrieve for each contact
                    if (person != null) {

                        try {

                            String name = person.getString("name");
                            int employeeId = person.getInt("employeeId");
                            String company = person.getString("company");
                            ;
                            String detailURL = person.getString("detailsURL");
                            String smallImageURl = person.getString("smallImageURL");
                            ;
                            String birthDate = person.getString("birthdate");
                            JSONObject phone = person.getJSONObject("phone");
                            String workPhone = null;
                            String homePhone = null;
                            String mobilePhone = null;

                            //Catch exception because they do not all exist
                            try {
                                workPhone = phone.getString("work");
                            } catch (JSONException f) {

                            }

                            try {
                                homePhone = phone.getString("home");
                            } catch (JSONException f) {

                            }

                            try {
                                mobilePhone = phone.getString("mobile");
                            } catch (JSONException f) {

                            }

                            //create contact from json info
                            Contact created = new Contact(name, employeeId, company, detailURL, smallImageURl, birthDate, workPhone, homePhone, mobilePhone);

                            contactList.add(created);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                }

                //all contacts have been retrieved and now we set up listview
                contactView.setAdapter(new ContactListAdapter(contactList));

            }
        });


        contactView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Contact person = contactList.get(position);

                //check to make sure we don't crash because of internet
                if(isConnectingToInternet())
                {
                    Intent intent = new Intent(getApplicationContext(),DetailedContactList.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("person",person);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"No Internet Connection!",Toast.LENGTH_LONG).show();
                }
            }
        });


    }



    public boolean isConnectingToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
    }



    /// ADAPTER FOR CUSTOM CONTACT LIST VIEW ITEMS

    private class ContactListAdapter extends BaseAdapter {
        ArrayList<Contact> contactList = null;

        public ContactListAdapter(ArrayList<Contact> contactList) {
            super();
            this.contactList = contactList;
        }


        @Override
        public int getCount() {
            if(contactList != null)
                return contactList.size();
            else
                return 0;
        }

        @Override
        public Contact getItem(int i) {
            return contactList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View contactView = View.inflate(getApplicationContext(), R.layout.contact_list_item, null);

            TextView name = (TextView) contactView.findViewById(R.id.name);
            TextView phone_number = (TextView)contactView.findViewById(R.id.phone_number);
            ImageView background = (ImageView)contactView.findViewById(R.id.icon);

            Contact info = contactList.get(i);

            name.setText(info.getName());
            phone_number.setText("#: "+info.getPhoneWork());
            String url = info.getSmallImageURl();

            //load image with Picasso
            Picasso.with(getApplicationContext()).load(url).into(background);

            return contactView;
        }
    }



}



