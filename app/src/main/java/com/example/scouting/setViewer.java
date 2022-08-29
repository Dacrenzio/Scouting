package com.example.scouting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

public class setViewer extends AppCompatActivity {
    private ArrayList<CardView> itemList;
    private RecyclerView recyclerView;
    public static MyAdapter adapter;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_viewer);
        File fileDX = new File(getApplicationContext().getFilesDir(), "setsDX.CSV");
        File fileSX = new File(getApplicationContext().getFilesDir(), "setsSX.CSV");
        Scanner scanDX = null;
        Scanner scanSX = null;
        try {
             itemList = new ArrayList<>();
            if (!fileDX.exists() || !fileSX.exists()) {
                itemList.add(new CardView("Err", new String[]{"Nan", "Nan", "Nan", "Nan", "Nan", "Nan", "Nan", "Nan"}, new String[]{"Nan", "Nan", "Nan", "Nan", "Nan", "Nan", "Nan", "Nan"}));
            } else {
                scanDX = new Scanner(fileDX);
                scanSX = new Scanner(fileSX);

                scanDX.nextLine();
                scanSX.nextLine();
                while (scanDX.hasNext() && scanSX.hasNext()) {
                    String[] dataDX = scanDX.nextLine().split(";");
                    String[] dataSX = scanSX.nextLine().split(";");
                    itemList.add(new CardView(dataDX[0], Arrays.copyOfRange(dataSX, 1, 9), Arrays.copyOfRange(dataDX, 1, 9)));
                }
                Collections.reverse(itemList);

            }

            adapter = new MyAdapter(itemList);
            buildRecyclerView();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void buildRecyclerView() {
        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);

        //set the row of 3 elements each
        layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL ,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}