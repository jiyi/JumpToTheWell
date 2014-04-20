package com.example.jumptothewell;

import android.app.Activity;
import android.os.Bundle;


public class MainActivity extends Activity {

	GameView gameView = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 隐藏标题栏
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		// setContentView(R.layout.activity_main);
		gameView = new GameView(this);
		setContentView(gameView);
	}
}
