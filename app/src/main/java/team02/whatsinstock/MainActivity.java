package team02.whatsinstock;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // Ingredients should not be searched if none have been selected
    private List<String> ingredients = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = getSharedPreferences("SavedIngredients", MODE_PRIVATE);
        if (preferences.contains("0")) {
            int i = 0;
            while (preferences.contains(String.valueOf(i))) {
                String data = preferences.getString(String.valueOf(i++),"");
                ingredients.add(data);
            }
        }
        else {
            ingredients.add("fruit");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences.Editor editor = getSharedPreferences("SavedIngredients", MODE_PRIVATE).edit();
        int i = 0;
        for (String data : ingredients) {
            editor.putString(String.valueOf(i++), data);
        }
        editor.apply();
    }
}
