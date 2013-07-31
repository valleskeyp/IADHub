package com.valleskeyp.mgdgame;

import java.util.List;

import sqlite.ScoresDataSource;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ScoreActivity extends Activity {
	ListView scoresList;
	TextView filterScoreText;
	ScoresDataSource datasource;
	private Button filterDay, filterAll;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);
	    
	    datasource = new ScoresDataSource(this);
	    scoresList = (ListView) findViewById(R.id.list_scores);
	    filterScoreText = (TextView) findViewById(R.id.filterModeText);
	    filterDay = (Button) findViewById(R.id.filterDay);
	    filterAll = (Button) findViewById(R.id.filterAll);
	    
	    filterDay.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) { // filter results by highest level achieved
				List<String> scores = datasource.findByDay();
				filterScoreText.setText("Filtered by one day results");
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(ScoreActivity.this, android.R.layout.simple_list_item_1, scores);
				scoresList.setAdapter(adapter);
			}
		});
	    
	    filterAll.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) { // filter results by highest score achieved
				List<String> scores = datasource.findAll();
				filterScoreText.setText("Filtered by all time results");
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(ScoreActivity.this, android.R.layout.simple_list_item_1, scores);
				scoresList.setAdapter(adapter);
			}
		});

	    datasource.open();
	    List<String> scores = datasource.findAll();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, scores);
		scoresList.setAdapter(adapter);
	}
}
