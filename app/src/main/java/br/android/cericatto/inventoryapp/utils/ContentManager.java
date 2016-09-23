package br.android.cericatto.inventoryapp.utils;

import java.util.ArrayList;
import java.util.List;

import br.android.cericatto.inventoryapp.model.Inventory;

/**
 * ContentManager.java class.
 *
 * @author Rodrigo Cericatto
 * @since September 23, 2016
 */
public class ContentManager {

    //----------------------------------------------
    // Statics
    //----------------------------------------------

    private static ContentManager sInstance = null;

    //----------------------------------------------
    // Attributes
    //----------------------------------------------

    private List<Inventory> mInventories = new ArrayList<>();

    //----------------------------------------------
    // Constructor
    //----------------------------------------------

    /**
     * Private constructor.
     */
    public ContentManager() {
    }

    /**
     * @return The singleton instance of ContentManager.
     */
    public static ContentManager getInstance() {
        if (sInstance == null) {
            sInstance = new ContentManager();
        }
        return sInstance;
    }

    //----------------------------------------------
    // Methods
    //----------------------------------------------

    public List<Inventory> getInventoryList() {
        return mInventories;
    }

    public void setInventoryList(List<Inventory> bitmaps) {
        mInventories = bitmaps;
    }
}