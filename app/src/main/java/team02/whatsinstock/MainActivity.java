package team02.whatsinstock;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * <p>Main Activity  starts the program calls onCreate(), onSearch(), onStop().
 * Sets up the ListViews.</p>
 */
public class MainActivity extends AppCompatActivity {
    private ArrayList <String> ingredientsList = new ArrayList<>();
    private static final String S_TAG = "List Check";
    private ArrayList<Recipe> recipes = new ArrayList<>();
    private ProgressBar progressBar;
    private int progressBarStatus = 0;
    private Handler progressHandler = new Handler();

    /**
     * Sets up app on start and defines local variables.
     * @param savedInstanceState uses any saved information.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //Protein Checklist
        String[] pItems = {"Anchovies", "Bacon", "Beef", "Chicken", "Crab", "Duck", "Eggs", "Ham", "Lamb", "Pork", "Salmon", "Sardines", "Sausage", "Shrimp", "Tuna", "Turkey", "Peanut", "Walnut", "Chesnutt", "Cashew", "Pistachio", "Sunflower"};
        ArrayAdapter<String> pAdapter = new ArrayAdapter<>(this, R.layout.rowlayout, pItems);
        ListView pList = (ListView) findViewById(R.id.Proteins);
        pList.setAdapter(pAdapter); // Error starts here
        pList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        pList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String ingredient = ((TextView) view).getText().toString();
                if (ingredientsList.contains(ingredient)) {
                    ingredientsList.remove(ingredient);
                } else {
                    ingredientsList.add(ingredient);
                    Log.d(S_TAG, "Size: " + ingredientsList.size());
                }
            }
        });

        //Dairy Checklist
        String[] dItems = {"Butter", "Cheese", "Custard", "Milk", "Yogurt"};
        ArrayAdapter<String> dAdapter = new ArrayAdapter<>(this, R.layout.rowlayout, dItems);
        ListView dList = (ListView) findViewById(R.id.Dairy);
        dList.setAdapter(dAdapter); // Error starts here
        dList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        dList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        String[] gItems = {"Allspice", "Basil", "Oregano", "BayLeaves", "Beans", "Pepper", "Bread", "CarawaySeed", "Cayenne", "Chives", "Cumin", "Cuscus", "DillWeed", "Flour", "Ketchup", "Lentils", "Marjoram", "Mayo", "Nutmeg", "Oats", "Paprika", "Parsley", "Pasta", "Quinoa", "Rice", "Saffron", "Salt", "Sugar", "Tarragon", "Thyme", "Vinegar", "Mustard"};
        ArrayAdapter<String> gAdapter = new ArrayAdapter<>(this, R.layout.rowlayout, gItems);
        ListView gList = (ListView) findViewById(R.id.Grains);
        gList.setAdapter(gAdapter); // Error starts here
        gList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        gList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        String[] fItems = {"Apple", "Apricot", "Artichoke", "Asparagus", "Avocado", "Banana", "Beet", "Blackberry", "Blueberry", "Broccoli",
                "BrusselsSprout", "Cabbage", "Cantaloupe", "Carrot", "Cauliflower", "Celery", "Cherry", "ChiliPepper", "Coconut",
               "Corn", "Cranberry", "Cucumber", "Durian", "Eggplant", "Fig", "Garlic", "Grape", "Grapefruit", "GreenBean", "Peppers", "Kale", "Kiwi", "Leek",
               "Lemon", "Lettuce", "Lychee", "Mango", "Olive", "Onion", "Orange", "Papaya", "Pea", "Peach", "Pear", "Pineapple", "Plum", "Pomegranate", "Potato", "Prune", "Radish", "Raisin",
                "Raspberry", "Scallion", "Spinach", "Squash", "Strawberry", "Tamarind", "Tomato", "Turnip", "Watercress", "Watermelon", "Zucchini", "OliveOil", "VegetableOil", "CanolaOil", "PeanutOil"};
        ArrayAdapter<String> fAdapter = new ArrayAdapter<>(this, R.layout.rowlayout, fItems);
        ListView fList = findViewById(R.id.Fruits);
        fList.setAdapter(fAdapter);
        fList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        fList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

    /**
     * Sends [ingredientsList] to SearchRecipe class on button prompt.
     * @param view works with search button.
     */
    public void onSearch(View view) throws UnirestException {
        /* Toasts to keep the user informed. */
        final Toast toastEmpty = Toast.makeText(this,"At least two ingredients needed",Toast.LENGTH_LONG);
        final Toast toastFail = Toast.makeText(this,"No recipes found",Toast.LENGTH_LONG);
        final Toast toastSearch = Toast.makeText(this,"Search started",Toast.LENGTH_LONG);

        if (ingredientsList.size() < 2) {
            toastEmpty.show(); // Make sure we are not searching for nothing
        }
        else {
            final RecipeSearch recipeSearch = new RecipeSearch();
            RecipeSearch.searchRecipes(ingredientsList.get(0), ingredientsList.get(1), new Callback(){
                @Override
                public void onFailure(Call call, IOException e) {e.printStackTrace();}

                @Override
                public void onResponse(Call call, Response response) {
                    toastSearch.show();
                    recipes = recipeSearch.processResults(response);
                    final ArrayList<Recipe> filteredResults = new ArrayList<>();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            /*Filters out recipes that contain ingredients not in the ingredients list provided*/
                            for (Recipe data : recipes) {
                                boolean check = true;
                                progressBarStatus += (100/recipes.size());
                                String rList[] = data.getIngredients();
                                for (int i = 0; i < rList.length; i++) {
                                    /*
                                    if (!(ingredientsList.contains(rList[i]))) {
                                        check = false;
                                    }*/ //Unfortunately, the API only takes two ingredients and the list given by the API has measurements too, so filtering is almost impossible.
                                }
                                if (check) {
                                    filteredResults.add(data);
                                }

                                progressHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressBar.setProgress(progressBarStatus);
                                    }
                                });
                            }
                        }
                    }).start();

                    /* Call new activity to display results */
                    if(recipes.size() != 0) {
                        Intent display = new Intent(MainActivity.this, DisplayResults.class);
                        display.putExtra("RECIPE_LIST", recipes);
                        startActivity(display);
                    }
                    else {
                        toastFail.show(); // If nothing is found, the user is notified.
                    }
                }
            });

        }


    }
}
