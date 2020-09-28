package com.example.bibliotheque;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ListDataActivity extends AppCompatActivity {


    private ArrayAdapter<String> adapter;
        RecyclerView recyclerView;
        FloatingActionButton add_button;
        ImageView empty_imageview;
        TextView no_data;

        DatabaseHelper myDB;
        ArrayList<String> book_id, book_title, book_author, book_resume,book_motcle;
        CustomAdapter customAdapter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.list_layout);
            ListView list = (ListView) findViewById(R.id.theList);
            recyclerView = findViewById(R.id.recyclerView);
            add_button = findViewById(R.id.add_button);
            empty_imageview = findViewById(R.id.empty_imageview);
            no_data = findViewById(R.id.no_data);
            EditText theFilter = (EditText) findViewById(R.id.searchFilter);
            add_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ListDataActivity.this, Add.class);
                    startActivity(intent);
                }
            });



            myDB = new DatabaseHelper(ListDataActivity.this);
            book_id = new ArrayList<>();
            book_title = new ArrayList<>();
            book_author = new ArrayList<>();
            book_motcle = new ArrayList<>();
            book_resume = new ArrayList<>();


            storeDataInArrays();

            customAdapter = new CustomAdapter(ListDataActivity.this,this, book_id, book_title, book_author,
                    book_motcle,book_resume);
            recyclerView.setAdapter(customAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(ListDataActivity.this));



            adapter = new ArrayAdapter<>(this, R.layout.list_item_layout,book_title);



            list.setAdapter(adapter);

            theFilter.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    (ListDataActivity.this).adapter.getFilter().filter(charSequence);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

        }





        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode == 1){
                recreate();
            }
        }

        void storeDataInArrays(){
            Cursor cursor = myDB.readAllData();
            if(cursor.getCount() == 0){
                empty_imageview.setVisibility(View.VISIBLE);
                no_data.setVisibility(View.VISIBLE);
            }else{
                while (cursor.moveToNext()){
                    book_id.add(cursor.getString(0));
                    book_title.add(cursor.getString(1));
                    book_author.add(cursor.getString(2));
                    book_motcle.add(cursor.getString(3));
                    book_resume.add(cursor.getString(4));

                }
                empty_imageview.setVisibility(View.GONE);
                no_data.setVisibility(View.GONE);
            }
        }



}










