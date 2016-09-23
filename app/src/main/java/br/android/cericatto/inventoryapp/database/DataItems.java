package br.android.cericatto.inventoryapp.database;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import br.android.cericatto.inventoryapp.model.Inventory;

/**
 * MainActivity.java.
 *
 * @author Rodrigo Cericatto
 * @since Sep 13, 2016
 */
public class DataItems {

    //--------------------------------------------------
    // Constants
    //--------------------------------------------------

    public static final Double[] PRICE = new Double[] {
        1d, 1.5d, 1d, 0.5d, 0.5d, 2.5d, 0.5d, 0.5d, 0.75d
    };

    public static final Integer[] QUANTITY = new Integer[] {
        25, 20, 30, 35, 45, 15, 35, 20, 30
    };

    public static final String[] NAME = new String[] {
        "Orange", "Bunch of grapes", "Apple", "Strawberry", "Banana",
        "Pineapple", "Potato", "Tomato", "Onion"
    };

    public static final String[] URL = new String[] {
        "https://realfood.tesco.com/media/images/Orange-and-almond-srping-cake-hero-58d07750-0952-" +
            "47eb-bc41-a1ef9b81c01a-0-472x310.jpg",
        "http://www.nutraingredients-usa.com/var/plain_site/storage/images/publications/food-" +
            "beverage-nutrition/nutraingredients-usa.com/research/study-finds-widespread-" +
            "adulteration-of-grape-seed-extract/9510929-1-eng-GB/Study-finds-widespread-" +
            "adulteration-of-grape-seed-extract_strict_xxl.jpg",
        "http://lymanorchards.com/files/7013/6725/1487/apples.jpg",
        "https://www.organicfacts.net/wp-content/uploads/2013/06/Strawberry1.jpg",
        "https://www.organicfacts.net/wp-content/uploads/2013/05/Banana3.jpg",
        "http://weknowyourdreams.com/images/pineapple/pineapple-08.jpg",
        "http://weknowyourdreams.com/images/potato/potato-04.jpg",
        "http://assets.inhabitat.com/wp-content/blogs.dir/1/files/2012/06/red-tomato-meteorite.jpg",
        "http://www.newfoxy.com/wp-content/uploads/2016/05/onions.jpg"
    };
}