package br.android.cericatto.inventoryapp.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import br.android.cericatto.inventoryapp.R;
import br.android.cericatto.inventoryapp.model.Inventory;

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

        Glide.with(mActivity).load(item.getPicture()).into(holder.productImageView);

        holder.nameTextView.setText(item.getProductName());
        holder.quantityTextView.setText(item.getQuantityAvailable().toString());
        holder.priceTextView.setText(item.getPrice().toString());
        holder.supplierTextView.setText("John's Farm");

        holder.saleProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mActivity, "Sale Product", Toast.LENGTH_SHORT).show();
            }
        });
        holder.orderMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mActivity, "Order More", Toast.LENGTH_SHORT).show();
            }
        });
        holder.deleteProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mActivity, "Delete Product", Toast.LENGTH_SHORT).show();
            }
        });
        holder.increaseQuantityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mActivity, "Increase Quantity", Toast.LENGTH_SHORT).show();
            }
        });
        holder.decreaseQuantityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mActivity, "Decrease Quantity", Toast.LENGTH_SHORT).show();
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
    // View Holder
    //--------------------------------------------------

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView productImageView;
        public TextView nameTextView;
        public TextView quantityTextView;
        public TextView priceTextView;
        public TextView supplierTextView;

        public Button saleProductButton;
        public Button orderMoreButton;
        public Button deleteProductButton;
        public Button increaseQuantityButton;
        public Button decreaseQuantityButton;

        public ViewHolder(View view) {
            super(view);
            productImageView = (ImageView) view.findViewById(R.id.id_adapter_inventory__image_view);
            nameTextView = (TextView) view.findViewById(R.id.id_adapter_inventory__name_text_view);
            quantityTextView = (TextView) view.findViewById(R.id.id_adapter_inventory__quantity_text_view);
            priceTextView = (TextView) view.findViewById(R.id.id_adapter_inventory__price_text_view);
            supplierTextView = (TextView) view.findViewById(R.id.id_adapter_inventory__supplier_text_view);

            saleProductButton = (Button) view.findViewById(R.id.id_adapter_inventory__sale_product_button);
            orderMoreButton = (Button) view.findViewById(R.id.id_adapter_inventory__order_more_button);
            deleteProductButton = (Button) view.findViewById(R.id.id_adapter_inventory__delete_product_button);
            increaseQuantityButton = (Button) view.findViewById(R.id.id_adapter_inventory__increase_quantity_button);
            decreaseQuantityButton = (Button) view.findViewById(R.id.id_adapter_inventory__decrease_quantity_button);
        }
    }
}