package br.com.caelum.cadastro.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by joaofaro on 08/07/15.
 */
public class SMSReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Chegou SMS", Toast.LENGTH_LONG).show();
    }
}
