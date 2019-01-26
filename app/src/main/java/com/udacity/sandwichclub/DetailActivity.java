package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    ImageView ingredientsIv = null;
    TextView tvAlsoKnownAs;
    TextView tvIngredients;
    TextView tvDescription;
    TextView tvPlace;
    Sandwich sandwich;

    String json = null;
    Intent intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);
        tvAlsoKnownAs = findViewById(R.id.tv_also_known);
        ingredientsIv = findViewById(R.id.image_iv);
        tvDescription = findViewById(R.id.tv_Description);
        tvIngredients = findViewById(R.id.tv_ingredients_details);
        tvPlace = findViewById(R.id.tv_origin);

        sandwich = new Sandwich();
        intent = getIntent();
        if (intent == null) {
            closeOnError();
        }
        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }
        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        json = sandwiches[position];
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            sandwich = null;
            sandwich = JsonUtils.parseSandwichJson(json);
            tvAlsoKnownAs.invalidate();
            tvAlsoKnownAs.setText("");
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }
        populateUI(sandwich);                       // populate UI
    }

    private void closeOnError() {
        tvAlsoKnownAs = null;
        sandwich = null;
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwichIn) {
        setTitle(sandwich.getMainName());
        if (sandwichIn.getPlaceOfOrigin() != null && !sandwichIn.getPlaceOfOrigin().equals("")) {
            tvPlace.setText(sandwichIn.getPlaceOfOrigin());
        } else {
            tvPlace.setText("Info unavailable");
        }
        tvDescription.setText(sandwichIn.getDescription());
        if (sandwichIn.getImage() != null) {
            Picasso.with(this)
                    .load(sandwichIn.getImage())
                    .error(R.drawable.ic_broken_image_black_24dp)
                    .into(ingredientsIv);
            if (sandwichIn.getAlsoKnownAs().size() != 0 && !sandwichIn.getAlsoKnownAs().equals(0)) {      // alsoKnownAs
                for (String strT : sandwichIn.getAlsoKnownAs()) {
                    if (strT.isEmpty()) {
                        tvAlsoKnownAs.setText(strT);
                    } else {
                        tvAlsoKnownAs.append("\n" + strT);
                    }
                }
            } else {
                tvAlsoKnownAs.setText(R.string.alsoKnownIsEmpty);
            }
            if (sandwichIn.getIngredients().size() != 0 && !sandwichIn.getIngredients().equals(0)) {      // Ingredients
                for (String strTIng : sandwichIn.getIngredients()) {
                    if (strTIng.isEmpty()) {
                        tvIngredients.setText(strTIng);
                    } else {
                        tvIngredients.append("\n" + strTIng);
                    }
                }
            } else {
                tvIngredients.setText(R.string.IngredientsEmpty);
            }
        }
    }
}
