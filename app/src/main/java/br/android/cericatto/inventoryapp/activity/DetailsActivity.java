package br.android.cericatto.inventoryapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import br.android.cericatto.inventoryapp.R;
import br.android.cericatto.inventoryapp.database.DatabaseUtils;
import br.android.cericatto.inventoryapp.database.InventoryProvider;
import br.android.cericatto.inventoryapp.model.Inventory;
import br.android.cericatto.inventoryapp.utils.Globals;
import br.android.cericatto.inventoryapp.utils.Utils;
import br.android.cericatto.inventoryapp.view.DeleteProductDialog;
import br.android.cericatto.inventoryapp.view.Navigation;
import br.android.cericatto.inventoryapp.view.TypeQuantityDialog;

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

    private LinearLayout mIncreaseQuantityLinearLayout;
    private LinearLayout mDecreaseQuantityLinearLayout;
    private LinearLayout mDeleteProductLinearLayout;
    private LinearLayout mOrderMoreLinearLayout;

    private TextView mTypeQuantityTextView;
    private ImageView mUrlImageView;
    private TextView mNameTextView;
    private TextView mQuantityAvailableTextView;
    private TextView mPriceTextView;

    /**
     * Extras.
     */

    private Integer mId;
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
            mId = extras.getInt(Globals.ID_EXTRA);
            mImageUrl = extras.getString(Globals.IMAGE_URL_EXTRA);
            mProductName = extras.getString(Globals.PRODUCT_NAME_EXTRA);
            mQuantityAvailable = extras.getString(Globals.QUANTITY_AVAILABLE_EXTRA);
            mPrice = extras.getString(Globals.PRICE_EXTRA);
        }
    }

    private void setLayout() {
        mIncreaseQuantityLinearLayout = (LinearLayout)findViewById(R.id.id_activity_details__increase_quantity_linear_layout);
        mIncreaseQuantityLinearLayout.setOnClickListener(this);

        mTypeQuantityTextView = (TextView)findViewById(R.id.id_activity_details__type_quantity_text_view);
        mTypeQuantityTextView.setOnClickListener(this);

        mDecreaseQuantityLinearLayout = (LinearLayout)findViewById(R.id.id_activity_details__decrease_quantity_linear_layout);
        mDecreaseQuantityLinearLayout.setOnClickListener(this);

        mOrderMoreLinearLayout = (LinearLayout)findViewById(R.id.id_activity_details__order_more_linear_layout);
        mOrderMoreLinearLayout.setOnClickListener(this);

        mDeleteProductLinearLayout = (LinearLayout)findViewById(R.id.id_activity_details__delete_product_linear_layout);
        mDeleteProductLinearLayout.setOnClickListener(this);

        mUrlImageView = (ImageView)findViewById(R.id.id_activity_details__image_view);
        Glide.with(mActivity).load(mImageUrl).into(mUrlImageView);

        mNameTextView = (TextView)findViewById(R.id.id_activity_details__name_text_view);
        mNameTextView.setText(mProductName);

        mQuantityAvailableTextView = (TextView)findViewById(R.id.id_activity_details__quantity_text_view);
        mQuantityAvailableTextView.setText(mQuantityAvailable);

        mPriceTextView = (TextView)findViewById(R.id.id_activity_details__price_text_view);
        mPriceTextView.setText("US$" + mPrice);
    }

    private void sendEmail() {
        String text = getString(R.string.activity_details__ask_more) + mProductName
            + getString(R.string.activity_details__please);
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO,
            Uri.fromParts(getString(R.string.activity_details__mailto), EMAIL, null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.activity_details__email_order_more)
            + mProductName);
        emailIntent.putExtra(Intent.EXTRA_TEXT, text);
        startActivity(Intent.createChooser(emailIntent, getString(R.string.activity_details__send_email)));
    }

    private void deleteProduct() {
        DeleteProductDialog dialog = new DeleteProductDialog(mActivity, mId);
        Utils.callBackgroundDialog(mActivity, dialog);
    }

    private void modifyQuantity(boolean add) {
        // Updates database.
        InventoryProvider database = DatabaseUtils.openDatabase(mActivity);
        Inventory current = DatabaseUtils.getInventory(mActivity, mId);
        if (add) {
            current.setQuantityAvailable(current.getQuantityAvailable() + 1);
        } else {
            current.setQuantityAvailable(current.getQuantityAvailable() - 1);
        }
        DatabaseUtils.updateInventory(mActivity, current);
        DatabaseUtils.closeDatabase(database);

        // Updates product info.
        mQuantityAvailableTextView.setText(current.getQuantityAvailable().toString());
    }

    private void typeQuantity() {
        TypeQuantityDialog dialog = new TypeQuantityDialog(mActivity, mId);
        Utils.callBackgroundDialog(mActivity, dialog);
    }

    public void updateQuantity(Integer quantity) {
        mQuantityAvailableTextView.setText(quantity.toString());
    }

    //--------------------------------------------------
    // View.OnClickListener
    //--------------------------------------------------

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_activity_details__increase_quantity_linear_layout:
                modifyQuantity(true);
                break;
            case R.id.id_activity_details__type_quantity_text_view:
                typeQuantity();
                break;
            case R.id.id_activity_details__decrease_quantity_linear_layout:
                modifyQuantity(false);
                break;
            case R.id.id_activity_details__order_more_linear_layout:
                sendEmail();
                break;
            case R.id.id_activity_details__delete_product_linear_layout:
                deleteProduct();
                break;
        }
    }
}