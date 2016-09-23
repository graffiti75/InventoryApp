package br.android.cericatto.inventoryapp.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import br.android.cericatto.inventoryapp.R;
import br.android.cericatto.inventoryapp.activity.DetailsActivity;
import br.android.cericatto.inventoryapp.model.Inventory;
import br.android.cericatto.inventoryapp.utils.ActivityUtils;
import br.android.cericatto.inventoryapp.utils.Globals;
import br.android.cericatto.inventoryapp.utils.Utils;
import br.android.cericatto.inventoryapp.view.SaleProductDialog;

/**
 * InventoryAdapter.java.
 *
 * @author Rodrigo Cericatto
 * @since Sep 12, 2016
 */
public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.ViewHolder> {

    //--------------------------------------------------
    // Attributes
    //--------------------------------------------------

    private Activity mActivity;
    private List<Inventory> mItems;

    //--------------------------------------------------
    // Constructor
    //--------------------------------------------------

    public InventoryAdapter(Activity context, List<Inventory> items) {
        mActivity = context;
        mItems = items;
    }

    //--------------------------------------------------
    // Adapter Methods
    //--------------------------------------------------

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_inventory, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Inventory item = mItems.get(position);
        final String url = item.getPicture();
        final String productName = item.getProductName();
        final String quantityAvailable = item.getQuantityAvailable().toString();
        final String price = item.getPrice().toString();

        Glide.with(mActivity).load(url).into(holder.productImageView);
        holder.nameTextView.setText(productName);
        holder.quantityTextView.setText(quantityAvailable);

        holder.saleProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaleProductDialog dialog = new SaleProductDialog(mActivity);
                Utils.callBackgroundDialog(mActivity, dialog);
            }
        });

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] keys = new String[] { Globals.IMAGE_URL_EXTRA, Globals.PRODUCT_NAME_EXTRA,
                    Globals.QUANTITY_AVAILABLE_EXTRA, Globals.PRICE_EXTRA };
                Object[] values = new Object[] { url, productName, quantityAvailable, price };
                ActivityUtils.startActivityExtras(mActivity, DetailsActivity.class, keys, values);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mItems != null && mItems.size() > 0) {
            return mItems.size();
        }
        return 0;
    }

    //--------------------------------------------------
    // Other Methods
    //--------------------------------------------------

    public void setFilter(List<Inventory> list) {
        mItems = new ArrayList<>();
        mItems.addAll(list);
        notifyDataSetChanged();
    }

    //--------------------------------------------------
    // View Holder
    //--------------------------------------------------

    public class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout linearLayout;
        public ImageView productImageView;
        public TextView nameTextView;
        public TextView quantityTextView;
        public ImageView saleProductButton;

        public ViewHolder(View view) {
            super(view);
            linearLayout = (LinearLayout)view.findViewById(R.id.id_adapter_inventory__linear_layout);
            productImageView = (ImageView)view.findViewById(R.id.id_adapter_inventory__image_view);
            nameTextView = (TextView)view.findViewById(R.id.id_adapter_inventory__name_text_view);
            quantityTextView = (TextView)view.findViewById(R.id.id_adapter_inventory__quantity_text_view);
            saleProductButton = (ImageView)view.findViewById(R.id.id_adapter_inventory__sale_product_button);
        }
    }
}