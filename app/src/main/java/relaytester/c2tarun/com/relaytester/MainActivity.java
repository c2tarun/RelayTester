package relaytester.c2tarun.com.relaytester;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static final String BASE_URL = "http://192.168.0.13:9898/relay/%d/%s";
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RadioGroup relaySwitchGroup = (RadioGroup) findViewById(R.id.radioGroup);
        Button onButton = (Button) findViewById(R.id.onButton);
        Button offButton = (Button) findViewById(R.id.offButton);

        onButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performActionOnRelaySwitch(relaySwitchGroup.getCheckedRadioButtonId(), "on");
            }
        });

        offButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performActionOnRelaySwitch(relaySwitchGroup.getCheckedRadioButtonId(), "off");
            }
        });

    }

    private void performActionOnRelaySwitch(int selectedRadioButtonId, String action) {
        int switchId = 0;

        switch(selectedRadioButtonId) {
            case R.id.radioButton1:
                switchId = 1;
                break;
            case R.id.radioButton2:
                switchId = 2;
                break;
            case R.id.radioButton3:
                switchId = 3;
                break;
            case R.id.radioButton4:
                switchId = 4;
                break;
        }

        String actionUrl = String.format(BASE_URL, switchId, action);
        new RelayCallAsyncTask().execute(actionUrl);

    }

    private class RelayCallAsyncTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {

            String actionUrl = params[0];
            try {
                URL url = new URL(actionUrl);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.getInputStream();
            } catch (MalformedURLException e) {
                Log.d(TAG, e.getMessage());
            } catch (IOException e) {
                Log.d(TAG, e.getMessage());
            }
            return null;
        }
    }
}
