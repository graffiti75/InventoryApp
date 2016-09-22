package br.android.cericatto.inventoryapp.database;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;

import br.android.cericatto.inventoryapp.model.Inventory;
import br.android.cericatto.inventoryapp.utils.Utils;

/**
 * DatabaseUtils.java.
 *
 * @author Rodrigo Cericatto
 * @since Sep 10, 2016
 */
public class DatabaseUtils {

    //--------------------------------------------------
    // Low Level Database Methods
    //--------------------------------------------------

    /**
     * Adds a new Inventory record.
     */
    public static Boolean insertInventory(Context context, Inventory inventory) {
        ContentValues values = getContentValues(inventory);
        Uri uri = context.getContentResolver().insert(InventoryProvider.CONTENT_URI, values);

        Boolean result = false;
        if (!Utils.isEmpty(uri.toString())) {
            result = true;
        }
        return result;
    }

    /**
     * Deletes an Inventory.
     */
    public static Boolean deleteInventory(Context context, Integer id) {
        Uri friends = Uri.parse(InventoryProvider.CONTENT_PROVIDER_URL);
        Boolean result = false;

        String where = InventoryProvider.ID + "=?";
        String _id = String.valueOf(id);
        String[] whereArgs = new String[] { _id };
        int count = context.getContentResolver().delete(friends, where, whereArgs);

        if (count > 0) result = true;

        return result;
    }

    /**
     * Gets an Inventory.
     */
    public static Inventory getInventory(Context context, Integer _id) {
        Uri inventories = Uri.parse(InventoryProvider.CONTENT_PROVIDER_URL);
        Inventory inventory;
        Cursor cursor = context.getContentResolver().query(inventories, null, InventoryProvider.ID + " = " + _id, null, null);
        if (!cursor.moveToFirst()) {
            inventory = null;
        } else {
            do {
                inventory = getInventoryFromCursor(cursor);
            } while (cursor.moveToNext());
        }
        return inventory;
    }

    /**
     * Gets all the Inventory.
     */
    public static ArrayList<Inventory> getInventoryList(Context context) {
        Uri inventories = Uri.parse(InventoryProvider.CONTENT_PROVIDER_URL);
        ArrayList<Inventory> list = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(inventories, null, null, null, null);
        if (!cursor.moveToFirst()) {
            list = null;
        } else {
            Inventory inventory;
            do {
                inventory = getInventoryFromCursor(cursor);
                list.add(inventory);
            } while (cursor.moveToNext());
        }
        return list;
    }

    /**
     * Updates a Inventory.
     */
    public static Boolean updateInventory(Context context, Inventory inventory) {
        ContentValues values = getContentValues(inventory);
        int rowsAffected = context.getContentResolver().update(InventoryProvider.CONTENT_URI, values, null, null);

        Boolean result = false;
        if (rowsAffected > 0) {
            result = true;
        }
        return result;
    }

    //--------------------------------------------------
    // Cursor Methods
    //--------------------------------------------------

    private static Inventory getInventoryFromCursor(Cursor cursor) {
        Inventory inventory = new Inventory();

        Integer id = cursor.getInt(cursor.getColumnIndex(InventoryProvider.ID));
        Double price = cursor.getDouble(cursor.getColumnIndex(InventoryProvider.PRICE));
        Integer quantityAvailable = cursor.getInt(cursor.getColumnIndex(InventoryProvider.QUANTITY_AVAILABLE));
        String picture = cursor.getString(cursor.getColumnIndex(InventoryProvider.PICTURE));
        String name = cursor.getString(cursor.getColumnIndex(InventoryProvider.PRODUCT_NAME));

        inventory.setId(id);
        inventory.setId(id);
        inventory.setPrice(price);
        inventory.setQuantityAvailable(quantityAvailable);
        inventory.setPicture(picture);
        inventory.setProductName(name);

        return inventory;
    }

    private static ContentValues getContentValues(Inventory inventory) {
        Integer id = inventory.getId();
        Double price = inventory.getPrice();
        Integer quantityAvailable = inventory.getQuantityAvailable();
        String picture = inventory.getPicture();
        String name = inventory.getProductName();

        ContentValues values = new ContentValues();
        values.put(InventoryProvider.ID, id);
        values.put(InventoryProvider.PRICE, price);
        values.put(InventoryProvider.QUANTITY_AVAILABLE, quantityAvailable);
        values.put(InventoryProvider.PICTURE, picture);
        values.put(InventoryProvider.PRODUCT_NAME, name);

        return values;
    }

    //--------------------------------------------------
    // High Level Database Methods
    //--------------------------------------------------

    public static InventoryProvider openDatabase(Activity activity) {
        InventoryProvider database = new InventoryProvider();
        database.openDatabase(activity);
        return database;
    }

    public static void closeDatabase(InventoryProvider database) {
        database.closeDatabase();
    }

    public static Inventory getInventory(Activity activity) {
        openDatabase(activity);
        Inventory inventory = getInventory(activity, 1);
        return inventory;
    }
}