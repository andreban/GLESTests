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
		"Cube with Texture and Lighting",
		"Sphere with Texture and Lighting",
		"Cylinder with Texture and Lighting",
		"Torus with Texture and Lighting",		
		"Disk with Texture and Lighting",
		"Cube with TGA Texture",
		"md3 Model",
		"md3 Model Texture",
		"Torus Toon Shader",
		"md3 Loader",
		"Tunnels 1"
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
			case 7: intent = new Intent(this, SphereLightingTextureActivity.class); break;			
			case 8: intent = new Intent(this, CylinderLightingTextureActivity.class); break;			
			case 9: intent = new Intent(this, TorusLightingTextureActivity.class); break;			
			case 10: intent = new Intent(this, DiskLightingTextureActivity.class); break;			
			case 11: intent = new Intent(this, TGACubeTextureActivity.class); break;			
			case 12: intent = new Intent(this, Md3ModelActivity.class); break;			
			case 13: intent = new Intent(this, Md3ModelTextureActivity.class); break;			
			case 14: intent = new Intent(this, TorusToonShaderActivity.class); break;			
			case 15: intent = new Intent(this, Md3LoaderActivity.class); break;			
			case 16: intent = new Intent(this, Tunnels1Activity.class); break;			
			default: intent = new Intent(this, GLESTestsActivity.class); break;
		}
		startActivity(intent);
	}
}
