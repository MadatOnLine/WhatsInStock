package team02.whatsinstock;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * <p>Main Activity  starts the program calls onCreate(), onSearch(), onStop().
 * Sets up the ListViews.</p>
 */
public class MainActivity extends AppCompatActivity {
    private List <String> ingredientsList = new ArrayList<>();
    private static final String S_TAG = "List Check";
    private ArrayList<Recipe> recipes = new ArrayList<>();
    private ProgressBar progressBar;
    private int progressBarStatus = 0;
    private Handler progressHandler = new Handler();

    /**
     * Sets up app on start and defines local variables.
     * Will use saved [ingredientsList] if one is found.
     * @param savedInstanceState uses any saved information.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        SharedPreferences preferences = getSharedPreferences("SavedIngredients", MODE_PRIVATE);
        if (preferences.contains("0")) {
            int i = 0;
            while (preferences.contains(String.valueOf(i))) {
                String data = preferences.getString(String.valueOf(i++),"");
                ingredientsList.add(data);
            }
        }
        else {
            ingredientsList.add("chicken");
            ingredientsList.add("flour");
            ingredientsList.add("sugar");
        }

        /*
        //Protein Checklist
        ListView food = (ListView) findViewById(R.id.Food);
        food.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        String[] items = {"Beef", "Pork", "Chicken", "Fish", "Milk", "Cheese", "Tomatoes", "Lettuce"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.rowlayout, R.id.Food, items);
        food.setAdapter(adapter); // Error starts here
        food.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        String[] dItems = {"Milk"};
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
        }); */
    }

    /**
     * Saves [ingredientsList] when app is closed.
     */
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

    /**
     * Sends [ingredientsList] to SearchRecipe class on button prompt.
     * @param view works with search button.
     */
    public void onSearch(View view) throws UnirestException {
        if (ingredientsList.size() == 0) {
            Log.e(S_TAG, "List is empty.");
        }
        else {
            Log.d(S_TAG, "Starting search.");

            final RecipeSearch recipeSearch = new RecipeSearch();
            RecipeSearch.searchRecipes(ingredientsList.get(0), ingredientsList.get(1), new Callback(){
                @Override
                public void onFailure(Call call, IOException e) {e.printStackTrace();}

                @Override
                public void onResponse(Call call, Response response) {
                    recipes = recipeSearch.processResults(response);
                    Log.d("TAG", recipes.get(0).getName());

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            /*filter will happen here in milestone 3*/
                            while (progressBarStatus < 100){
                                progressBarStatus++;
                                android.os.SystemClock.sleep(50);
                                progressHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressBar.setProgress(progressBarStatus);
                                    }
                                });
                            }
                            progressHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Log.d(S_TAG, "Finished!");
                                }
                            });
                        }
                    }).start();

                    /* call new activity */
                    Intent display = new Intent(MainActivity.this, DisplayResults.class);
                    display.putExtra("RECIPE_LIST", recipes);
                    startActivity(display);
                }
            });

        }


    }
}
