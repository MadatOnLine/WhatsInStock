package team02.whatsinstock;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Ryan on 3/23/2018.
 * Searches the Edamam database for recipes that match the users selections.
 */

public class RecipeSearch {
    private static final String TAG = RecipeSearch.class.getSimpleName();

    /**
     * Sets up the queries and keys to access the Edamam recipe database.
     * @param ingredient1 the first item from ingredientsList.
     * @param ingredient2 the second item from ingredientsList.
     * @param callback used to tell MainActivity if the search was a success.
     */
    public static void searchRecipes(String ingredient1, String ingredient2, Callback callback) {
        String APP_KEY = "5204d972480b2a94b4787fa93baf8356";
        String APP_ID = "1a8f346c";

        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        String ingredients = (ingredient1 + "," + ingredient2).replaceAll("\\s","");
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api.edamam.com/search").newBuilder();
        urlBuilder.addQueryParameter("q", ingredients);
        urlBuilder.addQueryParameter("app_id", APP_ID);
        urlBuilder.addQueryParameter("app_key", APP_KEY);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    /**
     * This function takes the results of the search and parses them into an
     * array of the Recipe class.
     * @param response is the unparsed results of the search.
     * @return an ArrayList of all the recipes that were found in the search.
     */
    public ArrayList<Recipe> processResults(Response response) {
        ArrayList<Recipe> recipes = new ArrayList<>();

        try {
            String jsonData = response.body().string();
            if (response.isSuccessful()) {
                JSONObject returnJSON = new JSONObject(jsonData);
                JSONArray recipesJSON = returnJSON.getJSONArray("hits");
                for (int i = 0; i < 10; i++) {
                    JSONObject recipeArrayJSON = recipesJSON.getJSONObject(i);
                    JSONObject recipeJSON = recipeArrayJSON.getJSONObject("recipe");
                    String name = recipeJSON.getString("label");
                    String imageUrl = recipeJSON.getString("image");
                    String sourceUrl = recipeJSON.getString("url");
                    String ingredients = recipeJSON.getJSONArray("ingredientLines").toString();

                    Recipe recipe = new Recipe (name, imageUrl, sourceUrl, ingredients);
                    recipes.add(recipe);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return recipes;
    }
}
