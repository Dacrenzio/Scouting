package com.example.scouting;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.WindowInsets;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Switch;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.scouting.databinding.ActivityFullscreenBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;


public class FullscreenActivity extends AppCompatActivity {
    private final FABButton[] buttons = new FABButton[18];

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar
            if (Build.VERSION.SDK_INT >= 30) {
                mContentView.getWindowInsetsController().hide(
                        WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars());
            } else {
                // Note that some of these constants are new as of API 16 (Jelly Bean)
                // and API 19 (KitKat). It is safe to use them, as they are inlined
                // at compile-time and do nothing on earlier devices.
                mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            }
        }
    };
    private View mControlsView;
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    private ActivityFullscreenBinding binding;


    /**
     * on rotation changed
     */
    @SuppressLint({"ResourceType", "RtlHardcoded"})
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        FloatingActionButton clearAllButton = this.findViewById(R.id.clearAll);
        FrameLayout.LayoutParams layoutParamsClear = (FrameLayout.LayoutParams) clearAllButton.getLayoutParams();

        FloatingActionButton addSetButton = this.findViewById(R.id.addSet);
        FrameLayout.LayoutParams layoutParamsSet = (FrameLayout.LayoutParams) addSetButton.getLayoutParams();


        ImageView bgImage = this.findViewById(R.id.backgroudImage);

        for (FABButton button : buttons) {
            button.changeLayout(binding.getRoot().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE);
        }

        /** Checks the new orientation of the screen*/
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            bgImage.setImageResource(R.drawable.campo);

            layoutParamsSet.gravity = (Gravity.END | Gravity.BOTTOM);
            addSetButton.setLayoutParams(layoutParamsSet);

            layoutParamsClear.gravity = Gravity.NO_GRAVITY;
            clearAllButton.setLayoutParams(layoutParamsClear);

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            bgImage.setImageResource(R.drawable.campoportrait);

            layoutParamsSet.gravity = Gravity.BOTTOM;
            addSetButton.setLayoutParams(layoutParamsSet);

            layoutParamsClear.gravity = Gravity.RIGHT;
            clearAllButton.setLayoutParams(layoutParamsClear);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFullscreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        mVisible = true;
        mControlsView = binding.fullscreenContentControls;
        mContentView = binding.fullscreenContent;

        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hide();
            }
        });

        /**setting the buttons based on orientation*/
        FloatingActionButton clearAllButton = this.findViewById(R.id.clearAll);
        FrameLayout.LayoutParams layoutParamsClear = (FrameLayout.LayoutParams) clearAllButton.getLayoutParams();

        FloatingActionButton addSetButton = this.findViewById(R.id.addSet);
        FrameLayout.LayoutParams layoutParamsSet = (FrameLayout.LayoutParams) addSetButton.getLayoutParams();

        ImageView bgImage = this.findViewById(R.id.backgroudImage);
        if (binding.getRoot().getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
            bgImage.setImageResource(R.drawable.campoportrait);

            layoutParamsSet.gravity = Gravity.BOTTOM;
            addSetButton.setLayoutParams(layoutParamsSet);

            layoutParamsClear.gravity = Gravity.RIGHT;
            clearAllButton.setLayoutParams(layoutParamsClear);
        }

        /**adding the buttons*/
        buttons[0] = new FABButton(120, 80, this.findViewById(R.id.lOpposto), true);
        buttons[1] = new FABButton(170, 150, this.findViewById(R.id.lOppostoPallonetto), buttons[0]);
        buttons[2] = new FABButton(-120, 80, this.findViewById(R.id.lBanda), true);
        buttons[3] = new FABButton(-70, 150, this.findViewById(R.id.lBandaPallonetto), buttons[2]);
        buttons[4] = new FABButton(0, 80, this.findViewById(R.id.lCentro), true);
        buttons[5] = new FABButton(50, 150, this.findViewById(R.id.lCentroPallonetto), buttons[4]);
        buttons[6] = new FABButton(0, 230, this.findViewById(R.id.lPipe), false);
        buttons[7] = new FABButton(140, 270, this.findViewById(R.id.lServizio), true);

        buttons[8] = new FABButton(120, -80, this.findViewById(R.id.rBanda), true);
        buttons[9] = new FABButton(170, -150, this.findViewById(R.id.rBandaPallonetto), buttons[8]);
        buttons[10] = new FABButton(-120, -80, this.findViewById(R.id.rOpposto), true);
        buttons[11] = new FABButton(-70, -150, this.findViewById(R.id.rOppostoPallonetto), buttons[10]);
        buttons[12] = new FABButton(0, -80, this.findViewById(R.id.rCentro), true);
        buttons[13] = new FABButton(50, -150, this.findViewById(R.id.rCentroPallonetto), buttons[12]);
        buttons[14] = new FABButton(0, -230, this.findViewById(R.id.rPipe), false);
        buttons[15] = new FABButton(-140, -270, this.findViewById(R.id.rServizio), true);

        buttons[16] = new FABButton(170, 300, this.findViewById(R.id.lServizioMancati), buttons[7]);
        buttons[17] = new FABButton(-170, -300, this.findViewById(R.id.rServizioMancati), buttons[15]);

        for (FABButton button : buttons) {
            button.assignRoot(binding.getRoot());
            button.drawScore();
            if (binding.getRoot().getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE)
                button.changeLayout(false);
        }


        /**setting up the switch and buttons for clear and reset*/
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch aSwitch = binding.getRoot().findViewById(R.id.switchLato);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                for (FABButton button : buttons) {
                    button.switchSize(binding.getRoot().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE);
                }
            }
        });


        clearAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (FABButton button : buttons) {
                    button.clearScore();
                }
            }
        });


        /**CREATE A SET*/
        addSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File fileDX = new File(getApplicationContext().getFilesDir(), "setsDX.CSV");
                File fileSX = new File(getApplicationContext().getFilesDir(), "setsSX.CSV");

                StringBuilder textDX = new StringBuilder();
                StringBuilder textSX = new StringBuilder();
                Calendar calendar = Calendar.getInstance();


                if (!fileDX.exists()) {
                    textDX.append(";Alzate ad Opposto dx;Alzate a Banda dx;Alzate a Centro dx;Alzate a Pipe dx;Pallonetti Opposto dx;Pallonetti Banda dx;Pallonetti Centr dx;Servizi dx;\n");
                }
                textDX.append(calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.YEAR) + ";");
                textDX.append(buttons[10].getScore() + ";");
                textDX.append(buttons[8].getScore() + ";");
                textDX.append(buttons[12].getScore() + ";");
                textDX.append(buttons[14].getScore() + ";");
                textDX.append(buttons[10].getPallonetti() + ";");
                textDX.append(buttons[8].getPallonetti() + ";");
                textDX.append(buttons[12].getPallonetti() + ";");
                textDX.append(buttons[15].getScore() + "(" + buttons[15].getPallonetti() + ")" + ";\n");


                if (!fileSX.exists()) {
                    textSX.append(";Alzate ad Opposto sx;Alzate a Banda sx;Alzate a Centro sx;Alzate a Pipe sx;Pallonetti Opposto sx;Pallonetti Banda sx;Pallonetti Centr sx;Servizi sx;\n");
                }
                textSX.append("Set in data: " + calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.YEAR) + ";");
                textSX.append(buttons[0].getScore() + ";");
                textSX.append(buttons[2].getScore() + ";");
                textSX.append(buttons[4].getScore() + ";");
                textSX.append(buttons[6].getScore() + ";");
                textSX.append(buttons[0].getPallonetti() + ";");
                textSX.append(buttons[2].getPallonetti() + ";");
                textSX.append(buttons[4].getPallonetti() + ";");
                textSX.append(buttons[7].getScore() + "(" + buttons[7].getPallonetti() + ")" + ";\n");


                try {
                    FileWriter fileWriter = new FileWriter(fileDX, true);
                    fileWriter.append(textDX.toString());
                    fileWriter.close();

                    fileWriter = new FileWriter(fileSX, true);
                    fileWriter.append(textSX.toString());
                    fileWriter.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                for (FABButton button : buttons) {
                    button.clearScore();
                }
            }
        });

        addSetButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(getApplicationContext(), setViewer.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
                return true;
            }
        });


    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }


    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}