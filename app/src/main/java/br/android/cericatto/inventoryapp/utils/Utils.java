package br.android.cericatto.inventoryapp.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.WindowManager;

import br.android.cericatto.inventoryapp.view.BlackBackgroundDialog;

/**
 * Utils.java.
 *
 * @author Rodrigo Cericatto
 * @since Sep 12, 2016
 */
public class Utils {

    //--------------------------------------------------
    // Dialog Methods
    //--------------------------------------------------

    public static void callBackgroundDialog(Activity context, Dialog inputDialog) {
        final BlackBackgroundDialog backgroundDialog = new BlackBackgroundDialog(context);
        backgroundDialog.show();
        inputDialog.show();
        inputDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            public void onDismiss(final DialogInterface dialog) {
                backgroundDialog.dismiss();
            }
        });
    }

    public static void setFullScreen(Dialog dialog) {
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    //--------------------------------------------------
    // String Methods
    //--------------------------------------------------

    public static Boolean isEmpty(String text) {
        Boolean result = true;
        Boolean isNull = (text == null);
        if (!isNull) {
            Boolean isZeroLength = (text.length() <= 0);
            Boolean isEmpty = (text.equals(""));
            Boolean contentOfTextIsLiteralNull = (text.equals("null"));
            result = isNull || isZeroLength || isEmpty || contentOfTextIsLiteralNull;
        }
        return result;
    }

    //--------------------------------------------------
    // Preference Methods
    //--------------------------------------------------

    public static void setPreference(Context context, String key, Boolean firstTime) {
        // Gets the preference.
        SharedPreferences preference = context.getSharedPreferences(Globals.GLOBAL_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();

        // Saves the preference.
        editor.putBoolean(key, firstTime);
        editor.commit();
    }

    public static Boolean getPreference(Context context, String key) {
        SharedPreferences preference = context.getSharedPreferences(Globals.GLOBAL_PREFERENCE, Context.MODE_PRIVATE);
        Boolean data = preference.getBoolean(key, true);
        return data;
    }
}