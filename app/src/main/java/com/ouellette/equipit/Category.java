package com.ouellette.equipit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.ouellette.equipit.adapter.RecyclerViewAdapter;
import com.ouellette.equipit.data.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;

import com.ouellette.equipit.model.Product;

public class Category extends AppCompatActivity {

    private Toolbar myToolBar;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<Product> productArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Color theme
        //Reach prefs
        SharedPreferences mPref = getSharedPreferences("idval",0);
        String selectedColor = mPref.getString("color_editor","");

        //change color when click
        switch (selectedColor) {
            case "Pastel":
                setTheme(R.style.PastelTheme);
                break;
            case "Dark":
                setTheme(R.style.DarkTheme);
                break;
            case "Fluo":
                setTheme(R.style.FluoTheme);
                break;
            default:
                setTheme(R.style.DarkTheme);
                break;
        }
        setContentView(R.layout.activity_category);

        //ToolBar as the app bar
        myToolBar = findViewById(R.id.menu_toolbar);
        setSupportActionBar(myToolBar);

        //Reach the database
        DatabaseHandler db = new DatabaseHandler(Category.this);

        //Initialize list and array list
        productArrayList = new ArrayList<>();

        //Recycler View
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        // to put horizontal recycler view
        // new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false)
        recyclerView.setLayoutManager(new LinearLayoutManager(Category.this));

        //create list final
        final List<Product> productList = db.getAllCategories();

        //no item in the list
        if(productList.isEmpty()){
            Toast.makeText(getApplicationContext(), "There is no product in your inventory",Toast.LENGTH_LONG).show();
        }
        //List elements method
        for(Product product: productList){
           productArrayList.add(product);
        }

        //set up recycler adapter
        recyclerViewAdapter = new RecyclerViewAdapter(Category.this, productArrayList);
        recyclerView.setAdapter(recyclerViewAdapter);

        //set adapter
        recyclerView.setAdapter(new RecyclerViewAdapter(productArrayList, new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product product) {
            }
        }));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.menuitem_settings:
                startActivity(new Intent(Category.this,Settings.class));
                return true;
            case R.id.menuitem_add_text:
                startActivity(new Intent (Category.this,Add.class));
                return true;
            case R.id.menuitem_home:
                startActivity(new Intent (Category.this,MainActivity.class));
                return true;
            default:
                //not recognize so invoke superclass
                return super.onOptionsItemSelected(item);
        }
    }
}
