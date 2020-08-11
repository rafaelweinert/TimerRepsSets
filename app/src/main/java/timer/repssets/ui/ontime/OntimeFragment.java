package timer.repssets.ui.ontime;

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
import timer.repssets.ui.onreps.OnrepsCDFragment;
import timer.repssets.ui.onreps.OnrepsViewModel;


public class OntimeFragment extends Fragment {

    private OntimeViewModel ontimeViewModel;

    private ImageButton addRestOntime;
    private ImageButton removeRestOntime;
    private ImageButton addWorkOntime;
    private ImageButton removeWorkOntime;
    private ImageButton addRepsOntime;
    private ImageButton removeRepsOntime;
    private Button buttonGoOntime;

    private EditText textRepsOntime;
    private EditText textRestSecondsOntime;
    private EditText textRestMinutesOntime;
    private EditText textWorkSecondsOntime;
    private EditText textWorkMinutesOntime;

    //longclicklistener
    private boolean mAutoIncrement = false;
    private boolean mAutoDecrement = false;
    private Handler handler = new Handler();
    private Runnable runnable;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ontimeViewModel = ViewModelProviders.of(this).get(OntimeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_ontime, container, false);

        //Button declaration

        addRestOntime = root.findViewById(R.id.addRestOntime);
        addRepsOntime = root.findViewById(R.id.addRepsOntime);
        removeRestOntime = root.findViewById(R.id.removeRestOntime);
        removeRepsOntime = root.findViewById(R.id.removeRepsOntime);
        addWorkOntime = root.findViewById(R.id.addWorkOntime);
        removeWorkOntime = root.findViewById(R.id.removeWorkOntime);
        buttonGoOntime = root.findViewById(R.id.buttonGoOntime);

        //TextField declaration

        textRepsOntime = root.findViewById(R.id.textRepsOntime);
        textRestSecondsOntime = root.findViewById(R.id.textRestSecondsOntime);
        textRestMinutesOntime = root.findViewById(R.id.textRestMinutesOntime);
        textWorkSecondsOntime = root.findViewById(R.id.textWorkSecondsOntime);
        textWorkMinutesOntime = root.findViewById(R.id.textWorkMinutesOntime);

        //ClickListener for Buttons

        addRepsOntime.setOnClickListener((view) -> {
            try{
                Integer help = Integer.valueOf(textRepsOntime.getText().toString());
                help++;
                textRepsOntime.setText(help.toString());
            }catch (Exception e) {textRepsOntime.setText("0");}
        });

        addRepsOntime.setOnLongClickListener((view) -> {
            try{
                   runnable = new Runnable() {
                       @Override
                       public void run() {
                           if(!addRepsOntime.isPressed()) return;
                           Integer help = Integer.valueOf(textRepsOntime.getText().toString());
                           help++;
                           textRepsOntime.setText(help.toString());
                           handler.postDelayed(runnable, 50);
                       }
                   };


            }catch (Exception e) {textRepsOntime.setText("0"); return false;}
            handler.postDelayed(runnable, 50);
            return true;
        });

        removeRepsOntime.setOnClickListener((view) -> {
            try{
                Integer help = Integer.valueOf(textRepsOntime.getText().toString());
                help--;
                if(help == -1)
                    help = 0;
                textRepsOntime.setText(help.toString());
            }catch (Exception e) {textRepsOntime.setText("0");}
        });

        removeRepsOntime.setOnLongClickListener((view) -> {
            try{
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        if(!removeRepsOntime.isPressed()) return;
                        Integer help = Integer.valueOf(textRepsOntime.getText().toString());
                        help--;
                        if(help == -1)
                            help = 0;
                        textRepsOntime.setText(help.toString());
                        handler.postDelayed(runnable, 50);
                    }
                };


            }catch (Exception e) {textRepsOntime.setText("0"); return false;}
            handler.postDelayed(runnable, 50);
            return true;
        });

        addRestOntime.setOnClickListener((view) -> {
            try{
                Integer help = Integer.valueOf(textRestSecondsOntime.getText().toString());
                if(help > 59){
                    Integer help2 = Integer.valueOf(textRestMinutesOntime.getText().toString());
                    int remainder = help % 60;
                    int quotient = help / 60;
                    help2 = help2 + quotient;
                    textRestMinutesOntime.setText(help2.toString());
                    remainder++;
                    textRestSecondsOntime.setText((((Integer) remainder).toString()));
                    help = remainder;
                }
                else{
                    help++;
                    textRestSecondsOntime.setText(help.toString());
                }

            }catch (Exception e) {textRepsOntime.setText("0");}
        });

        addRestOntime.setOnLongClickListener((view) -> {
            try{
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        if(!addRestOntime.isPressed()) return;
                        Integer help = Integer.valueOf(textRestSecondsOntime.getText().toString());
                        if(help > 59){
                            Integer help2 = Integer.valueOf(textRestMinutesOntime.getText().toString());
                            int remainder = help % 60;
                            int quotient = help / 60;
                            help2 = help2 + quotient;
                            textRestMinutesOntime.setText(help2.toString());
                            remainder++;
                            textRestSecondsOntime.setText((((Integer) remainder).toString()));
                            help = remainder;
                        }
                        else{
                            help++;
                            textRestSecondsOntime.setText(help.toString());
                        }
                        handler.postDelayed(runnable, 50);
                    }
                };


            }catch (Exception e) {textRepsOntime.setText("0"); return false;}
            handler.postDelayed(runnable, 50);
            return true;
        });

        removeRestOntime.setOnClickListener((view) -> {
            try{
                Integer help = Integer.valueOf(textRestSecondsOntime.getText().toString());
                if(help > 60){
                    Integer help2 = Integer.valueOf(textRestMinutesOntime.getText().toString());
                    int remainder = help % 60;
                    int quotient = help / 60;
                    help2 = help2 + quotient;
                    textRestMinutesOntime.setText(help2.toString());
                    remainder--;
                    if(remainder < 0)
                        remainder = 59;
                    textRestSecondsOntime.setText(((Integer)remainder).toString());
                    help = remainder;
                }
                else{
                    help--;
                    if(help < 0) {
                        help = 59;
                        Integer help2 = Integer.valueOf(textRestMinutesOntime.getText().toString());
                        help2--;
                        if(help2 < 0)
                            help2 = 0;
                        textRestMinutesOntime.setText(help2.toString());
                    }
                    textRestSecondsOntime.setText(help.toString());
                }
            }catch (Exception e) {textRepsOntime.setText("0");}
        });

        removeRestOntime.setOnLongClickListener((view) -> {
            try{
                runnable = () -> {
                    if(!removeRestOntime.isPressed()) return;
                    Integer help = Integer.valueOf(textRestSecondsOntime.getText().toString());
                    if(help > 60){
                        Integer help2 = Integer.valueOf(textRestMinutesOntime.getText().toString());
                        int remainder = help % 60;
                        int quotient = help / 60;
                        help2 = help2 + quotient;
                        textRestMinutesOntime.setText(help2.toString());
                        remainder--;
                        if(remainder < 0)
                            remainder = 59;
                        textRestSecondsOntime.setText(((Integer)remainder).toString());
                        help = remainder;
                    }
                    else{
                        help--;
                        if(help < 0) {
                            help = 59;
                            Integer help2 = Integer.valueOf(textRestMinutesOntime.getText().toString());
                            help2--;
                            if(help2 < 0)
                                help2 = 0;
                            textRestMinutesOntime.setText(help2.toString());
                        }
                        textRestSecondsOntime.setText(help.toString());
                    }
                    handler.postDelayed(runnable, 50);
                };
            }catch (Exception e) {textRepsOntime.setText("0"); return false;}
            handler.postDelayed(runnable, 50);
            return true;
        });

        addWorkOntime.setOnClickListener((view) -> {
            try{
                Integer help = Integer.valueOf(textWorkSecondsOntime.getText().toString());
                if(help > 59){
                    Integer help2 = Integer.valueOf(textWorkMinutesOntime.getText().toString());
                    int remainder = help % 60;
                    int quotient = help / 60;
                    help2 = help2 + quotient;
                    textWorkMinutesOntime.setText(help2.toString());
                    remainder++;
                    textWorkSecondsOntime.setText((((Integer) remainder).toString()));
                    help = remainder;
                }
                else{
                    help++;
                    textWorkSecondsOntime.setText(help.toString());
                }

            }catch (Exception e) {textRepsOntime.setText("0");}
        });

        addWorkOntime.setOnLongClickListener((view) -> {
            try{
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        if(!addWorkOntime.isPressed()) return;
                        Integer help = Integer.valueOf(textWorkSecondsOntime.getText().toString());
                        if(help > 59){
                            Integer help2 = Integer.valueOf(textWorkMinutesOntime.getText().toString());
                            int remainder = help % 60;
                            int quotient = help / 60;
                            help2 = help2 + quotient;
                            textWorkMinutesOntime.setText(help2.toString());
                            remainder++;
                            textWorkSecondsOntime.setText((((Integer) remainder).toString()));
                            help = remainder;
                        }
                        else{
                            help++;
                            textWorkSecondsOntime.setText(help.toString());
                        }
                        handler.postDelayed(runnable, 50);
                    }
                };


            }catch (Exception e) {textRepsOntime.setText("0"); return false;}
            handler.postDelayed(runnable, 50);
            return true;
        });

        removeWorkOntime.setOnClickListener((view) -> {
            try{
                Integer help = Integer.valueOf(textWorkSecondsOntime.getText().toString());
                if(help > 60){
                    Integer help2 = Integer.valueOf(textWorkMinutesOntime.getText().toString());
                    int remainder = help % 60;
                    int quotient = help / 60;
                    help2 = help2 + quotient;
                    textWorkMinutesOntime.setText(help2.toString());
                    remainder--;
                    if(remainder < 0)
                        remainder = 59;
                    textWorkSecondsOntime.setText(((Integer)remainder).toString());
                    help = remainder;
                }
                else{
                    help--;
                    if(help < 0) {
                        help = 59;
                        Integer help2 = Integer.valueOf(textWorkMinutesOntime.getText().toString());
                        help2--;
                        if(help2 < 0)
                            help2 = 0;
                        textWorkMinutesOntime.setText(help2.toString());
                    }
                    textWorkSecondsOntime.setText(help.toString());
                }
            }catch (Exception e) {textRepsOntime.setText("0");}
        });

        removeWorkOntime.setOnLongClickListener((view) -> {
            try{
                runnable = () -> {
                    if(!removeWorkOntime.isPressed()) return;
                    Integer help = Integer.valueOf(textWorkSecondsOntime.getText().toString());
                    if(help > 60){
                        Integer help2 = Integer.valueOf(textWorkMinutesOntime.getText().toString());
                        int remainder = help % 60;
                        int quotient = help / 60;
                        help2 = help2 + quotient;
                        textWorkMinutesOntime.setText(help2.toString());
                        remainder--;
                        if(remainder < 0)
                            remainder = 59;
                        textWorkSecondsOntime.setText(((Integer)remainder).toString());
                        help = remainder;
                    }
                    else{
                        help--;
                        if(help < 0) {
                            help = 59;
                            Integer help2 = Integer.valueOf(textWorkMinutesOntime.getText().toString());
                            help2--;
                            if(help2 < 0)
                                help2 = 0;
                            textWorkMinutesOntime.setText(help2.toString());
                        }
                        textWorkSecondsOntime.setText(help.toString());
                    }
                    handler.postDelayed(runnable, 50);
                };
            }catch (Exception e) {textRepsOntime.setText("0"); return false;}
            handler.postDelayed(runnable, 50);
            return true;
        });

        buttonGoOntime.setOnClickListener((view) -> {
            try {
                int reps = Integer.valueOf(textRepsOntime.getText().toString());
                int restMinutes = Integer.valueOf(textRestMinutesOntime.getText().toString());
                int restSeconds = Integer.valueOf(textRestSecondsOntime.getText().toString());
                int workMinutes = Integer.valueOf(textWorkMinutesOntime.getText().toString());
                int workSeconds = Integer.valueOf(textWorkSecondsOntime.getText().toString());

                if(reps < 1){
                    buttonGoOntime.setBackgroundColor(getResources().getColor(R.color.red));
                    return;
                }

                Fragment ontimeFragmentCD = new OntimeCDFragment(reps, restMinutes, restSeconds, workMinutes, workSeconds);
                replaceFragment(ontimeFragmentCD);
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
