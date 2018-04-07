package team02.whatsinstock;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * This activity displays the recipes that are found and will
 * allow a user to see the instructions of the recipe when selected.
 * This will open a web browser.
 */
public class DisplayResults extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_results);
        final List<Recipe> recipes = (ArrayList<Recipe>) getIntent().getSerializableExtra("RECIPE_LIST");

        /* Fills the list with the names of the found recipes. */
        List<String> names = new ArrayList<>();
        for (Recipe e : recipes) {
            names.add(e.getName());
        }
        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names);
        ListView resultList = (ListView) findViewById(R.id.result_list);
        resultList.setAdapter(adapter);
        resultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selected_recipe = ((TextView) view).getText().toString();
                for (Recipe e : recipes) {
                    /* Opens a web browser to the recipe location when selected. */
                    if (e.getName().equals(selected_recipe)) {
                        Uri uri = Uri.parse(e.getSourceUrl());
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                }
            }
        });
    }
}
