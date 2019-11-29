package com.ouellette.equipit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.ouellette.equipit.data.DatabaseHandler;

public class MainActivity extends AppCompatActivity {

    private Toolbar myToolBar;
    private Button startButton;

    private String selectedColor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Color theme
//        //Reach prefs
        SharedPreferences mPref = getSharedPreferences("idval", 0);
        String selectColor = mPref.getString("color_editor", "");

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
        setContentView(R.layout.activity_main);


        //ToolBar as the app bar
        myToolBar = findViewById(R.id.menu_toolbar);
        setSupportActionBar(myToolBar);

        //Radio Start button
        startButton = findViewById(R.id.StartButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Category.class));
                shakeAnimation();
            }
        });

        //Message Toast when item added
        String intentAdd = getIntent().getStringExtra("add_product_name");
        if (intentAdd != null) {
            Context context = getApplicationContext();
            String toasttext = "You've added " + intentAdd;
            Toast.makeText(context, toasttext, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        //set default theme color
        setTheme(R.style.PastelTheme);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuitem_settings:
                startActivity(new Intent(MainActivity.this, Settings.class));
                return true;
            case R.id.menuitem_add_text:
                startActivity(new Intent(MainActivity.this, Add.class));
                return true;
            case R.id.menuitem_home:
                startActivity(new Intent(MainActivity.this, MainActivity.class));
                return true;
            default:
                //not recognize so invoke superclass
                return super.onOptionsItemSelected(item);
        }
    }

    private void shakeAnimation() {
        Animation shake = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake_animation);
        startButton.startAnimation(shake);

        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
