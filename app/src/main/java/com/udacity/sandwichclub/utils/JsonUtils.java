package com.udacity.sandwichclub.utils;

import android.os.Build;
import android.support.annotation.*;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String KEY_MAIN_NAME = "mainName";
    private static final String KEY_ALSO_KNOW_AS = "alsoKnownAs";
    private static final String KEY_PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String KEY_INGREDIENTS= "ingredients";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_DESCRIPTION = "description";

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Sandwich parseSandwichJson(String... json) {
        List<String> strAlsoKnown = new ArrayList<>();
        List<String> strIngredients = new ArrayList<>();
        Sandwich jSandWitchExt = new Sandwich();

        try {
            JSONObject jsonObject = new JSONObject(json[0]);
           jSandWitchExt.setMainName(jsonObject.getJSONObject("name").getString(KEY_MAIN_NAME));
//           jSandWitchExt.setImage(jsonObject.getString(KEY_IMAGE));
           jSandWitchExt.setImage(jsonObject.optString(KEY_IMAGE));
           jSandWitchExt.setPlaceOfOrigin(jsonObject.getString(KEY_PLACE_OF_ORIGIN));
           jSandWitchExt.setDescription(jsonObject.getString(KEY_DESCRIPTION));

           JSONArray jsonArrayAlso = jsonObject.getJSONObject("name").getJSONArray(KEY_ALSO_KNOW_AS);
           for(int count=0;count < jsonArrayAlso.length();count++){
               strAlsoKnown.add(jsonArrayAlso.getString(count));
           }
           jSandWitchExt.setAlsoKnownAs(strAlsoKnown);
           JSONArray jArrayIngredients = jsonObject.getJSONArray(KEY_INGREDIENTS);
           for(int count=0;count < jArrayIngredients.length();count++){
               strIngredients.add(jArrayIngredients.getString(count));
           }
           jSandWitchExt.setIngredients(strIngredients);
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return jSandWitchExt;
    }
}
