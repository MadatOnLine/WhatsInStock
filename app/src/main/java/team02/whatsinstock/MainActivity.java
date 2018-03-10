package team02.whatsinstock;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // Ingredients should not be searched if none have been selected
    private List <String> ingredientsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = getSharedPreferences("SavedIngredients", MODE_PRIVATE);
        if (preferences.contains("0")) {
            int i = 0;
            while (preferences.contains(String.valueOf(i))) {
                String data = preferences.getString(String.valueOf(i++),"");
                ingredientsList.add(data);
            }
        }

        //Protein Checklist
        ListView protein = (ListView) findViewById(R.id.Protein);
        protein.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        String[] pItems = {"Beef", "Pork", "Chicken", "Fish"};
        ArrayAdapter<String> pAdapter = new ArrayAdapter<>(this, R.layout.rowlayout, R.id.Protein, pItems);
        protein.setAdapter(pAdapter);
        protein.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String ingredient = ((TextView) view).getText().toString();
                if (ingredientsList.contains(ingredient)) {
                    ingredientsList.remove(ingredient);
                } else {
                    ingredientsList.add(ingredient);
                }
            }
        });


        //Dairy Checklist
        ListView dairy = (ListView) findViewById(R.id.Dairy);
        dairy.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        String[] dItems = {"Beef", "Pork", "Chicken", "Fish"};
        ArrayAdapter<String> dAdapter = new ArrayAdapter<>(this, R.layout.rowlayout, R.id.Dairy, dItems);
        dairy.setAdapter(dAdapter);
        dairy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String ingredient = ((TextView) view).getText().toString();
                if (ingredientsList.contains(ingredient)) {
                    ingredientsList.remove(ingredient);
                } else {
                    ingredientsList.add(ingredient);
                }
            }
        });

        //Grains Checklist
        ListView grains = (ListView) findViewById(R.id.Grains);
        grains.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        String[] gItems = {"Beef", "Pork", "Chicken", "Fish"};
        ArrayAdapter<String> gAdapter = new ArrayAdapter<>(this, R.layout.rowlayout, R.id.Grains, gItems);
        grains.setAdapter(gAdapter);
        grains.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String ingredient = ((TextView) view).getText().toString();
                if (ingredientsList.contains(ingredient)) {
                    ingredientsList.remove(ingredient);
                } else {
                    ingredientsList.add(ingredient);
                }
            }
        });

        //Fruits and Vegetables Checklist
        ListView fruits = (ListView) findViewById(R.id.Fruits);
        fruits.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        String[] fItems = {"Beef", "Pork", "Chicken", "Fish"};
        ArrayAdapter<String> fAdapter = new ArrayAdapter<>(this, R.layout.rowlayout, R.id.Fruits, fItems);
        fruits.setAdapter(fAdapter);
        fruits.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String ingredient = ((TextView) view).getText().toString();
                if (ingredientsList.contains(ingredient)) {
                    ingredientsList.remove(ingredient);
                } else {
                    ingredientsList.add(ingredient);
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences.Editor editor = getSharedPreferences("SavedIngredients", MODE_PRIVATE).edit();
        int i = 0;
        for (String data : ingredientsList) {
            editor.putString(String.valueOf(i++), data);
        }
        editor.apply();
    }
}
