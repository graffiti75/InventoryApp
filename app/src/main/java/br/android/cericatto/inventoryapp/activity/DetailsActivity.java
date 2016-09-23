package br.android.cericatto.inventoryapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import br.android.cericatto.inventoryapp.R;
import br.android.cericatto.inventoryapp.utils.Globals;
import br.android.cericatto.inventoryapp.view.Navigation;

/**
 * MainActivity.java.
 *
 * @author Rodrigo Cericatto
 * @since Sep 12, 2016
 */
public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {

    //--------------------------------------------------
    // Constants
    //--------------------------------------------------

    private static final String EMAIL = "graffiti75@gmail.com";

    //--------------------------------------------------
    // Attributes
    //--------------------------------------------------

    /**
     * Context.
     */

    private Activity mActivity = DetailsActivity.this;

    /**
     * Layout.
     */

    private LinearLayout mOrderMoreLinearLayout;
    private ImageView mUrlImageView;
    private TextView mNameTextView;
    private TextView mQuantityAvailableTextView;
    private TextView mPriceTextView;

    /**
     * Extras.
     */

    private String mImageUrl;
    private String mProductName;
    private String mQuantityAvailable;
    private String mPrice;

    //--------------------------------------------------
    // Activity Life Cycle
    //--------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        initToolbar(true);
        getExtras();
        setLayout();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Navigation.animate(this, Navigation.Animation.BACK);
    }

    //--------------------------------------------------
    // Menu
    //--------------------------------------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        Integer id = menuItem.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return false;
    }

    //--------------------------------------------------
    // Methods
    //--------------------------------------------------

    private void initToolbar(Boolean homeEnabled) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.id_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(homeEnabled);
            getSupportActionBar().setTitle(R.string.activity_details__title);
        }
    }

    private void getExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mImageUrl = extras.getString(Globals.IMAGE_URL_EXTRA);
            mProductName = extras.getString(Globals.PRODUCT_NAME_EXTRA);
            mQuantityAvailable = extras.getString(Globals.QUANTITY_AVAILABLE_EXTRA);
            mPrice = extras.getString(Globals.PRICE_EXTRA);
        }
    }

    private void setLayout() {
        mOrderMoreLinearLayout = (LinearLayout)findViewById(R.id.id_activity_details__order_more_linear_layout);
        mOrderMoreLinearLayout.setOnClickListener(this);

        mUrlImageView = (ImageView)findViewById(R.id.id_activity_details__image_view);
        Glide.with(mActivity).load(mImageUrl).into(mUrlImageView);

        mNameTextView = (TextView)findViewById(R.id.id_activity_details__name_text_view);
        mNameTextView.setText(mProductName);

        mQuantityAvailableTextView = (TextView)findViewById(R.id.id_activity_details__quantity_text_view);
        mQuantityAvailableTextView.setText(mQuantityAvailable);

        mPriceTextView = (TextView)findViewById(R.id.id_activity_details__price_text_view);
        mPriceTextView.setText("US$" + mPrice);
    }

    //--------------------------------------------------
    // View.OnClickListener
    //--------------------------------------------------

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_activity_details__order_more_linear_layout:
                String text = "I would like to ask for more " + mProductName + ", please.";
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", EMAIL, null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Order more " + mProductName);
                emailIntent.putExtra(Intent.EXTRA_TEXT, text);
                startActivity(Intent.createChooser(emailIntent, "Send email"));
                break;
        }
    }
}