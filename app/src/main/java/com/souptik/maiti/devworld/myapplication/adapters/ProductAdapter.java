package com.souptik.maiti.devworld.myapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.billingclient.api.SkuDetails;
import com.souptik.maiti.devworld.myapplication.R;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> {
    List<SkuDetails> skuDetailsList;
    ProductClickListener productClickListener;

    public ProductAdapter(List<SkuDetails> skuDetailsList, ProductClickListener productClickListener) {
        this.skuDetailsList = skuDetailsList;
        this.productClickListener = productClickListener;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, final int position) {
        holder.bind(skuDetailsList.get(position));
    }

    @Override
    public int getItemCount() {
        return skuDetailsList.size();
    }

    public class ProductHolder extends RecyclerView.ViewHolder{
        TextView tvName, tvPrice;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPrice = itemView.findViewById(R.id.tv_price);
        }

        void bind(final SkuDetails skuDetails){
            tvName.setText(skuDetails.getTitle());
            tvPrice.setText("Price: " + skuDetails.getPrice());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    productClickListener.onProductClick(skuDetails);
                }
            });
        }
    }
}
