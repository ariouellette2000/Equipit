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
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Settings extends AppCompatActivity {

    private RadioGroup colors;
    private RadioButton color;
    private Button apply;
    private Button cancel;

    private Toolbar myToolBar;

    private int selectedId;
    private String selectedColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Color theme
        //Reach prefs
        SharedPreferences mPref = getSharedPreferences("idval",0);
        String selectColor = mPref.getString("color_editor","");

        //change color when click
        switch (selectColor) {
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
        setContentView(R.layout.activity_settings);


        //ToolBar as the app bar
        myToolBar = findViewById(R.id.menu_toolbar);
        setSupportActionBar(myToolBar);

        //method click button
        colors = findViewById(R.id.radioGroupColor);
        apply = findViewById(R.id.apply_button);
        cancel = findViewById(R.id.cancel_button);

        //Find radiobutton with the id chosen
        color = findViewById(getResources().getIdentifier(selectColor, "id", getPackageName()));
        if(color != null) {
            //check chosen radiobutton
            color.setChecked(true);
        }else{
            //last addition
            color=findViewById(R.id.Dark);
            color.setChecked(true);
        }

        //apply button clicked
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get selected radio button
                selectedId = colors.getCheckedRadioButtonId();
                //find radiobutton by returned id
                color = findViewById(selectedId);
                //define selected color
                selectedColor = color.getText().toString();
                //save the color
                SharedPreferences mPref = getSharedPreferences("idval",MODE_PRIVATE);
                SharedPreferences.Editor editor = mPref.edit();
                editor.putString("color_editor", selectedColor);
                editor.apply();
                startActivity(new Intent (Settings.this,Settings.class));

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent (Settings.this,MainActivity.class));
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences mPref = getSharedPreferences("idval",MODE_PRIVATE);
        selectedColor = mPref.getString("color_editor","");
        SharedPreferences.Editor editor = mPref.edit();
        editor.putString("color_editor", selectedColor);
        editor.apply();
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
                startActivity(new Intent (Settings.this,Settings.class));
                return true;
            case R.id.menuitem_add_text:
                startActivity(new Intent (Settings.this,Add.class));
                return true;
            case R.id.menuitem_home:
                startActivity(new Intent (Settings.this,MainActivity.class));
                return true;
            default:
                //not recognize so invoke superclass
                return super.onOptionsItemSelected(item);
        }
    }
}
