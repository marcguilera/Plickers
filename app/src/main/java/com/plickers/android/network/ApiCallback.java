package com.plickers.android.network;

/**
 * This allows perform actions after getting
 * result of an api call. It usually would be
 * an abstract class of interface to force
 * implementation but I left it as a class
 * to allow for users to override the functions
 * they want and leave the ones they don't need
 * without implementing.
 */
public class ApiCallback {
    /**
     * Called when the api call has been successful.ç
     * @param response
     */
    public void onSuccess(Object response){}

    /**
     * Called when the call failed for any reson.
     */

    public void onFailed(){}

    /**
     * Called when the api wasn't reached
     * Automatically calls ApiCallback#onFailed
     */
    public void onTimeout(){
        onFailed();
    }

    /**
     * Called when the api responded with an error.
     * Automatically calls ApiCallback#onFailed
     * @param errorCode
     */
    public void onError(int errorCode){
        onFailed();
    }
}
