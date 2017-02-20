package schedule.star.com.schedule;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * Created by SUPERSTAR on 20.02.2017.
 */

public class FileReadWrite {
    private final String FILE_NAME = "Alarms.txt";
    private Context m_context;

    public FileReadWrite(Context context)
    {
        m_context = context;
    }

    public ArrayList<Alarm> getAlarms()
    {
        ArrayList<Alarm> list = new ArrayList<>();

        try(FileInputStream fis = m_context.openFileInput(FILE_NAME))
        {
            ObjectInputStream ois = new ObjectInputStream(fis);

            while (true) {
                Alarm alarm = (Alarm) ois.readObject();

                Intent intent = new Intent (m_context, AlarmReceiver.class);
                PendingIntent pending = PendingIntent.getBroadcast(m_context, alarm.getOrder(), intent, 0);

                alarm.setPendingIntent(pending);

                list.add(alarm);
            }
        }
        catch (FileNotFoundException ex) {
            Toast.makeText(m_context, "GetAlarms FileNotFoundException " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        catch (IOException ex) {
            Toast.makeText(m_context, "GetAlarms IOException " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        catch (ClassNotFoundException ex) {
            Toast.makeText(m_context, "GetAlarms ClassNotFoundException " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        catch (Exception ex) {
            Toast.makeText(m_context, "GetAlarms Exception " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        return list;
    }

    public void setAlarms()
    {
        ArrayList<Alarm> list = MainActivity.m_alarms;
        int index = 0;

        try(FileOutputStream fos = m_context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE))
        {
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            while (true) {
                Alarm alarm = list.get(index++);

                oos.writeObject(alarm);
            }
        }
        catch (FileNotFoundException ex) {
            Toast.makeText(m_context, "SetAlarms FileNotFoundException" + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        catch (IOException ex) {
            Toast.makeText(m_context, "SetAlarms IOException" + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        catch (Exception ex) {
            Toast.makeText(m_context, "SetAlarms Exception" + ex.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

}
