package com.example.bibliotheque;



import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by User on 2/28/2017.
 */

public class EditDataActivity extends AppCompatActivity {
    private String selectedName;
    private int selectedID;
    String id;
    private static final String TAG = "EditDataActivity";

    private Button btnSave,btnDelete,view;

    DatabaseHelper mDatabaseHelper;

    String Auteur ;
    String Titre ;
    String Motcle ;
    String Resume;
    EditText auteu,titr,motcl,resum ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        view = (Button) findViewById(R.id.editview);
        auteu = findViewById(R.id.auteur);
        titr = findViewById(R.id.titre);
        motcl = findViewById(R.id.motcle);
        resum = findViewById(R.id.resume);

        Button update_button, delete_button;


        update_button = findViewById(R.id.btnSave);
        delete_button = findViewById(R.id.btnDelete);

        //First we call this
        getAndSetIntentData();

        //Set actionbar title after getAndSetIntentData method
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(Titre);
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditDataActivity.this, ListDataActivity.class);
                startActivity(intent);
            }
        });



        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //And only then we call this
                DatabaseHelper myDB = new DatabaseHelper(EditDataActivity.this);
                Titre = titr.getText().toString().trim();
                Auteur = auteu.getText().toString().trim();
                Resume = resum.getText().toString().trim();
               Motcle = motcl.getText().toString().trim();
                myDB.updateData(id,Titre, Auteur, Motcle,Resume);
            }
        });
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });

    }
        void getAndSetIntentData(){
            if(getIntent().hasExtra("id") && getIntent().hasExtra("title") &&
                    getIntent().hasExtra("author") && getIntent().hasExtra("motcle")&& getIntent().hasExtra("resume")){
                //Getting Data from Intent
                id = getIntent().getStringExtra("id");
                Titre = getIntent().getStringExtra("title");
                Auteur = getIntent().getStringExtra("author");
                Resume = getIntent().getStringExtra("resume");
                Motcle = getIntent().getStringExtra("motcle");

                //Setting Intent Data
                titr.setText(Titre);
                auteu.setText(Auteur);
                resum.setText(Resume);
                motcl.setText(Motcle);
            }else{
                Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
            }
        }

        void confirmDialog(){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Delete " + titr + " ?");
            builder.setMessage("Are you sure you want to delete " + titr + " ?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    DatabaseHelper myDB = new DatabaseHelper(EditDataActivity.this);
                    myDB.deleteOneRow(id);
                    finish();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.create().show();
        }
    }







