package com.official.pb.satya;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Basefunctions {
    public static boolean status=false;
    public static boolean checkinternetconnection(Context ctx)
    {
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }
}
