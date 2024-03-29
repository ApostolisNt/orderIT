package com.example.orderit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ΜΕΤΑΒΑΣΗ ΣΤΗΝ ΣΕΛΙΔΑ ΤΟΥ ΜΕΝΟΥ
        Button submit_button = findViewById(R.id.submit_button);
        submit_button.setOnClickListener(v -> startActivity(
                new Intent(this, MenuActivity.class)));

        final Spinner spinner = findViewById(R.id.tables_spinner);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        // ΕΜΦΑΝΙΖΕΙ ΤΑ ID ΜΕΣΑ ΣΤΟ SPINNER ΚΑΙ ΤΟ ID ΤΟΥ ΤΡΑΠΕΖΙΟΥ ΠΟΥ ΕΠΙΛΕΧΘΗΚΕ
        final ArrayList<String> arrayList = new ArrayList<>();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tableName = parent.getItemAtPosition(position).toString();
                Toast toast = Toast.makeText(parent.getContext(), "Table: " + tableName, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM, 0, 0);
                toast.show();
                SharedPreferences sharedPref = getSharedPreferences("shared", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("tableName", tableName);
                editor.apply();
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });

        // ΔΙΑΒΑΖΕΙ ΑΠΟ ΤΗΝ FIREBASE ΚΑΙ ΠΕΡΝΕΙ ΟΛΑ ΤΑ ID ΤΩΝ TABLES
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot table : dataSnapshot.child("tables").getChildren()) {
                    Integer value = table.child("id").getValue(Integer.class);
                    Log.d(TAG, "onDataChange: " + value.getClass() + " value: " + value);
                    arrayList.add(value.toString());
                }
                arrayAdapter.notifyDataSetChanged();
                /*HashMap value = dataSnapshot.getValue(HashMap.class);
                Log.d(TAG, "Value is: " + value);*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

}