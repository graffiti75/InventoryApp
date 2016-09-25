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
import android.widget.Toast;

import br.android.cericatto.inventoryapp.R;
import br.android.cericatto.inventoryapp.activity.DetailsActivity;
import br.android.cericatto.inventoryapp.database.DatabaseUtils;
import br.android.cericatto.inventoryapp.database.InventoryProvider;
import br.android.cericatto.inventoryapp.utils.Globals;
import br.android.cericatto.inventoryapp.utils.Utils;

/**
 * SaleProductDialog.java.
 *
 * @author Rodrigo Cericatto
 * @since September 22, 2016
 */
public class DeleteProductDialog extends Dialog {

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

    public DeleteProductDialog(Activity activity, Integer inventoryId) {
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
        setContentView(R.layout.dialog_delete_product);

        Utils.setFullScreen(this);
        setLayout();
    }

    //--------------------------------------------------
    // Methods
    //--------------------------------------------------

    private void setLayout() {
        Button yesButton = (Button)findViewById(R.id.id_dialog_delete_product__yes_button);
        yesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                deleteProduct();
            }
        });

        Button noButton = (Button)findViewById(R.id.id_dialog_delete_product__no_button);
        noButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    private void deleteProduct() {
        // Deletes from database.
        InventoryProvider database = DatabaseUtils.openDatabase(mActivity);
        boolean success = DatabaseUtils.deleteInventory(mActivity, mInventoryId);
        if (!success) {
            Toast.makeText(mActivity, R.string.database_error, Toast.LENGTH_LONG).show();
        }
        DatabaseUtils.closeDatabase(database);

        // Closes activity.
        DetailsActivity activity = (DetailsActivity)mActivity;
        activity.closesActivity(this);
    }
}