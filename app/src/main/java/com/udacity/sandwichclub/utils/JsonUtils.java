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

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Sandwich parseSandwichJson(String... json) {
        List<String> strAlsoKnown = new ArrayList<>();
        List<String> strIngredients = new ArrayList<>();
        Sandwich jSandWitchExt = new Sandwich();

        try {
            JSONObject jsonObject = new JSONObject(json[0]);
           jSandWitchExt.setMainName(jsonObject.getJSONObject("name").getString("mainName"));
           jSandWitchExt.setImage(jsonObject.getString("image"));
           jSandWitchExt.setPlaceOfOrigin(jsonObject.getString("placeOfOrigin"));
           jSandWitchExt.setDescription(jsonObject.getString("description"));

           JSONArray jsonArrayAlso = jsonObject.getJSONObject("name").getJSONArray("alsoKnownAs");
           for(int count=0;count < jsonArrayAlso.length();count++){
               strAlsoKnown.add(jsonArrayAlso.getString(count));
           }
           jSandWitchExt.setAlsoKnownAs(strAlsoKnown);
           JSONArray jArrayIngredients = jsonObject.getJSONArray("ingredients");
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
