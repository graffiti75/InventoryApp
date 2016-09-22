package br.android.cericatto.inventoryapp.activity;

import android.app.Activity;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.android.cericatto.inventoryapp.R;
import br.android.cericatto.inventoryapp.adapter.InventoryAdapter;
import br.android.cericatto.inventoryapp.model.Inventory;

/**
 * MainActivity.java.
 *
 * @author Rodrigo Cericatto
 * @since Sep 12, 2016
 */
public class MainActivity extends AppCompatActivity {

    //--------------------------------------------------
    // Attributes
    //--------------------------------------------------

    private Activity mActivity = MainActivity.this;
    private RecyclerView mRecyclerView;
    private InventoryAdapter mAdapter;

    //--------------------------------------------------
    // Activity Life Cycle
    //--------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setLayout();
        setRecyclerView();
    }

    //--------------------------------------------------
    // Methods
    //--------------------------------------------------

    private void setLayout() {
        mRecyclerView = (RecyclerView)findViewById(R.id.id_activity_main__recycler_view);
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

        Inventory item = new Inventory(1, 1d, 25, "https://realfood.tesco.com/media/images/Orange-and-almond-srping-cake-hero-58d07750-0952-47eb-bc41-a1ef9b81c01a-0-472x310.jpg", "orange");
        list.add(item);
        item = new Inventory(2, 1.5d, 20, "http://www.nutraingredients-usa.com/var/plain_site/storage/images/publications/food-beverage-nutrition/nutraingredients-usa.com/research/study-finds-widespread-adulteration-of-grape-seed-extract/9510929-1-eng-GB/Study-finds-widespread-adulteration-of-grape-seed-extract_strict_xxl.jpg", "bunch of grapes");
        list.add(item);
        item = new Inventory(3, 1d, 30, "http://lymanorchards.com/files/7013/6725/1487/apples.jpg", "apple");
        list.add(item);
        item = new Inventory(4, 0.5d, 35, "https://www.organicfacts.net/wp-content/uploads/2013/06/Strawberry1.jpg", "strawberry");
        list.add(item);
        item = new Inventory(5, 0.5d, 45, "https://www.organicfacts.net/wp-content/uploads/2013/05/Banana3.jpg", "banana");
        list.add(item);
        item = new Inventory(6, 2.5d, 15, "http://weknowyourdreams.com/images/pineapple/pineapple-08.jpg", "pineapple");
        list.add(item);

        return list;
    }
}