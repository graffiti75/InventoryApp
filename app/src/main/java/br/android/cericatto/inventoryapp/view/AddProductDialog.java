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
        final EditText nameEditText = (EditText)findViewById(R.id.id_activity_add_product__product_name_edit_text);
        final EditText urlEditText = (EditText)findViewById(R.id.id_activity_add_product__image_url_edit_text);
        final EditText priceEditText = (EditText)findViewById(R.id.id_activity_add_product__price_edit_text);

        Button addProductButton = (Button)findViewById(R.id.id_activity_add_product__button);
        addProductButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String name = nameEditText.getText().toString();
                String url = urlEditText.getText().toString();
                String price = priceEditText.getText().toString();
                Boolean nameEmpty = Utils.isEmpty(name);
                Boolean urlEmpty = Utils.isEmpty(url);
                Boolean priceEmpty = Utils.isEmpty(price);
                Boolean priceIsDouble = true;
                // Checks if the price is double.
                try {
                    Double.parseDouble(price);
                } catch (NumberFormatException e) {
                    priceIsDouble = false;
                }
                if (nameEmpty || urlEmpty || priceEmpty || !priceIsDouble) {
                    Toast.makeText(mActivity, "Please don't add empty values.", Toast.LENGTH_LONG).show();
                } else if (!priceIsDouble) {
                    Toast.makeText(mActivity, "The price must be in decimal format.", Toast.LENGTH_LONG).show();
                } else {
                    // Update database.
                    InventoryProvider database = DatabaseUtils.openDatabase(mActivity);
                    Integer id = ContentManager.getInstance().getInventoryList().size() + 1;
                    Inventory inventory = new Inventory(id, Double.valueOf(price), 0, url, name);
                    DatabaseUtils.insertInventory(mActivity, inventory);
                    List<Inventory> list = DatabaseUtils.getInventoryList(mActivity);
                    DatabaseUtils.closeDatabase(database);

                    // Update adapter.
                    MainActivity activity = (MainActivity)mActivity;
                    activity.updateAdapter(list);

                    // Dismiss dialog.
                    dismiss();
                }
            }
        });
    }
}