package com.idehub.Billing;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.SkuDetails;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.anjlab.android.iab.v3.PurchaseData;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InAppBillingBridge extends ReactContextBaseJavaModule {
    ReactApplicationContext _reactContext;
    String LICENSE_KEY = null;
    BillingProcessor bp;
    Boolean mShortCircuit = false;
    static final String LOG_TAG = "rnbilling";


    @Override
    public String getName() {
        return "InAppBillingBridge";
    }


    public InAppBillingBridge(ReactApplicationContext reactContext, String licenseKey) {
        super(reactContext);
        _reactContext = reactContext;
        LICENSE_KEY = licenseKey;
    }

    public InAppBillingBridge(ReactApplicationContext reactContext) {
        super(reactContext);
        _reactContext = reactContext;
        int keyResourceId = _reactContext
                .getResources()
                .getIdentifier("RNB_GOOGLE_PLAY_LICENSE_KEY", "string", _reactContext.getPackageName());
        LICENSE_KEY = _reactContext.getString(keyResourceId);
    }

    @ReactMethod
    public void open(final String productId, final Promise promise){
        getIAP(productId).open(promise);
    }

    @ReactMethod
    public void close(final String productId, final Promise promise){
        getIAP(productId).close(promise);
        removeIAP(productId);
    }

    @ReactMethod
    public void loadOwnedPurchasesFromGoogle(final String productId, final Promise promise){
        getIAP(productId).loadOwnedPurchasesFromGoogle(promise);
    }

    @ReactMethod
    public void purchase(final String productId, final String developerPayload, final Promise promise){
        getIAP(productId).purchase(productId, developerPayload, getCurrentActivity(), promise);
    }

    @ReactMethod
    public void consumePurchase(final String productId, final Promise promise) {
        getIAP(productId).consumePurchase(productId, promise);
    }

    @ReactMethod
    public void subscribe(final String productId, final String developerPayload, final Promise promise){
        getIAP(productId).subscribe(productId, developerPayload, getCurrentActivity(), promise);
    }

    @ReactMethod
    public void updateSubscription(final ReadableArray oldProductIds, final String productId, final String developerPayload, final Promise promise){
        getIAP(productId).updateSubscription(oldProductIds, productId, developerPayload, getCurrentActivity(), promise);
    }

    @ReactMethod
    public void isSubscribed(final String productId, final Promise promise){
        getIAP(productId).isSubscribed(productId, promise);
    }

    @ReactMethod
    public void isPurchased(final String productId, final Promise promise){
        getIAP(productId).isPurchased(productId, promise);
    }

    @ReactMethod
    public void isOneTimePurchaseSupported(final String productId, final Promise promise){
        getIAP(productId).isOneTimePurchaseSupported(promise);
    }

    @ReactMethod
    public void isValidTransactionDetails(final String productId, final Promise promise) {
        getIAP(productId).isValidTransactionDetails(productId, promise);
    }

    @ReactMethod
    public void listOwnedProducts(final String productId, final Promise promise){
        getIAP(productId).listOwnedProducts(promise);
    }

    @ReactMethod
    public void listOwnedSubscriptions(final String productId, final Promise promise){
        getIAP(productId).listOwnedSubscriptions(promise);
    }

    @ReactMethod
    public void getProductDetails(final String productId, final ReadableArray productIds, final Promise promise) {
        getIAP(productId).getProductDetails(productIds, promise);
    }

    @ReactMethod
    public void getSubscriptionDetails(final String productId, final ReadableArray productIds, final Promise promise) {
        getIAP(productId).getSubscriptionDetails(productIds, promise);
    }

    @ReactMethod
    public void getPurchaseTransactionDetails(final String productId, final Promise promise) {
        getIAP(productId).getPurchaseTransactionDetails(productId, promise);
    }

    @ReactMethod
    public void getSubscriptionTransactionDetails(final String productId, final Promise promise) {
        getIAP(productId).getSubscriptionTransactionDetails(productId, promise);
    }

    @ReactMethod
    public void shortCircuitPurchaseFlow(final String productId, final Boolean enable) {
        getIAP(productId).shortCircuitPurchaseFlow(enable);
    }

    HashMap<String, InAppBilling> mIAPCache = new HashMap<>();

    synchronized InAppBilling getIAP(String key) {
        if (mIAPCache.containsKey(key)) {
            return mIAPCache.get(key);
        }
        InAppBilling iap = new InAppBilling(_reactContext, LICENSE_KEY);
        mIAPCache.put(key, iap);
        return iap;
    }

    synchronized void removeIAP(String key) {
        mIAPCache.remove(key);
    }
}
