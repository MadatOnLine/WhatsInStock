package team02.whatsinstock;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    // Ingredients should not be searched if none have been selected
    public List<String> ingredients = null;
    // Results from using Search should not return zero initially
    public List<String> results = null;
    // Searches from the ingredients search bar should not be null
    public String searchIngredients = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
}
