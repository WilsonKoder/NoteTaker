//Wilson Koder 30/07/14
//Copyright (c) Wilson Koder 2014
//"NoteTaker" for Android OS
//wilsonkoder111+dev@gmail.com

package com.wilsonkoder.notetaker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EditNoteActivity extends Activity {

    public static final int RESULT_DELETE = -500;
    private boolean isInEditMode = true;
    private boolean isAddingNote = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editnote);
        //Declare the done buttons variable.
        final Button doneButton = (Button)findViewById(R.id.doneButton);
        final Button cancelButton = (Button) findViewById(R.id.cancelButton);
        final EditText titleEditText = (EditText) findViewById(R.id.titleEditText);
        final EditText noteEditText = (EditText) findViewById(R.id.noteEditText);
        final TextView dateTextView = (TextView) findViewById(R.id.dateTextView);

        Serializable extra = getIntent().getSerializableExtra("Note");

        //Check if we even have data
        if(extra != null) {
            //code goes here
            Note note = (Note) extra;
            titleEditText.setText(note.getTitle());
            noteEditText.setText(note.getNote());

            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String date = dateFormat.format(note.getDate());

            dateTextView.setText(date);

            isInEditMode = false;
            titleEditText.setEnabled(false);
            noteEditText.setEnabled(false);
            doneButton.setText("Edit");

            isAddingNote = false;
        }

        //Set the on click listener.
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(isInEditMode) {
                    Intent returnInent = new Intent();
                    Note note = new Note(titleEditText.getText().toString(), noteEditText.getText().toString(), Calendar.getInstance().getTime());
                    returnInent.putExtra("Note", note);
                    setResult(RESULT_OK, returnInent);
                    finish();
                } else {
                    isInEditMode = true;
                    doneButton.setText("Done");
                    titleEditText.setEnabled(true);
                    noteEditText.setEnabled(true);
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED, new Intent());
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        if(isAddingNote) {
            menu.removeItem(R.id.deleteNote);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete this note? This action cannot be undone.");
        builder.setTitle("Confirm Deletion");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Intent returnIntent = new Intent();

                setResult(RESULT_DELETE);
                finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.create().show();

        return true;
    }

}
