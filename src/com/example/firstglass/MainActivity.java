package com.example.firstglass;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends Activity {

	TextView textView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		textView = (TextView)findViewById(R.id.TextView10);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void onClickSet(View v){
	//	
	//	Toast.makeText(this, "Hello Glass", Toast.LENGTH_SHORT).show();
	//	hello.setText("Hello Glass!");
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case R.id.menu_item_android:
				textView.setText("Hello Android!");
				return true;
			case R.id.menu_item_glass:
				textView.setText("Hello Glass!");
				return true;
			case R.id.menu_item_exit:
				//Launch Voice
				LaunchVoiceRecognition();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	//Method to use the Voice Recognition
	public void LaunchVoiceRecognition(){
		//Create an Intent to recognize speech
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		//The second Parameter is a user defined code that is returned from the activity
		this.startActivityForResult(intent, 100);
	}
	
	//We override onActivityResult to create the activity for Voice Recognition
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode==100 && resultCode == Activity.RESULT_OK) {
			String name = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0);
			textView.setText("Hello" + name +"!");
		}
		super.onActivityResult(requestCode, resultCode, data);
	}



	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

	//This method is called when is a tapping
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_DPAD_CENTER){
			Toast.makeText(this, "You tapped", Toast.LENGTH_SHORT).show();
			//To show the menu
			this.openOptionsMenu();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	//This method is called whenever a Move event tap defined is triggered (Slide up,down or slide forward/backward)
	@Override
	public boolean onGenericMotionEvent(MotionEvent event) {
		switch(event.getAction()){
			case MotionEvent.ACTION_DOWN:
				Toast.makeText(this, "You slided down", Toast.LENGTH_SHORT).show();
				Log.d("TOUCH","ACTION_DOWN");
				break;
			case MotionEvent.ACTION_UP:
				Toast.makeText(this, "You slided up", Toast.LENGTH_SHORT).show();
				Log.d("TOUCH","ACTION_UP");
				break;
			case MotionEvent.ACTION_MOVE:
				Toast.makeText(this, "You slided at: x: " + event.getX() + " ; y: " + event.getY(), Toast.LENGTH_SHORT).show();
				Log.d("TOUCH","ACTION_MOVE");
				break;
		}
		return super.onGenericMotionEvent(event);
	}
	
	
	

}
