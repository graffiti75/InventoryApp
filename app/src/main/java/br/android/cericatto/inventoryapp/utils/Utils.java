package br.android.cericatto.inventoryapp.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;

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

    public static Boolean hasConnection(Activity activity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        Boolean hasConnection = connectivityManager.getActiveNetworkInfo() != null;
        return hasConnection;
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
}