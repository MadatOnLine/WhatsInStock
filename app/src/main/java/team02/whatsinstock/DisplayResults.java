package team02.whatsinstock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DisplayResults extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_results);
        List<Recipe> recipes = (ArrayList<Recipe>) getIntent().getSerializableExtra("RECIPE_LIST");

        ListView resultList = (ListView) findViewById(R.id.result_list);
        List<String> names = new ArrayList<>();
        for (Recipe e : recipes) {
            names.add(e.getName());
        }
        /*
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_recipe_layout, R.id.result_list, names);
        resultList.setAdapter(adapter);*/
    }
}
