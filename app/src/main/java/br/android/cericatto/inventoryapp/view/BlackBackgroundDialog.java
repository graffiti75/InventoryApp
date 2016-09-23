package br.android.cericatto.inventoryapp.view;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import br.android.cericatto.inventoryapp.R;

/**
 * BlackBackgroundDialog.java.
 *
 * @author Rodrigo Cericatto
 * @since September 22, 2016
 */
public class BlackBackgroundDialog extends Dialog {

    //--------------------------------------------------
    // Attributes
    //--------------------------------------------------

    private Activity mActivity;

    //--------------------------------------------------
    // Constructor
    //--------------------------------------------------

    public BlackBackgroundDialog(Activity activity) {
        super(activity);
        mActivity = activity;
    }

    //--------------------------------------------------
    // Dialog Life Cycle
    //--------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_black_background);

        setFullScreen();
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
}