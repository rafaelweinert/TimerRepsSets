package timer.repssets.ui.ontime;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import timer.repssets.R;
import timer.repssets.ui.BeepPlay;

import static java.lang.String.valueOf;

public class OntimeCDFragment extends Fragment {

    int reps;
    int restMinutes;
    int restSeconds;
    int workMinutes;
    int workSeconds;

    //TextViews

    private TextView textViewOntimecd;
    private TextView textViewOntimecdMinutes;
    private TextView textViewOntimecdSeconds;
    private TextView textViewOntimecdRepsleft;
    private TextView textViewResthelpOntimecd;

    //Threads for CountDown

    private Thread threadRest;
    private Thread threadWork;
    private Handler handler = new Handler();

    //Beep-Sound

    private BeepPlay beepPlay;


    public OntimeCDFragment(int reps, int restMinutes, int restSeconds, int workMinutes, int workSeconds) {
        this.reps = reps;
        this.restMinutes = restMinutes;
        this.restSeconds = restSeconds;
        this.workMinutes = workMinutes;
        this.workSeconds = workSeconds;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_ontimecd, container, false);


        //declaration of TextViews

        textViewOntimecd = root.findViewById(R.id.textViewOntimecd);
        textViewOntimecdMinutes = root.findViewById(R.id.textViewOntimecdMinutes);
        textViewOntimecdSeconds = root.findViewById(R.id.textViewOntimecdSeconds);
        textViewOntimecdRepsleft = root.findViewById(R.id.textViewOntimecdRepsleft);

        textViewResthelpOntimecd = root.findViewById(R.id.textViewResthelpOntimecd);

        //textViewOntimecd.setText(R.string.work);
        textViewOntimecdRepsleft.setText("Intervals left: " + reps);
        //textViewOntimecdMinutes.setText(valueOf(workMinutes));
        //textViewOntimecdSeconds.setText(valueOf(workSeconds));

        beepPlay = new BeepPlay(getContext());

        restwork();

        return root;
    }


   private void rest() {

       if(reps == 1)
       {
           textViewOntimecd.setText(R.string.workout_done);
           textViewOntimecdRepsleft.setText("Intervals left: " + --reps);
           textViewOntimecdMinutes.setText("");
           textViewOntimecdSeconds.setText("");
           textViewResthelpOntimecd.setText("");
           return;
       }

        textViewOntimecd.setText("REST!");

      textViewOntimecdMinutes.setText(valueOf(restMinutes));
      textViewOntimecdSeconds.setText(valueOf(restSeconds));

        threadRest = new Thread() {
            int min = restMinutes;
            int sec = restSeconds;

            @Override
            public void run() {
                if(min == 0 && sec == 0){
                    work();
                    return;
                }
                else{
                    sec--;
                    if(min == 0 && sec == 3)
                        beepPlay.play();
                    if (sec < 0) {
                        sec = 59;
                        min--;
                    }
                    textViewOntimecdMinutes.setText(valueOf(min));
                    textViewOntimecdSeconds.setText(valueOf(sec));
                }
                handler.postDelayed(threadWork, 1000);
            }
        };

       handler.postDelayed(threadWork, 1000);
    }

    private void work() {
        textViewOntimecd.setText("WORK!");

        textViewOntimecdMinutes.setText(valueOf(workMinutes));
        textViewOntimecdSeconds.setText(valueOf(workSeconds));

        threadWork = new Thread() {
            int min = workMinutes;
            int sec = workSeconds;

            @Override
            public void run() {
                if (min == 0 && sec == 0) {
                    textViewOntimecdRepsleft.setText("Intervals left: " + --reps);
                    rest();
                    return;
                } else {
                    sec--;
                    if (min == 0 && sec == 3)
                        beepPlay.play();
                    if (sec < 0) {
                        sec = 59;
                        min--;
                    }
                    textViewOntimecdMinutes.setText(valueOf(min));
                    textViewOntimecdSeconds.setText(valueOf(sec));
                }
                handler.postDelayed(threadWork, 1000);
                }
            };

        handler.postDelayed(threadWork, 0);
    }

    private void restwork(){

        boolean verfuegbar = false;

        threadWork = new Thread() {
            int min = workMinutes;
            int sec = workSeconds;

            @Override
            synchronized public  void run() {
                boolean v1 = verfuegbar;
                if(!v1){
                    try {
                        wait();
                    }catch (InterruptedException e){}
                }
                v1 = false;
                notify();

                textViewOntimecd.setText("WORK!");
                if (min == 0 && sec == 0) {
                    textViewOntimecdRepsleft.setText("Intervals left: " + --reps);
                    try {
                        notify();
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return;
                } else {
                    sec--;
                    if (min == 0 && sec == 3)
                        beepPlay.play();
                    if (sec < 0) {
                        sec = 59;
                        min--;
                    }
                    textViewOntimecdMinutes.setText(valueOf(min));
                    textViewOntimecdSeconds.setText(valueOf(sec));
                }
                handler.postDelayed(threadWork, 1000);
            }
        };

        threadRest = new Thread() {
            int min = restMinutes;
            int sec = restSeconds;

            @Override
            synchronized public void run() {
                //boolean v2 =
                textViewOntimecd.setText("REST!");
                if(min == 0 && sec == 0){
                    try {
                        notify();
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                else{
                    sec--;
                    if(min == 0 && sec == 3)
                        beepPlay.play();
                    if (sec < 0) {
                        sec = 59;
                        min--;
                    }
                    textViewOntimecdMinutes.setText(valueOf(min));
                    textViewOntimecdSeconds.setText(valueOf(sec));
                }
                handler.postDelayed(threadWork, 1000);
            }
        };

        threadWork.start();
        threadRest.start();

    }
    }

