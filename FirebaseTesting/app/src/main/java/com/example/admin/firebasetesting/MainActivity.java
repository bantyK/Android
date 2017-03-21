package com.example.admin.firebasetesting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import model.Item;

public class MainActivity extends AppCompatActivity {

    private Intent logInIntent;

    //Firebase Authentication variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    //Firebase Database variables
    private DatabaseReference mDatabase;

    private String mUserId;
    private EditText todoText;
    private Button addButton;
    private ArrayAdapter<String> adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        if (mFirebaseUser == null) {
            //User is not logged in.. load the LogIn Screen
            startLogInActivity();
        } else {
            showHomePage();
        }
    }

    private void showHomePage() {
        mUserId = mFirebaseUser.getUid();
        setUpListView();
        initUIElements();
        populateListView();
    }

    private void populateListView() {
        mDatabase.child("users").child(mUserId).child("items").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                addDataIntoList(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                removeDataFromList(dataSnapshot);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void removeDataFromList(DataSnapshot dataSnapshot) {
        adapter.remove((String) dataSnapshot.child("title").getValue());
    }

    private void addDataIntoList(DataSnapshot dataSnapshot) {
        adapter.add((String) dataSnapshot.child("title").getValue());
    }

    private void initUIElements() {
        todoText = (EditText) findViewById(R.id.todoText);
        addButton = (Button) findViewById(R.id.addButton);
        addEventListeners();

    }

    private void addEventListeners() {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEntryToDatabase();
            }
        });


    }

    private void addEntryToDatabase() {
        Item item = new Item(todoText.getText().toString());
        mDatabase.child("users").child(mUserId).child("items").push().setValue(item);
        clearEditText();
    }

    private void clearEditText() {
        todoText.setText("");
    }

    private void setUpListView() {
        listView = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,android.R.id.text1);
        listView.setAdapter(adapter);
        setListViewListener();
    }

    private void setListViewListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                deleteItemFromDatabase(position);
            }
        });
    }

    private void deleteItemFromDatabase(int position) {
        mDatabase.child("users").child(mUserId).child("items")
                .orderByChild("title")
                .equalTo((String) listView.getItemAtPosition(position))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        removeData(dataSnapshot);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    private void removeData(DataSnapshot dataSnapshot) {
        if(dataSnapshot.hasChildren()){
            DataSnapshot firstChild = dataSnapshot.getChildren().iterator().next();
            firstChild.getRef().removeValue();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            mFirebaseAuth.signOut();
            startLogInActivity();
        }
        return super.onOptionsItemSelected(item);
    }

    private void startLogInActivity() {
        logInIntent = new Intent(this, LogInActivity.class);
        addFlagsToIntent(logInIntent);
        startActivity(logInIntent);
    }

    private void addFlagsToIntent(Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    }
}
