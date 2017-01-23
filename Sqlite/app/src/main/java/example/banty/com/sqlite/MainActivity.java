package example.banty.com.sqlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import example.banty.com.sqlite.model.Superhero;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHelper db = new DatabaseHelper(this);

        Log.d(TAG, "onCreate: inserting data");
        Superhero ironman = new Superhero(1, "IRONMAN", "Iron Man (Anthony Edward Tony Stark) is a fictional superhero appearing in American comic books published by Marvel Comics, as well as its associated media.", "http://davidspell.com/wp-content/uploads/2014/10/IronMan-Avengers.png");
        Superhero captainAmerica = new Superhero(2, "CAPTAIN AMERICA", "Captain America is a fictional superhero appearing in American comic books published by Marvel Comics. Created by cartoonists Joe Simon and Jack Kirby", "https://s-media-cache-ak0.pinimg.com/originals/9c/a7/6f/9ca76f13edd0bce1e5b9ed3a0eabbb17.jpg");
//        db.createData(ironman);
//        db.createData(captainAmerica);

        Log.d(TAG, "onCreate: retrieving data");
        String text = "";
        List<Superhero> superheros = db.getAllSuperheros();
        for(Superhero superhero : superheros){
            text += " " + superhero.getName();
        }
        ((TextView) findViewById(R.id.name)).setText(text);
//
        text = "";
        Superhero superhero = new Superhero(1,"IRON MAN","Iron Man (Anthony Edward Tony Stark) is a fictional superhero appearing in American comic books published by Marvel Comics, as well as its associated media.","http://davidspell.com/wp-content/uploads/2014/10/IronMan-Avengers.png");
        db.updateSuperHero(superhero);
        superheros = db.getAllSuperheros();
        for(Superhero hero : superheros){
            text += " " + hero.getName();
        }
        ((TextView) findViewById(R.id.name)).setText(text);

        db.deleteData(ironman);

        text = "";
        superheros = db.getAllSuperheros();
        for(Superhero hero : superheros){
            text += " " + hero.getName();
        }
        ((TextView) findViewById(R.id.name)).setText(text);

    }
}
