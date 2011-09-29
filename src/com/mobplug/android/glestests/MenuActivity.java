package com.mobplug.android.glestests;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class MenuActivity extends ListActivity {
	private static final String[] OPTIONS  = {
		"Rotating Triangle",
		"Cube",
		"Torus",
		"Cube with Lighting",
		"Torus with Lighting",
		"Cube with Texture",
		"Cube with Texture and Lighting"
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, OPTIONS);
		setListAdapter(adapter);
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = null;		
		switch(position) {
			case 0: intent = new Intent(this, GLESTestsActivity.class); break;
			case 1: intent = new Intent(this, CubeActivity.class); break;
			case 2: intent = new Intent(this, TorusActivity.class); break;			
			case 3: intent = new Intent(this, CubeLightingActivity.class); break;			
			case 4: intent = new Intent(this, TorusLightingActivity.class); break;			
			case 5: intent = new Intent(this, CubeTextureActivity.class); break;			
			case 6: intent = new Intent(this, CubeLightingTextureActivity.class); break;			
			default: intent = new Intent(this, GLESTestsActivity.class); break;
		}
		startActivity(intent);
	}
}
