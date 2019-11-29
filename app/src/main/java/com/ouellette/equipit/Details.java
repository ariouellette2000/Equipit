package com.ouellette.equipit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ouellette.equipit.data.DatabaseHandler;
import com.ouellette.equipit.model.Product;

public class Details extends AppCompatActivity {

    private Toolbar myToolBar;

    private TextView idTV;
    private TextView nameTV;
    private TextView descriptionTV;
    private TextView companyTV;
    private TextView sizeTV;
    private TextView weightTV;
    private TextView waterproofTV;
    private TextView madeinTV;
    private TextView materialTV;
    private TextView reviewTV;
    private TextView notesTV;

    private Button editButton;
    private Button deleteButton;
    private Button receiptButton;

    Product productobj;
    String cat_name;

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
        setContentView(R.layout.activity_details);

        //ToolBar as the app bar
        myToolBar = findViewById(R.id.menu_toolbar);
        setSupportActionBar(myToolBar);


        //Receive Parcelable object
        Bundle bundle = getIntent().getExtras();

        if(bundle != null){
            Gson gson = new Gson();
            String productAsString = bundle.getString("productAsString");
            //Both the productobj(initialize at the top) and the Product.class has to be the model class
            productobj = gson.fromJson(productAsString, com.ouellette.equipit.model.Product.class);
        }

        //Find Views
          idTV = findViewById(R.id.id_title);
          nameTV = findViewById(R.id.name_title);
          descriptionTV = findViewById(R.id.description_title);
          companyTV = findViewById(R.id.company_title);
          sizeTV = findViewById(R.id.size_title);
          weightTV = findViewById(R.id.weight_title);
          waterproofTV = findViewById(R.id.waterproof_title);
          madeinTV = findViewById(R.id.madein_title);
          materialTV = findViewById(R.id.material_title);
          reviewTV = findViewById(R.id.review_title);
          notesTV = findViewById(R.id.notes_title);

          editButton = findViewById(R.id.edit_button);
          deleteButton = findViewById(R.id.delete_button);
          receiptButton = findViewById(R.id.receipt_button);

          //Set text for view title
          idTV.setText(productobj.getP_id());
          nameTV.setText(productobj.getP_name());
          descriptionTV.setText(productobj.getP_description());
          companyTV.setText(productobj.getP_company());
          sizeTV.setText(productobj.getP_size());
          weightTV.setText(String.valueOf(productobj.getP_weight()));
          waterproofTV.setText(productobj.getP_waterproof());
          madeinTV.setText(productobj.getP_made_in());
          materialTV.setText(productobj.getP_material());
          reviewTV.setText(String.valueOf(productobj.getP_review()));
          notesTV.setText(productobj.getP_notes());

        //Radio Start button
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Pass object to the Edit page
                Context context = view.getContext();
                Gson gson = new Gson();
                String productAsString = gson.toJson(productobj);

                Intent intent = new Intent(context, Edit.class);
                intent.putExtra("productAsString", productAsString);
                context.startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(Details.this, R.style.AlertDialog)
                        .setTitle("Delete Item")
                        .setMessage("Are you sure you want to delete this item?")

                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //Save the category
                                cat_name = productobj.getP_cat_name();

                                //Reach database
                                  DatabaseHandler db = new DatabaseHandler(Details.this);

                                  //Delete Product
                                  db.deleteItem(productobj);

                                //Change activity because item no longuer exist
                                Context context = Details.this;
                                Intent intent = new Intent(context, com.ouellette.equipit.Product.class);
                                intent.putExtra("cat_name", cat_name);
                                context.startActivity(intent);
                            }
                        })

                        .setNegativeButton(R.string.no, null)
                        .setIcon(R.drawable.warning_alert)
                        .show();






            }
        });

        receiptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Reach the database
                DatabaseHandler db = new DatabaseHandler(Details.this);

                //Get Receipt
                com.ouellette.equipit.model.Receipt receipt = db.getSpecificReceipt(productobj);

                //Parcelable object
                Context context = view.getContext();
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

                String receiptAsString = gson.toJson(receipt);

                //Initialize bundle
                Bundle bundle = new Bundle();
                bundle.putString("receiptobj",receiptAsString);
//
//              //Set Arguments
                Receipt receiptview = new Receipt();
                receiptview.setArguments(bundle);
//
//                //Display the fragment
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.linearLayoutContainer, receiptview);
                ft.commit();

            }
        });


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
                startActivity(new Intent(Details.this,Settings.class));
                return true;
            case R.id.menuitem_add_text:
                startActivity(new Intent (Details.this,Add.class));
                return true;
            case R.id.menuitem_home:
                startActivity(new Intent (Details.this,MainActivity.class));
                return true;
            default:
                //not recognize so invoke superclass
                return super.onOptionsItemSelected(item);
        }
    }
}
