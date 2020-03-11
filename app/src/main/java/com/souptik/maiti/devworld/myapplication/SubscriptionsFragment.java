package com.souptik.maiti.devworld.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.souptik.maiti.devworld.myapplication.adapters.SubscriptionAdapter;
import com.souptik.maiti.devworld.myapplication.adapters.SubscriptionClickListener;
import com.souptik.maiti.devworld.myapplication.util.Constants;

import java.util.Arrays;
import java.util.List;


public class SubscriptionsFragment extends Fragment implements PurchasesUpdatedListener, SubscriptionClickListener {
    RecyclerView rvSubscription;
    BillingClient billingClient;

    public SubscriptionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_subscriptions, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvSubscription = getActivity().findViewById(R.id.rv_subscriptions);
        setUpBillingClient();
    }

    private void setUpBillingClient() {
        billingClient = BillingClient.newBuilder(getContext())
                .enablePendingPurchases()
                .setListener(this)
                .build();

        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(BillingResult billingResult) {
                if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK){
                    Toast.makeText(getContext(), "Billing client Connected", Toast.LENGTH_SHORT).show();
                    fetchSkuAsync();
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                Toast.makeText(getContext(), "Billing client Disconnected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchSkuAsync() {
        if(billingClient.isReady()){

            SkuDetailsParams detailsParams = SkuDetailsParams.newBuilder()
                    .setSkusList(Arrays.asList(Constants.SUBSCRIPTION_1, Constants.SUBSCRIPTION_2,
                            Constants.SUBSCRIPTION_3, Constants.SUBSCRIPTION_4))
                    .setType(BillingClient.SkuType.SUBS)
                    .build();

            billingClient.querySkuDetailsAsync(detailsParams, new SkuDetailsResponseListener() {
                @Override
                public void onSkuDetailsResponse(BillingResult billingResult, List<SkuDetails> list) {
                    if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && list != null){
                        setRecyclerView(list);
                    }
                }
            });
        }
    }

    private void setRecyclerView(List<SkuDetails> list) {
        rvSubscription.setLayoutManager(new LinearLayoutManager(getContext()));
        rvSubscription.setHasFixedSize(true);
        SubscriptionAdapter adapter = new SubscriptionAdapter(list, this);
        rvSubscription.setAdapter(adapter);
    }

    @Override
    public void onSubscriptionClick(SkuDetails skuDetails) {
        // launch billing flow if user click any subscription item
        // run on ui thread
        BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                .setSkuDetails(skuDetails)
                .build();
        billingClient.launchBillingFlow(getActivity(), flowParams);
    }

    @Override
    public void onPurchasesUpdated(BillingResult billingResult, @Nullable List<Purchase> purchaseList) {
        // if user tap on BUY, we get data here
        if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK){
            for(Purchase purchase: purchaseList){
                handlePurchase(purchase);
            }
        }else if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED){
            Toast.makeText(getContext(), "User Cancelled purchase", Toast.LENGTH_SHORT).show();
        }else if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED){
            Toast.makeText(getContext(), "Already owned", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getContext(), "purchase failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void handlePurchase(Purchase purchase) {
        Toast.makeText(getContext(), "You have purchased " + purchase.getSku(), Toast.LENGTH_SHORT).show();
    }
}
