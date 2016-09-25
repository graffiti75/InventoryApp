package br.android.cericatto.inventoryapp.view;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import br.android.cericatto.inventoryapp.R;
import br.android.cericatto.inventoryapp.activity.DetailsActivity;
import br.android.cericatto.inventoryapp.database.DatabaseUtils;
import br.android.cericatto.inventoryapp.database.InventoryProvider;
import br.android.cericatto.inventoryapp.model.Inventory;
import br.android.cericatto.inventoryapp.utils.ContentManager;
import br.android.cericatto.inventoryapp.utils.Globals;

/**
 * AddProductDialog.java.
 *
 * @author Rodrigo Cericatto
 * @since September 24, 2016
 */
public class TypeQuantityDialog extends Dialog {

    //--------------------------------------------------
    // Attributes
    //--------------------------------------------------

    /**
     * Context.
     */

    private Activity mActivity;

    /**
     * Database.
     */

    private Integer mInventoryId;

    //--------------------------------------------------
    // Constructor
    //--------------------------------------------------

    public TypeQuantityDialog(Activity activity, Integer inventoryId) {
        super(activity, Globals.DIALOG_THEME);
        mActivity = activity;
        mInventoryId = inventoryId;
    }

    //--------------------------------------------------
    // Dialog Life Cycle
    //--------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_type_quantity);

        setFullScreen();
        setLayout();
    }

    //--------------------------------------------------
    // Methods
    //--------------------------------------------------

    private void setFullScreen() {
        Window window = getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void setLayout() {
        final EditText editText = (EditText)findViewById(R.id.id_dialog_type_quantity__edit_text);

        Button button = (Button)findViewById(R.id.id_dialog_type_quantity__button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String quantity = editText.getText().toString();
                updateDatabase(quantity);
            }
        });
    }

    private void updateDatabase(String quantity) {
        // Checks if the quantity is of type Integer.
        Boolean quantityIsInteger = true;
        Integer newQuantity = 0;
        boolean success = false;
        try {
            newQuantity = Integer.parseInt(quantity);
        } catch (NumberFormatException e) {
            quantityIsInteger = false;
        }

        // Checks if we can update the database and activity.
        if (quantityIsInteger) {
            InventoryProvider database = DatabaseUtils.openDatabase(mActivity);
            Inventory current = DatabaseUtils.getInventory(mActivity, mInventoryId);
            current.setQuantityAvailable(newQuantity);
            success = DatabaseUtils.updateInventory(mActivity, current);
            if (!success) {
                Toast.makeText(mActivity, R.string.database_error, Toast.LENGTH_LONG).show();
            }
            DatabaseUtils.closeDatabase(database);
        }

        // Update activity.
        if (quantityIsInteger && success) {
            DetailsActivity activity = (DetailsActivity)mActivity;
            activity.updateQuantity(newQuantity);
            dismiss();
        }
    }
}