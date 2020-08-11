package timer.repssets.ui.onreps;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import timer.repssets.R;

import timer.repssets.ui.BeepPlay;
import static java.lang.String.valueOf;

public class OnrepsCDFragment extends Fragment {

    int reps;
    int restMinutes;
    int restSeconds;



    //TextViews

    private TextView textViewOnrepscd;
    private TextView textViewOnrepscdMinutes;
    private TextView textViewOnrepscdSeconds;
    private TextView textViewOnrepscdRepsleft;
    private TextView textViewResthelpOnrepscd;
    private Button buttonDoneOnrepscd;

    //Threads for CountDown

    private Runnable runnableRest;
    private Thread threadWork;
    private Handler handler = new Handler();

    //Beep-Sound

    private BeepPlay beepPlay;


    public OnrepsCDFragment(int reps, int restMinutes, int restSeconds) {
       this.reps = reps;
       this.restMinutes = restMinutes;
       this.restSeconds = restSeconds;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_onrepscd, container, false);


        //declaration of TextViews

        textViewOnrepscd = root.findViewById(R.id.textViewOnrepscd);
        textViewOnrepscdMinutes = root.findViewById(R.id.textViewOnrepscdMinutes);
        textViewOnrepscdSeconds = root.findViewById(R.id.textViewOnrepscdSeconds);
        textViewOnrepscdRepsleft = root.findViewById(R.id.textViewOnrepscdRepsleft);
        buttonDoneOnrepscd = root.findViewById(R.id.buttonDoneOnrepscd);
        textViewResthelpOnrepscd = root.findViewById(R.id.textViewResthelpOnrepscd);

        textViewOnrepscd.setText(R.string.work);
        textViewOnrepscdRepsleft.setText("Intervals left: " + reps);
        textViewOnrepscdMinutes.setText(valueOf(restMinutes));
        textViewOnrepscdSeconds.setText(valueOf(restSeconds));

        beepPlay = new BeepPlay(getContext());
        
        buttonDoneOnrepscd.setOnClickListener((view) -> {
            
            if(reps == 1){
                textViewOnrepscd.setText(R.string.workout_done);
                textViewOnrepscdRepsleft.setText("Intervals left: " + --reps);
                textViewOnrepscdMinutes.setText("");
                textViewOnrepscdSeconds.setText("");
                buttonDoneOnrepscd.setClickable(false);
                buttonDoneOnrepscd.setTextColor(getResources().getColor(R.color.white));
                textViewResthelpOnrepscd.setText("");
            }else{
                rest();
            }
        });
        return root;
    }


    // mit Thread versuchen!
    private void rest() {
        buttonDoneOnrepscd.setTextColor(getResources().getColor(R.color.white));
        textViewOnrepscd.setText("REST!");
        textViewOnrepscdRepsleft.setText("Intervals left: " + --reps);

        runnableRest = new Runnable() {
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
                    textViewOnrepscdMinutes.setText(valueOf(min));
                    textViewOnrepscdSeconds.setText(valueOf(sec));
                    buttonDoneOnrepscd.setClickable(false);
                }
                handler.postDelayed(runnableRest, 1000);
            }
        };
        handler.postDelayed(runnableRest, 50);
    }

    private void work() {

        buttonDoneOnrepscd.setTextColor(getResources().getColor(R.color.red));
        threadWork = new Thread(){
            @Override
            public void run() {
                textViewOnrepscd.setText(R.string.work);
                textViewOnrepscdMinutes.setText(valueOf(restMinutes));
                textViewOnrepscdSeconds.setText(valueOf(restSeconds));
                buttonDoneOnrepscd.setClickable(true);
            }
        };
        threadWork.start();
    }

}