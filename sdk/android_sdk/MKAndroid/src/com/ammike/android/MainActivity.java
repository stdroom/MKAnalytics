package com.ammike.android;

import com.wbtech.ums.UmsAgent;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity implements View.OnClickListener{
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UmsAgent.setBaseURL("http://192.168.10.104:80/index.php");
        findViewById(R.id.btn_event1).setOnClickListener(this);
        findViewById(R.id.btn_event2).setOnClickListener(this);
        findViewById(R.id.btn_event3).setOnClickListener(this);
    }

	@Override
	protected void onResume() {
		super.onResume();
		UmsAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		UmsAgent.onPause(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btn_event1:
			UmsAgent.onEvent(MainActivity.this, "event1");
			break;
		case R.id.btn_event2:
			UmsAgent.onEvent(MainActivity.this, "event2");
			break;
		case R.id.btn_event3:
			UmsAgent.onEvent(MainActivity.this, "event3");
			break;
		}
	}
    
    

}
