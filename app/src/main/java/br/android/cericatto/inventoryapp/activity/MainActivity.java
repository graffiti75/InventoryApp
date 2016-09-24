package br.android.cericatto.inventoryapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.android.cericatto.inventoryapp.R;
import br.android.cericatto.inventoryapp.adapter.InventoryAdapter;
import br.android.cericatto.inventoryapp.database.DataItems;
import br.android.cericatto.inventoryapp.database.DatabaseUtils;
import br.android.cericatto.inventoryapp.database.InventoryProvider;
import br.android.cericatto.inventoryapp.model.Inventory;
import br.android.cericatto.inventoryapp.utils.ContentManager;
import br.android.cericatto.inventoryapp.utils.Globals;
import br.android.cericatto.inventoryapp.utils.Utils;
import br.android.cericatto.inventoryapp.view.AddProductDialog;
import br.android.cericatto.inventoryapp.view.Navigation;

/**
 * MainActivity.java.
 *
 * @author Rodrigo Cericatto
 * @since Sep 12, 2016
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //--------------------------------------------------
    // Attributes
    //--------------------------------------------------

    /**
     * Contexts.
     */

    private Activity mActivity = MainActivity.this;

    /**
     * Layout.
     */

    private RecyclerView mRecyclerView;
    private TextView mEmptyTextView;
    private Button mAddProductButton;

    /**
     * Adapter.
     */

    private InventoryAdapter mAdapter;

    //--------------------------------------------------
    // Activity Life Cycle
    //--------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setLayout();
        setRecyclerView();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Navigation.animate(this, Navigation.Animation.BACK);
    }

    //--------------------------------------------------
    // Methods
    //--------------------------------------------------

    private void initToolbar(Boolean homeEnabled) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.id_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(homeEnabled);
            getSupportActionBar().setTitle(R.string.app_name);
        }
    }

    private void setLayout() {
        mRecyclerView = (RecyclerView)findViewById(R.id.id_activity_main__recycler_view);
        mEmptyTextView = (TextView)findViewById(R.id.id_activity_main__empty_text_view);

        mAddProductButton = (Button)findViewById(R.id.id_activity_main__add_product_button);
        mAddProductButton.setOnClickListener(this);
    }

    private void setRecyclerView() {
        // Sets the recycler view.
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        List<Inventory> list = populateRecyclerView();
        if (list == null || list.size() < 1) {
            // Sets the empty TextView.
            mRecyclerView.setVisibility(View.GONE);
            mEmptyTextView.setVisibility(View.VISIBLE);
        } else {
            // Sets the adapter.
            mRecyclerView.setVisibility(View.VISIBLE);
            mEmptyTextView.setVisibility(View.GONE);
            mAdapter = new InventoryAdapter(mActivity, list);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    private List<Inventory> populateRecyclerView() {
        boolean firstTime = Utils.getPreference(mActivity, Globals.CONTROL);
        InventoryProvider database = DatabaseUtils.openDatabase(mActivity);
        List<Inventory> list = checkFirstTime(firstTime);
        DatabaseUtils.closeDatabase(database);
        ContentManager.getInstance().setInventoryList(list);
        return list;
    }

    private List<Inventory> checkFirstTime(boolean firstTime) {
        List<Inventory> list = new ArrayList<>();
        if (firstTime) {
            Utils.setPreference(mActivity, Globals.CONTROL, false);
            // Adds Inventory' to database.
            boolean success = addListToDatabase();
            if (!success) {
                Toast.makeText(mActivity, R.string.database_error, Toast.LENGTH_LONG).show();
            }
        } else {
            // Check if it exists an Inventory into the database.
            Inventory inventory = DatabaseUtils.getInventory(mActivity, 1);
            if (inventory != null) {
                list = DatabaseUtils.getInventoryList(mActivity);
            }
        }
        return list;
    }

    private boolean addListToDatabase() {
        List<Inventory> list = new ArrayList<>();
        boolean success = true;
        for (int i = 1; (i <= DataItems.NAME.length) && (success); i++) {
            Inventory item = new Inventory(i, DataItems.PRICE[i - 1], DataItems.QUANTITY[i - 1],
                DataItems.URL[i - 1], DataItems.NAME[i - 1]);
            success = DatabaseUtils.insertInventory(mActivity, item);
            list.add(item);
        }
        return success;
    }

    public void updateAdapter() {
        InventoryProvider database = DatabaseUtils.openDatabase(mActivity);
        List<Inventory> list = DatabaseUtils.getInventoryList(mActivity);
        DatabaseUtils.closeDatabase(database);
        mAdapter.setFilter(list);
    }

    public void updateAdapter(List<Inventory> list) {
        mAdapter.setFilter(list);
    }

    //--------------------------------------------------
    // View.OnClickListener
    //--------------------------------------------------

    @Override
    public void onClick(View view) {
        AddProductDialog dialog = new AddProductDialog(mActivity);
        Utils.callBackgroundDialog(mActivity, dialog);
    }
}