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
import br.android.cericatto.inventoryapp.activity.MainActivity;
import br.android.cericatto.inventoryapp.database.DatabaseUtils;
import br.android.cericatto.inventoryapp.database.InventoryProvider;
import br.android.cericatto.inventoryapp.model.Inventory;
import br.android.cericatto.inventoryapp.utils.Globals;

/**
 * SaleProductDialog.java.
 *
 * @author Rodrigo Cericatto
 * @since September 22, 2016
 */
public class SaleProductDialog extends Dialog {

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

    public SaleProductDialog(Activity activity, Integer inventoryId) {
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
        setContentView(R.layout.dialog_sale_product);

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
        Button yesButton = (Button)findViewById(R.id.id_dialog_sale_product__yes_button);
        yesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                updateDatabase();
                updateAdapter();
                dismiss();
            }
        });

        Button noButton = (Button)findViewById(R.id.id_dialog_sale_product__no_button);
        noButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    private void updateDatabase() {
        InventoryProvider database = DatabaseUtils.openDatabase(mActivity);
        Inventory current = DatabaseUtils.getInventory(mActivity, mInventoryId);
        Integer id = current.getId();
        Double price = current.getPrice();
        Integer newQuantity = current.getQuantityAvailable() - 1;
        if (newQuantity <= 0) {
            newQuantity = 0;
        }
        String picture = current.getPicture();
        String name = current.getProductName();
        Inventory newInventory = new Inventory(id, price, newQuantity, picture, name);
        Boolean success = DatabaseUtils.updateInventory(mActivity, newInventory);
        if (!success) {
            Toast.makeText(mActivity, R.string.database_error, Toast.LENGTH_LONG).show();
        }
        DatabaseUtils.closeDatabase(database);
    }

    private void updateAdapter() {
        MainActivity activity = (MainActivity)mActivity;
        activity.updateAdapter();
    }
}