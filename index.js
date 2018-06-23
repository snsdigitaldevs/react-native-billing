"use strict";

const InAppBillingBridge = require("react-native").NativeModules
  .InAppBillingBridge;

class InAppBilling {
  static open(productId) {
    return InAppBillingBridge.open(productId);
  }

  static close(productId) {
    return InAppBillingBridge.close(productId);
  }

  static loadOwnedPurchasesFromGoogle(productId) {
    return InAppBillingBridge.loadOwnedPurchasesFromGoogle();
  }

  static purchase(productId, developerPayload = null) {
    return InAppBillingBridge.purchase(productId, developerPayload);
  }

  static consumePurchase(productId) {
    return InAppBillingBridge.consumePurchase(productId);
  }

  static subscribe(productId, developerPayload = null) {
    return InAppBillingBridge.subscribe(productId, developerPayload);
  }

  static updateSubscription(oldProductIds, productId, developerPayload = null) {
    return InAppBillingBridge.updateSubscription(oldProductIds, productId, developerPayload);
  }

  static isSubscribed(productId) {
    return InAppBillingBridge.isSubscribed(productId);
  }

  static isPurchased(productId) {
    return InAppBillingBridge.isPurchased(productId);
  }

  static isOneTimePurchaseSupported(productId) {
    return InAppBillingBridge.isOneTimePurchaseSupported(productId);
  }

  static isValidTransactionDetails(productId) {
    return InAppBillingBridge.isValidTransactionDetails(productId);
  }

  static listOwnedProducts(productId) {
    return InAppBillingBridge.listOwnedProducts(productId);
  }

  static listOwnedSubscriptions(productId) {
    return InAppBillingBridge.listOwnedSubscriptions(productId);
  }

  static getProductDetails(productId) {
    return InAppBillingBridge.getProductDetails(productId, [productId])
      .then(arr => {
        if (arr != null && arr.length > 0) {
          return Promise.resolve(arr[0]);
        } else {
          return Promise.reject("Could not find details.");
        }
      })
      .catch(error => {
        return Promise.reject(error);
      });
  }

  static getPurchaseTransactionDetails(productId) {
    return InAppBillingBridge.getPurchaseTransactionDetails(productId);
  }

  static getSubscriptionTransactionDetails(productId) {
    return InAppBillingBridge.getSubscriptionTransactionDetails(productId);
  }

  static getProductDetailsArray(productId, productIds) {
    return InAppBillingBridge.getProductDetails(productId, productIds);
  }

  static getSubscriptionDetails(productId) {
    return InAppBillingBridge.getSubscriptionDetails(productId, [productId])
      .then(arr => {
        if (arr != null && arr.length > 0) {
          return Promise.resolve(arr[0]);
        } else {
          return Promise.reject("Could not find details.");
        }
      })
      .catch(error => {
        return Promise.reject(error);
      });
  }

  static getSubscriptionDetailsArray(productId, productIds) {
    return InAppBillingBridge.getSubscriptionDetails(productId, productIds);
  }

  static shortCircuitPurchaseFlow(enable) {
    InAppBillingBridge.shortCircuitPurchaseFlow(enable);
  }
}

module.exports = InAppBilling;
