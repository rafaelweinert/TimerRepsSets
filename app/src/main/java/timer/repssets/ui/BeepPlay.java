package timer.repssets.ui;

import android.content.Context;
import android.media.MediaPlayer;
import timer.repssets.R;


public class BeepPlay {

    private Context context;
    private MediaPlayer shortBeep;
    private MediaPlayer longBeep;
    private Thread beepThread;


    public BeepPlay(Context context){
        this.context = context;
       shortBeep = MediaPlayer.create(context, R.raw.shortbeep);
       longBeep = MediaPlayer.create(context, R.raw.longbeep);
    }

    public void play(){
        beepThread = new Thread(){
            @Override
            public void run() {
                for(int i = 0; i<3; i++) {
                    shortBeep.start();
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                longBeep.start();
            }
        };
        beepThread.start();


    }


}
