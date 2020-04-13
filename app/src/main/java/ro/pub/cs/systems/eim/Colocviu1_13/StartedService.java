package ro.pub.cs.systems.eim.Colocviu1_13;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StartedService extends Service {

    private ProcessingThread thr;
    private String toSend;

    public class ProcessingThread extends Thread {

        private Context context;

        public ProcessingThread(Context context) {
            this.context = context;
        }

        @Override
        public void run() {
            sleep();
            Intent intent = new Intent();
            intent.setAction("actiune2");
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            intent.putExtra("transmis", toSend + " : " +  dateFormat.format(date));
            context.sendBroadcast(intent);
        }

        private void sleep() {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }

    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return true;
    }

    @Override
    public void onRebind(Intent intent) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("My msg", "Got into service");
        toSend = (String)intent.getExtras().get("val");
        thr = new ProcessingThread(getApplicationContext());
        thr.start();



        return START_REDELIVER_INTENT;
    }

}
