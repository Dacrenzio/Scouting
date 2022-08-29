package com.example.scouting;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FABButton {
    private View root;
    private final int[] margin;
    private final FloatingActionButton button;
    private int score = 0;
    private int pallonetti = 0;
    private FABButton mainButton = null;

    public FABButton(int marginTop, int marginRight,FloatingActionButton button, boolean canPallonetto) {
        this.margin = new int[]{marginTop, marginRight};
        this.button = button;
        pallonetti = canPallonetto ? 0 : -1;
    }

    public FABButton(int marginTop, int marginRight, FloatingActionButton button, FABButton mainButton) {
        this.margin = new int[]{marginTop, marginRight};
        this.button = button;
        this.mainButton = mainButton;
        score = -1;
    }

    public void assignRoot(View root) {
        this.root = root;
        this.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementScore();
            }
        });
        this.button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                decrementScore();
                return true;
            }
        });
    }


    private void incrementScore() {
        if (mainButton != null) {
            mainButton.incrementPallonetti();
        } else {
            ++score;
            drawScore();
        }
    }

    private void decrementScore() {
        if (mainButton != null) {
            mainButton.decrementPallonetti();
        } else {
            score = score - 1 >= 0 ? --score : 0;
            drawScore();
        }
    }

    private void incrementPallonetti() {
        ++pallonetti;
        drawScore();
    }

    private void decrementPallonetti() {
        pallonetti = pallonetti - 1 >= 0 ? --pallonetti : 0;
        drawScore();
    }

    public void drawScore() {
        if (score == -1) {
            return;
        }
        String imageText = pallonetti == -1 ? (score + "") : (score + "(" + pallonetti + ")");
        button.setImageBitmap(textAsBitmap(imageText, 1000, Color.BLACK));
    }

    public void changeLayout(boolean landscape) {
            margin[1] = margin[1] * -1;

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) button.getLayoutParams();
        params.setMargins(0, dpToPixel(landscape? margin[0]:margin[1]), dpToPixel(landscape? margin[1]:margin[0]), 0);
        button.setLayoutParams(params);
    }

    public void switchSize(boolean landscape) {
        margin[0] = margin[0] * -1;
        changeLayout(landscape);
    }

    public void clearScore(){
        if(score > 0)
            score = 0;
        if(pallonetti>0)
            pallonetti = 0;

        drawScore();
    }



    public static Bitmap textAsBitmap(String text, float textSize, int textColor) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setFakeBoldText(true);
        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(text) + 0.0f); // round
        int height = (int) (baseline + paint.descent() + 0.0f);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(image);
        canvas.drawText(text, 0, baseline, paint);
        return image;
    }

    private int dpToPixel(int pixel) {
        return Math.round(pixel * ((float) root.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }


    public int getScore() {
        return score;
    }

    public int getPallonetti() {
        return pallonetti;
    }
}
