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
import br.android.cericatto.inventoryapp.utils.ContentManager;
import br.android.cericatto.inventoryapp.utils.Globals;
import br.android.cericatto.inventoryapp.utils.Utils;

/**
 * AddProductDialog.java.
 *
 * @author Rodrigo Cericatto
 * @since September 22, 2016
 */
public class AddProductDialog extends Dialog {

    //--------------------------------------------------
    // Attributes
    //--------------------------------------------------

    /**
     * Context.
     */

    private Activity mActivity;

    //--------------------------------------------------
    // Constructor
    //--------------------------------------------------

    public AddProductDialog(Activity activity) {
        super(activity, Globals.DIALOG_THEME);
        mActivity = activity;
    }

    //--------------------------------------------------
    // Dialog Life Cycle
    //--------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_add_product);

        Utils.setFullScreen(this);
        setLayout();
    }

    //--------------------------------------------------
    // Methods
    //--------------------------------------------------

    private void setLayout() {
        final EditText nameEditText = (EditText)findViewById(R.id.id_dialog_add_product__product_name_edit_text);
        final EditText urlEditText = (EditText)findViewById(R.id.id_dialog_add_product__image_url_edit_text);
        final EditText priceEditText = (EditText)findViewById(R.id.id_dialog_add_product__price_edit_text);

        Button addProductButton = (Button)findViewById(R.id.id_dialog_add_product__button);
        addProductButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String name = nameEditText.getText().toString();
                String url = urlEditText.getText().toString();
                String price = priceEditText.getText().toString();
                addProduct(name, url, price);
            }
        });
    }

    private List<Inventory> updateDatabase(String name, String url, String price) {
        InventoryProvider database = DatabaseUtils.openDatabase(mActivity);
        Integer id = ContentManager.getInstance().getInventoryList().size() + 1;
        Inventory inventory = new Inventory(id, Double.valueOf(price), 0, url, name);
        DatabaseUtils.insertInventory(mActivity, inventory);
        List<Inventory> list = DatabaseUtils.getInventoryList(mActivity);
        DatabaseUtils.closeDatabase(database);
        return list;
    }

    private void addProduct(String name, String url, String price) {
        // Verifications.
        Boolean nameEmpty = Utils.isEmpty(name);
        Boolean urlEmpty = Utils.isEmpty(url);
        Boolean priceEmpty = Utils.isEmpty(price);
        Double priceDouble = 0d;

        // Checks if the price is of type Double.
        Boolean priceIsDouble = true;
        try {
            priceDouble = Double.parseDouble(price);
        } catch (NumberFormatException e) {
            priceIsDouble = false;
        }

        // Checks if we can update the database and adapter.
        if (nameEmpty || urlEmpty || priceEmpty) {
            Toast.makeText(mActivity, R.string.dialog_add_product__empty_values, Toast.LENGTH_LONG).show();
        } else if (!priceIsDouble) {
            Toast.makeText(mActivity, R.string.dialog_add_product__price_decimal, Toast.LENGTH_LONG).show();
        } else {
            Boolean priceNegative = (priceDouble < 0);
            if (priceNegative) {
                Toast.makeText(mActivity, R.string.dialog_add_product__price_negative, Toast.LENGTH_LONG).show();
            } else {
                // Updates database and adapter.
                List<Inventory> list = updateDatabase(name, url, price);
                MainActivity activity = (MainActivity) mActivity;
                activity.updateAdapter(list);
                dismiss();
            }
        }
    }
}