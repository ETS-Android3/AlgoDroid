package com.droidevils.algodroid.utility;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;

public class AppUtility {

    public static boolean checkInternetConnection(Activity activity){
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        return true;
    }



}
