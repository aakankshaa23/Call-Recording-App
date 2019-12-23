package com.example.callrecordingapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ShowFiles extends AppCompatActivity {
    File file;
    ArrayAdapter adapter;
    ArrayList myList;
    ListView audioListView;
    String path = "/sdcard/Alarms/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_files);
        file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS);
        if (!file.exists()) {
            file.mkdir();
        }
        myList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, myList);
        final List<String> myNewList = new ArrayList<>();
        File list[] = file.listFiles();

        for (int i = 0; i < list.length; i++) {
            myNewList.add(0, list[i].getName());
        }

        myList.clear();
        myList.addAll(myNewList);
        adapter.notifyDataSetChanged();
        audioListView = (ListView) findViewById(R.id.audioList);
        audioListView.setAdapter(adapter); //Set all the file in the list.
        audioListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                String filepath = path + myNewList.get(position);
                final Uri data = FileProvider.getUriForFile(ShowFiles.this, ".provider", new File(filepath));
                grantUriPermission(getPackageName(), data, Intent.FLAG_GRANT_READ_URI_PERMISSION);

                final Intent intent = new Intent(Intent.ACTION_VIEW)
                        .setDataAndType(data, "video/3gp")
                        .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(intent);


            }
        });
//        audioListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//
//            @Override
//            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
//                                           final int position, long arg3) {
//
//                AlertDialog.Builder alert = new AlertDialog.Builder(
//                        ShowFiles.this);
//                alert.setTitle("Rename");
//
//                final EditText input = new EditText(ShowFiles.this);
//                alert.setView(input);
//
//
//                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton) {
//                        File from = new File(Environment.getExternalStorageDirectory() + path + adapter.getItem(position));
//
//                        File to = new File(Environment.getExternalStorageDirectory() + path + input.getText().toString() + ".3gp" );
//
//                        boolean renamed = from.renameTo(to);
//
//                        if(renamed){
//                            System.out.println("The position is " + position);
//                            adapter.notifyDataSetChanged();
//                        }
//
//
//                    }
//                });
//
//                alert.setNegativeButton("CANCEL",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int whichButton) {
//                                dialog.cancel();
//                            }
//                        });
//                AlertDialog alertDialog = alert.create();
//                alertDialog.show();
//                return false;
//            }
//        });
//
//    }


    }
}

