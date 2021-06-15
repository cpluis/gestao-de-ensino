package com.br.gestao_de_ensino.Activity.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConfigInternet {

    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;
    private static final String URL1 = "http://192.168.100.10/createPost.php";
    private static final String URL2 = "http://192.168.100.10/delete.php";
    private static final String URL3 = "http://192.168.100.10/getAlunos.php";



    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }



    public static String getConnectivityStatusString(Context context) {
        int conn = ConfigInternet.getConnectivityStatus(context);
        String status = null;
        if (conn == ConfigInternet.TYPE_WIFI) {
            status = "Wifi habilitado";
        } else if (conn == ConfigInternet.TYPE_MOBILE) {
            status = "Telefone com os dados habilitado";
        } else if (conn == ConfigInternet.TYPE_NOT_CONNECTED) {
            status = "Não connected ha Internet";
        }
        return status;
    }

   public String metodoPost(){
        return URL1;
   }

    public String metodoDelete(){
        return URL2;
    }
}