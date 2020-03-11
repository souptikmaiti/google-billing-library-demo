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

public class SubscriptionAdapter extends RecyclerView.Adapter<SubscriptionAdapter.SubscriptionHolder> {

    List<SkuDetails> skuDetailsList;
    SubscriptionClickListener subscriptionClickListener;

    public SubscriptionAdapter(List<SkuDetails> skuDetailsList, SubscriptionClickListener subscriptionClickListener) {
        this.skuDetailsList = skuDetailsList;
        this.subscriptionClickListener = subscriptionClickListener;
    }

    @NonNull
    @Override
    public SubscriptionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subscription, parent, false);
        return new SubscriptionHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubscriptionHolder holder, int position) {
        holder.bind(skuDetailsList.get(position));
    }

    @Override
    public int getItemCount() {
        return skuDetailsList.size();
    }

    public class SubscriptionHolder extends RecyclerView.ViewHolder{
        TextView tvPlan, tvPrice, tvDuration;
        public SubscriptionHolder(@NonNull View itemView) {
            super(itemView);
            tvPlan = itemView.findViewById(R.id.tv_plan);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvDuration = itemView.findViewById(R.id.tv_duration);
        }

        void bind(final SkuDetails details){
            tvPlan.setText(details.getTitle());
            tvPrice.setText(details.getPrice());
            tvDuration.setText(details.getSubscriptionPeriod());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    subscriptionClickListener.onSubscriptionClick(details);
                }
            });
        }
    }
}
