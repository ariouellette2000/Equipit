package com.ouellette.equipit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


//import com.ouellette.equipit.adapter.RecyclerViewAdapterTwo;
import com.ouellette.equipit.adapter.RecyclerViewAdapter;
import com.ouellette.equipit.adapter.RecyclerViewAdapterTwo;
import com.ouellette.equipit.data.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;

//Add the implements for the parceable interface
public class Product extends AppCompatActivity {



    private Toolbar myToolBar;
    private RecyclerView recyclerViewTwo;
    private RecyclerViewAdapterTwo recyclerViewAdapterTwo;
    private ArrayList<com.ouellette.equipit.model.Product> productArrayList;

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
        setContentView(R.layout.activity_product);

        //ToolBar as the app bar
        myToolBar = findViewById(R.id.menu_toolbar);
        setSupportActionBar(myToolBar);

        //Retrieve the clicked category name
        Bundle bundle = getIntent().getExtras();
        String catName = bundle.getString("cat_name");

        //Reach the database
        DatabaseHandler db = new DatabaseHandler(com.ouellette.equipit.Product.this);

        //Initialize list and array list
        productArrayList = new ArrayList<>();

        //Recycler View
        recyclerViewTwo = findViewById(R.id.recyclerview);
        recyclerViewTwo.setHasFixedSize(true);
        recyclerViewTwo.setLayoutManager(new LinearLayoutManager(com.ouellette.equipit.Product.this));

        //create list final
        final List<com.ouellette.equipit.model.Product> productList = db.getSpecificCategoryProduct(catName);

        //List elements method
        for(com.ouellette.equipit.model.Product product: productList){
            productArrayList.add(product);
        }

        //set up recycler adapter
        recyclerViewAdapterTwo = new RecyclerViewAdapterTwo(Product.this, productArrayList);
        recyclerViewTwo.setAdapter(recyclerViewAdapterTwo);

        recyclerViewTwo.setAdapter(new RecyclerViewAdapterTwo(productArrayList, new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(com.ouellette.equipit.model.Product product) {
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
                startActivity(new Intent(Product.this,Settings.class));
                return true;
            case R.id.menuitem_add_text:
                startActivity(new Intent (Product.this,Add.class));
                return true;
            case R.id.menuitem_home:
                startActivity(new Intent (Product.this,MainActivity.class));
                return true;
            default:
                //not recognize so invoke superclass
                return super.onOptionsItemSelected(item);
        }
    }


}
