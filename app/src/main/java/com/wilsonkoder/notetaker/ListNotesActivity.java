//Wilson Koder 30/07/14
//Copyright (c) Wilson Koder 2014
//"NoteTaker" for Android OS
//wilsonkoder111+dev@gmail.com


package com.wilsonkoder.notetaker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.wilsonkoder.notetaker.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListNotesActivity extends Activity {
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_CANCELED) {
            return;
        }

        if(resultCode == EditNoteActivity.RESULT_DELETE) {
            notes.remove(editingNoteId);
            editingNoteId = -1;
            populateList();
        }

        Serializable extra = data.getSerializableExtra("Note");
        if(extra != null) {
            Note newNote = (Note)extra;
            if(editingNoteId > -1)
            {
                notes.set(editingNoteId, newNote);
                editingNoteId = -1;
            }
            else {
                notes.add(newNote);
            }
            populateList();
        }
    }

    private List<Note> notes = new ArrayList<Note>();
    private ListView notesListView;
    private int editingNoteId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_notes);
        notesListView = (ListView) findViewById(R.id.notesListView);

        notesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent updateNoteIntent = new Intent(view.getContext(), EditNoteActivity.class);
                updateNoteIntent.putExtra("Note", notes.get(i));
                editingNoteId = i; //i is equal to the items number on the list.
                startActivityForResult(updateNoteIntent, 1);
            }
        });

        registerForContextMenu(notesListView);



        /*
        * notes.add(new Note("First Note", "blah", new Date()));
        * notes.add(new Note("Second Note", "blah", new Date()));
        * notes.add(new Note("Third Note", "blah", new Date()));
        * notes.add(new Note("Fourth Note", "blah", new Date()));
        * notes.add(new Note("Fifth Note", "blah", new Date()));
        */
        populateList();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list_notes, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Intent editNoteIntent = new Intent(this, EditNoteActivity.class);
        //startActivity(editNoteIntent);
        startActivityForResult(editNoteIntent, 1);
        //return super.onOptionsItemSelected(item);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        notes.remove(info.position);
        populateList();
        return super.onContextItemSelected(item);
    }

    private void populateList() {
        List<String> values = new ArrayList<String>();

        for(Note note : notes) {
            values.add(note.getTitle());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, values);
        notesListView.setAdapter(adapter);
    }
}
