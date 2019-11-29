package com.ouellette.equipit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ouellette.equipit.data.DatabaseHandler;
import com.ouellette.equipit.model.Product;
import com.ouellette.equipit.model.Receipt;

import java.util.Calendar;

public class Add extends AppCompatActivity {

    private Button submitButton;
    private Button cancelButton;

    private Spinner categoryS;
    private EditText idET;
    private EditText nameET;
    private EditText descriptionET;
    private EditText companyET;
    private Spinner sizeS;
    private EditText weightET;
    private EditText waterproofET;
    private EditText madeinET;
    private EditText materialET;
    private Spinner reviewS;
    private EditText notesET;
    private DatePicker paydateDP;
    private DatePicker wardateDP;
    private EditText warlocationET;

    private Toolbar myToolBar;



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
        setContentView(R.layout.activity_add);

       DatabaseHandler db = new DatabaseHandler(Add.this);

        //ToolBar as the app bar
        myToolBar = findViewById(R.id.menu_toolbar);
        setSupportActionBar(myToolBar);

        //Populate the category spinner
            Spinner spinnerc = (Spinner) findViewById(R.id.category_input);
            // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter<CharSequence> adapterc = ArrayAdapter.createFromResource(this,
                    R.array.categories_array, android.R.layout.simple_spinner_item);
            // Specify the layout to use when the list of choices appears
            adapterc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
            spinnerc.setAdapter(adapterc);

        //Populate Size spinner
            Spinner spinners = (Spinner) findViewById(R.id.size_input);
            // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter<CharSequence> adapters = ArrayAdapter.createFromResource(this,
                    R.array.sizes_array, android.R.layout.simple_spinner_item);
            // Specify the layout to use when the list of choices appears
            adapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
            spinners.setAdapter(adapters);

        //Populate Review spinner
        Spinner spinnerr = (Spinner) findViewById(R.id.review_input);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterr = ArrayAdapter.createFromResource(this,
                R.array.reviews_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerr.setAdapter(adapterr);


        //submit button
        submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(insertProduct);

        //inputs
        categoryS = findViewById(R.id.category_input);
        idET = findViewById(R.id.id_input);
        nameET = findViewById(R.id.name_input);
        descriptionET = findViewById(R.id.description_input);
        companyET = findViewById(R.id.company_input);
        sizeS = findViewById(R.id.size_input);
        weightET = findViewById(R.id.weight_input);
        waterproofET = findViewById(R.id.waterproof_input);
        madeinET = findViewById(R.id.madein_input);
        materialET = findViewById(R.id.material_input);
        reviewS = findViewById(R.id.review_input);
        notesET = findViewById(R.id.notes_input);
        paydateDP = findViewById(R.id.paydate_input);
        wardateDP = findViewById(R.id.war_date_input);
        warlocationET = findViewById(R.id.war_location_input);


        //Cancel button
        cancelButton = findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Add.this, MainActivity.class));
            }
        });
    }

    private Button.OnClickListener insertProduct = new Button.OnClickListener(){

        @Override
        public void onClick(View view) {
            //Get text of all EditText
            String idText = idET.getText().toString();
            String nameText = nameET.getText().toString();

            if(idText.matches("")){
                Toast.makeText(Add.this, "You did not enter a product id", Toast.LENGTH_SHORT).show();
                return;
            }
            else if(nameText.matches("")) {
                Toast.makeText(Add.this, "You did not enter a product name", Toast.LENGTH_SHORT).show();
                return;
            }
            else{
            //Connect database
            DatabaseHandler db = new DatabaseHandler(Add.this);

            //Set a product
            Product product = new Product();
            product.setP_cat_name(categoryS.getSelectedItem().toString());
            product.setP_id(idText);
            product.setP_name(nameText);
            product.setP_description(descriptionET.getText().toString());
            product.setP_company(companyET.getText().toString());
            product.setP_size(sizeS.getSelectedItem().toString());
            String weightString = weightET.getText().toString();
            int weightInt =0;
            if(!(weightString.matches(""))) {
                weightInt = Integer.valueOf(weightString);
            }
            product.setP_weight(weightInt);
            product.setP_waterproof(waterproofET.getText().toString());
            product.setP_made_in(madeinET.getText().toString());
            product.setP_material(materialET.getText().toString());
            product.setP_review(Integer.valueOf(reviewS.getSelectedItem().toString()));
            product.setP_notes(notesET.getText().toString());

            //Set a Receipt
            Receipt receipt = new Receipt();
            receipt.setP_id(idET.getText().toString());
            receipt.setR_pay_date(getPayDateFromDatePicker(paydateDP));
            receipt.setR_warranty_date(getWarrantyDateFromDatePicker(wardateDP));
            receipt.setR_warranty_location(warlocationET.getText().toString());


            //Add product and receipt
            db.addProduct(product);
            db.addReceipt(receipt);

            //Pass done add
            Intent intent = new Intent(Add.this, MainActivity.class);
            intent.putExtra("add_product_name", product.getP_name());
            startActivity(intent);
        }

        }
    };

    //Pay date
    public static java.util.Date getPayDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }

    //Warranty Date
    public static java.util.Date getWarrantyDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
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
                startActivity(new Intent(Add.this,Settings.class));
                return true;
            case R.id.menuitem_add_text:
                startActivity(new Intent (Add.this,Add.class));
                return true;
            case R.id.menuitem_home:
                startActivity(new Intent (Add.this,MainActivity.class));
                return true;
            default:
                //not recognize so invoke superclass
                return super.onOptionsItemSelected(item);
        }
    }
}
