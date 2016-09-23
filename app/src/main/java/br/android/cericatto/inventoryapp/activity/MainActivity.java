package br.android.cericatto.inventoryapp.activity;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import br.android.cericatto.inventoryapp.R;
import br.android.cericatto.inventoryapp.adapter.InventoryAdapter;
import br.android.cericatto.inventoryapp.model.Inventory;
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

    private Activity mActivity = MainActivity.this;
    private RecyclerView mRecyclerView;
    private Button mAddProductButton;
    private InventoryAdapter mAdapter;

    //--------------------------------------------------
    // Activity Life Cycle
    //--------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar(false);
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

    public void initToolbar(Boolean homeEnabled) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.id_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(homeEnabled);
            getSupportActionBar().setTitle(R.string.app_name);
        }
    }

    private void setLayout() {

        mRecyclerView = (RecyclerView)findViewById(R.id.id_activity_main__recycler_view);
        mAddProductButton = (Button)findViewById(R.id.id_activity_main__add_product_button);
        mAddProductButton.setOnClickListener(this);
    }

    private void setRecyclerView() {
        // Sets the recycler view.
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // Sets the adapter.
        List<Inventory> list = populateRecyclerView();
        mAdapter = new InventoryAdapter(mActivity, list);
        mRecyclerView.setAdapter(mAdapter);
    }

    private List<Inventory> populateRecyclerView() {
        List<Inventory> list = new ArrayList<>();

        Inventory item = new Inventory(1, 1d, 25, "https://realfood.tesco.com/media/images/Orange-and-almond-srping-cake-hero-58d07750-0952-47eb-bc41-a1ef9b81c01a-0-472x310.jpg", "Orange");
        list.add(item);
        item = new Inventory(2, 1.5d, 20, "http://www.nutraingredients-usa.com/var/plain_site/storage/images/publications/food-beverage-nutrition/nutraingredients-usa.com/research/study-finds-widespread-adulteration-of-grape-seed-extract/9510929-1-eng-GB/Study-finds-widespread-adulteration-of-grape-seed-extract_strict_xxl.jpg", "Bunch of grapes");
        list.add(item);
        item = new Inventory(3, 1d, 30, "http://lymanorchards.com/files/7013/6725/1487/apples.jpg", "Apple");
        list.add(item);
        item = new Inventory(4, 0.5d, 35, "https://www.organicfacts.net/wp-content/uploads/2013/06/Strawberry1.jpg", "Strawberry");
        list.add(item);
        item = new Inventory(5, 0.5d, 45, "https://www.organicfacts.net/wp-content/uploads/2013/05/Banana3.jpg", "Banana");
        list.add(item);
        item = new Inventory(6, 2.5d, 15, "http://weknowyourdreams.com/images/pineapple/pineapple-08.jpg", "Pineapple");
        list.add(item);
        item = new Inventory(7, 0.5d, 35, "http://weknowyourdreams.com/images/potato/potato-04.jpg", "Potato");
        list.add(item);
        item = new Inventory(8, 0.5d, 20, "http://assets.inhabitat.com/wp-content/blogs.dir/1/files/2012/06/red-tomato-meteorite.jpg", "Tomato");
        list.add(item);
        item = new Inventory(9, 0.75d, 30, "http://www.newfoxy.com/wp-content/uploads/2016/05/onions.jpg", "Onion");
        list.add(item);

        return list;
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