package com.example.spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public
class MainActivity extends AppCompatActivity {
    ArrayList<String> listSpinnerState=new ArrayList<String>();
    ArrayList<String> listSpinnerDistrict=new ArrayList<String>();

    ArrayList<String> listState=new ArrayList<String>();

    ArrayList<String> listDistrict=new ArrayList<String>();
    
    AutoCompleteTextView act;



/** Spinner spinner;
 EditText etState;
 String state ="";
 DatabaseReference databaseReference;
 ValueEventListener listener;
 ArrayAdapter<String> adapter;
 ArrayList<String> spinnerList;**/


    @Override
    protected
    void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        states_and_districts();
    }

    private
    void states_and_districts() {
        obj_list();
        addToSpinnerStates();
        addToSpinnerDistricts();
        addDistrict();
        addState();
    }

    private
    void addToSpinnerDistricts() {
        Spinner spinner=(Spinner)findViewById(R.id.spDistrict);
        // Adapter for spinner
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,listSpinnerDistrict);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private
    void obj_list() {
        try
        {
            // Convert the string returned to a JSON object
            JSONObject jsonObject=new JSONObject(getJson());
            // Get Json array
            JSONArray array=jsonObject.getJSONArray("states");
            // Navigate through an array item one by one
            for(int i=0;i<array.length();i++)
            {
                // select the particular JSON data
                JSONObject object=array.getJSONObject(i);
                String district=object.getString("districts");
                String state=object.getString("state");
                // add to the lists in the specified format

                listSpinnerState.add(String.valueOf(i+1)+" :  "+state);
                listSpinnerState.add(String.valueOf(i+1)+" :  "+district);


                listDistrict.add(district);
                listState.add(state);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

    }

    private
    void addDistrict() {

        act=(AutoCompleteTextView)findViewById(R.id.actDistrict);
        adapterSetting(listDistrict);
    }

    private
    void adapterSetting(ArrayList<String> arrayList) {
         
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,arrayList);
        act.setAdapter(adapter);
        hideKeyBoard();
    }

    private
    void hideKeyBoard() {
        act.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            }
        });
    }

    void addState()
    {
        Set<String> set = new HashSet<String>(listState);
        act=(AutoCompleteTextView)findViewById(R.id.actState);
        adapterSetting(new ArrayList(set));
    }

    private
    void addToSpinnerStates() {
        Spinner spinner=(Spinner)findViewById(R.id.spState);
        // Adapter for spinner
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,listSpinnerState);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public String getJson()
    {
        String json=null;
        try
        {
            // Opening cities.json file
            InputStream is = getAssets().open("states-and-districts.json");
            // is there any content in the file
            int size = is.available();
            byte[] buffer = new byte[size];
            // read values in the byte array
            is.read(buffer);
            // close the stream --- very important
            is.close();
            // convert byte to string
            json = new String(buffer, "UTF-8");
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            return json;
        }
        return json;
    }
        /**  spinner = findViewById(R.id.spinner);
          etState = findViewById(R.id.etState);
          databaseReference = FirebaseDatabase.getInstance().getReference("spinner data");


          spinnerList = new ArrayList<>();

          adapter = new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_spinner_dropdown_item,spinnerList);
          spinner.setAdapter(adapter);
          selectFromFirebase();**/

}

   /** public
    void btnAddState(View view) {

        state = etState.getText().toString().trim();
        databaseReference.push().setValue(state).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public
            void onComplete(@NonNull Task<Void> task) {
                etState.setText("");
                spinnerList.clear();
                selectFromFirebase();
                adapter.notifyDataSetChanged();
            }
        });**/
   /* public void selectFromFirebase(){
        listener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public
            void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot item :snapshot.getChildren()){
                    spinnerList.add(item.getValue().toString());

                }
                    adapter.notifyDataSetChanged();
            }

            @Override
            public
            void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }**/
