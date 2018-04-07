package team02.whatsinstock;

import java.io.Serializable;

/**
 * Created by Ryan on 3/23/2018.
 * This class is used to hold all the information for a Recipe from
 * Edamam Recipe Database.
 */

public class Recipe implements Serializable {
    private String name;
    private String imageUrl;
    private String sourceUrl;
    private String[] ingredients;
    private String pushId;

    public Recipe() {};

    public Recipe(String name, String imageUrl, String sourceUrl, String ingredients) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.sourceUrl = sourceUrl;
        this.ingredients = ingredients.replaceAll("\\[","").replaceAll("\\]","").replaceAll("\\\\","").split("\",\"");
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }
}
