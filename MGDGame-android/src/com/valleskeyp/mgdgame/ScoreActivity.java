package com.valleskeyp.mgdgame;

import java.util.List;

import sqlite.ScoresDataSource;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ScoreActivity extends Activity {
	ListView scoresList;
	ScoresDataSource datasource;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);
	    
	    datasource = new ScoresDataSource(this);
	    scoresList = (ListView) findViewById(R.id.list_scores);
	    
	    datasource.open();
	    List<String> scores = datasource.findAll();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, scores);
		scoresList.setAdapter(adapter);
	}
}
