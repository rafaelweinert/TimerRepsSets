package timer.repssets.ui.onreps;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import timer.repssets.R;


public class OnrepsFragment extends Fragment {

    private OnrepsViewModel onrepsViewModel;

    private ImageButton addRestOnreps;
    private ImageButton removeRestOnreps;
    private ImageButton addRepsOnreps;
    private ImageButton removeRepsOnreps;
    private Button buttonGoOnreps;

    private EditText textRepsOnreps;
    private EditText textRestSecondsOnreps;
    private EditText textRestMinutesOnreps;

    //longclicklistener
    private boolean mAutoIncrement = false;
    private boolean mAutoDecrement = false;
    private Handler handler = new Handler();
    private Runnable runnable;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        onrepsViewModel = ViewModelProviders.of(this).get(OnrepsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_onreps, container, false);

        //Button declaration

        addRestOnreps = root.findViewById(R.id.addRestOnreps);
        addRepsOnreps = root.findViewById(R.id.addRepsOnreps);
        removeRestOnreps = root.findViewById(R.id.removeRestOnreps);
        removeRepsOnreps = root.findViewById(R.id.removeRepsOnreps);
        buttonGoOnreps = root.findViewById(R.id.buttonGoOnreps);

        //TextField declaration

        textRepsOnreps = root.findViewById(R.id.textRepsOnreps);
        textRestSecondsOnreps = root.findViewById(R.id.textRestSecondsOnreps);
        textRestMinutesOnreps = root.findViewById(R.id.textRestMinutesOnreps);

        //ClickListener for Buttons

        addRepsOnreps.setOnClickListener((view) -> {
            try{
                Integer help = Integer.valueOf(textRepsOnreps.getText().toString());
                help++;
                textRepsOnreps.setText(help.toString());
            }catch (Exception e) {textRepsOnreps.setText("0");}
        });

        addRepsOnreps.setOnLongClickListener((view) -> {
            try{
                   runnable = new Runnable() {
                       @Override
                       public void run() {
                           if(!addRepsOnreps.isPressed()) return;
                           Integer help = Integer.valueOf(textRepsOnreps.getText().toString());
                           help++;
                           textRepsOnreps.setText(help.toString());
                           handler.postDelayed(runnable, 50);
                       }
                   };


            }catch (Exception e) {textRepsOnreps.setText("0"); return false;}
            handler.postDelayed(runnable, 50);
            return true;
        });

        removeRepsOnreps.setOnClickListener((view) -> {
            try{
                Integer help = Integer.valueOf(textRepsOnreps.getText().toString());
                help--;
                if(help == -1)
                    help = 0;
                textRepsOnreps.setText(help.toString());
            }catch (Exception e) {textRepsOnreps.setText("0");}
        });

        removeRepsOnreps.setOnLongClickListener((view) -> {
            try{
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        if(!removeRepsOnreps.isPressed()) return;
                        Integer help = Integer.valueOf(textRepsOnreps.getText().toString());
                        help--;
                        if(help == -1)
                            help = 0;
                        textRepsOnreps.setText(help.toString());
                        handler.postDelayed(runnable, 50);
                    }
                };


            }catch (Exception e) {textRepsOnreps.setText("0"); return false;}
            handler.postDelayed(runnable, 50);
            return true;
        });

        addRestOnreps.setOnClickListener((view) -> {
            try{
                Integer help = Integer.valueOf(textRestSecondsOnreps.getText().toString());
                if(help > 59){
                    Integer help2 = Integer.valueOf(textRestMinutesOnreps.getText().toString());
                    int remainder = help % 60;
                    int quotient = help / 60;
                    help2 = help2 + quotient;
                    textRestMinutesOnreps.setText(help2.toString());
                    remainder++;
                    textRestSecondsOnreps.setText((((Integer) remainder).toString()));
                    help = remainder;
                }
                else{
                    help++;
                    textRestSecondsOnreps.setText(help.toString());
                }

            }catch (Exception e) {textRepsOnreps.setText("0");}
        });

        addRestOnreps.setOnLongClickListener((view) -> {
            try{
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        if(!addRestOnreps.isPressed()) return;
                        Integer help = Integer.valueOf(textRestSecondsOnreps.getText().toString());
                        if(help > 59){
                            Integer help2 = Integer.valueOf(textRestMinutesOnreps.getText().toString());
                            int remainder = help % 60;
                            int quotient = help / 60;
                            help2 = help2 + quotient;
                            textRestMinutesOnreps.setText(help2.toString());
                            remainder++;
                            textRestSecondsOnreps.setText((((Integer) remainder).toString()));
                            help = remainder;
                        }
                        else{
                            help++;
                            textRestSecondsOnreps.setText(help.toString());
                        }
                        handler.postDelayed(runnable, 50);
                    }
                };


            }catch (Exception e) {textRepsOnreps.setText("0"); return false;}
            handler.postDelayed(runnable, 50);
            return true;
        });

        removeRestOnreps.setOnClickListener((view) -> {
            try{
                Integer help = Integer.valueOf(textRestSecondsOnreps.getText().toString());
                if(help > 60){
                    Integer help2 = Integer.valueOf(textRestMinutesOnreps.getText().toString());
                    int remainder = help % 60;
                    int quotient = help / 60;
                    help2 = help2 + quotient;
                    textRestMinutesOnreps.setText(help2.toString());
                    remainder--;
                    if(remainder < 0)
                        remainder = 59;
                    textRestSecondsOnreps.setText(((Integer)remainder).toString());
                    help = remainder;
                }
                else{
                    help--;
                    if(help < 0) {
                        help = 59;
                        Integer help2 = Integer.valueOf(textRestMinutesOnreps.getText().toString());
                        help2--;
                        if(help2 < 0)
                            help2 = 0;
                        textRestMinutesOnreps.setText(help2.toString());
                    }
                    textRestSecondsOnreps.setText(help.toString());
                }
            }catch (Exception e) {textRepsOnreps.setText("0");}
        });

        removeRestOnreps.setOnLongClickListener((view) -> {
            try{
                runnable = () -> {
                    if(!removeRestOnreps.isPressed()) return;
                    Integer help = Integer.valueOf(textRestSecondsOnreps.getText().toString());
                    if(help > 60){
                        Integer help2 = Integer.valueOf(textRestMinutesOnreps.getText().toString());
                        int remainder = help % 60;
                        int quotient = help / 60;
                        help2 = help2 + quotient;
                        textRestMinutesOnreps.setText(help2.toString());
                        remainder--;
                        if(remainder < 0)
                            remainder = 59;
                        textRestSecondsOnreps.setText(((Integer)remainder).toString());
                        help = remainder;
                    }
                    else{
                        help--;
                        if(help < 0) {
                            help = 59;
                            Integer help2 = Integer.valueOf(textRestMinutesOnreps.getText().toString());
                            help2--;
                            if(help2 < 0)
                                help2 = 0;
                            textRestMinutesOnreps.setText(help2.toString());
                        }
                        textRestSecondsOnreps.setText(help.toString());
                    }
                    handler.postDelayed(runnable, 50);
                };
            }catch (Exception e) {textRepsOnreps.setText("0"); return false;}
            handler.postDelayed(runnable, 50);
            return true;
        });

        buttonGoOnreps.setOnClickListener((view) -> {
            try {
                int reps = Integer.valueOf(textRepsOnreps.getText().toString());
                int restMinutes = Integer.valueOf(textRestMinutesOnreps.getText().toString());
                int restSeconds = Integer.valueOf(textRestSecondsOnreps.getText().toString());

                if(reps < 1){
                    buttonGoOnreps.setBackgroundColor(getResources().getColor(R.color.red));
                    return;
                }

                Fragment onrepsFragmentCD = new OnrepsCDFragment(reps, restMinutes, restSeconds);
                replaceFragment(onrepsFragmentCD);
            }catch (NullPointerException e) {}
        });
        return root;
    }

    public void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
