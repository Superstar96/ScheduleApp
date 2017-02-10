package schedule.star.com.schedule;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    private ListView m_listView;
    private AlarmAdapter m_alarmAdapter;

    private void init() //MainActivity'de kullanıcının ayarladığı saatleri Bundle'dan al ve Özel olarak oluşturduğun Adaptöre ver ve Adaptörü ListView'a bağla.
    {
        m_listView = (ListView)this.findViewById(R.id.LISTACTIVITY_LISTVIEW_ALARMLIST);
        Intent intent = this.getIntent();

        Bundle bundle = intent.getBundleExtra("BUNDLE");

        ArrayList<Alarm> alarms = new ArrayList<>();

        alarms = (ArrayList<Alarm>) bundle.getSerializable("LIST");

        m_alarmAdapter = new AlarmAdapter(this, alarms);

        m_listView.setAdapter(m_alarmAdapter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        this.init();
    }
}
