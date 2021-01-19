package com.basbaer.baked;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class mCategories {

    public static ArrayList<mCategories> allCategories;

    //keeps track if the 'All' is checked
    public static SharedPreferences sharedPreferences;

    private int id;
    private String name;
    private boolean isChecked;

    public mCategories(String name, boolean isChecked) {
        this.name = name;
        this.isChecked = isChecked;
    }


    public static void updateCategoriesList(Context context){
        if(allCategories == null) {
            allCategories = new ArrayList<>();
            sharedPreferences = context.getSharedPreferences("com.basbaer.baked", Context.MODE_PRIVATE);

            allCategories.add(new mCategories("All", sharedPreferences.getBoolean("isAllChecked", false)));

            ArrayList<mCategories> categories = TrackedActivity.getDifferentCategories();

            allCategories.addAll(categories);
        }else{
            //sort the list alphabetically
            Collections.sort(allCategories, new Comparator<mCategories>() {
                @Override
                public int compare(mCategories o1, mCategories o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });

        }

    }



    public String getName() {
        return name;
    }

    public boolean isChecked() {
        return isChecked;
    }


    public void setChecked(boolean checked) {
        isChecked = checked;
        if(this.getName().equals("All")){
            sharedPreferences.edit().putBoolean("isAllChecked", isChecked);
        }else {
            TrackedActivity.updateIsChecked(this.getName(), checked);
        }

    }
}