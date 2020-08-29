package com.example.recipeapp2.Breakfest;

import java.util.List;

public class Recycler_total {

    String Description;
    List<String> Ingredients;
    int Rating;
    String Name;
    String Notes;
    String Nutrition;
    String PreparationTime;
    String Procedure;
    String cooktime;
    String totaltime;
    String image_url_1;
    String image_url_2;
    String code;
    String key;

    public Recycler_total(String Description, String Name, String Notes, List<String> Ingredients, int Rating, String Nutrition, String PreparationTime, String Procedure,
                          String cooktime, String totaltime, String image_url_2, String image_url_1, String key, String code) {
        this.Description = Description;
        this.Name = Name;
        this.Rating = Rating;
        this.Ingredients = Ingredients;
        this.Notes = Notes;
        this.Nutrition = Nutrition;
        this.PreparationTime = PreparationTime;
        this.Procedure = Procedure;
        this.cooktime = cooktime;
        this.totaltime = totaltime;
        this.image_url_1 = image_url_1;
        this.image_url_2 = image_url_2;
        this.key = key;
        this.code = code;
    }

    public Recycler_total() {
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
//
//    public String[] getIngredients() {
//        return Ingredients;
//    }
//
//    public void setIngredients(String[] ingredients) {
//        Ingredients = ingredients;
//    }


    public int getRating() {
        return Rating;
    }

    public void setRating(int rating) {
        Rating = rating;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }

    public String getNutrition() {
        return Nutrition;
    }

    public void setNutrition(String nutrition) {
        Nutrition = nutrition;
    }

    public String getPreparationTime() {
        return PreparationTime;
    }

    public void setPreparationTime(String preparationTime) {
        PreparationTime = preparationTime;
    }

    public String getProcedure() {
        return Procedure;
    }

    public void setProcedure(String procedure) {
        Procedure = procedure;
    }

    public String getCooktime() {
        return cooktime;
    }

    public void setCooktime(String cooktime) {
        this.cooktime = cooktime;
    }

    public String getTotaltime() {
        return totaltime;
    }

    public void setTotaltime(String totaltime) {
        this.totaltime = totaltime;
    }

    public String getImage_url_1() {
        return image_url_1;
    }

    public void setImage_url_1(String image_url_1) {
        this.image_url_1 = image_url_1;
    }

    public String getImage_url_2() {
        return image_url_2;
    }

    public void setImage_url_2(String image_url_2) {
        this.image_url_2 = image_url_2;
    }

    public List<String> getIngredients() {
        return Ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        Ingredients = ingredients;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
