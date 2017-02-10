package schedule.star.com.schedule;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by SUPERSTAR on 6.02.2017.
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) //Alarmın zamanı gelince yapılacak işleri yapan BroadcastReceiver Sınıfının onReceive Methodu
    {
        //"Time is " + intent.getStringExtra("TIME")
        Toast.makeText(context, "ALARM IS RINGING", Toast.LENGTH_LONG).show();
    }

}
