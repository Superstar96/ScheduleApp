package schedule.star.com.schedule;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.widget.Toast;

/**
 * Created by SUPERSTAR on 6.02.2017.
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) //Alarmın zamanı gelince yapılacak işleri yapan BroadcastReceiver Sınıfının onReceive Methodu
    {
        Toast.makeText(context, "***ALARM ÇALIYOR***", Toast.LENGTH_LONG).show();
        Vibrator vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(5000);
    }
}
