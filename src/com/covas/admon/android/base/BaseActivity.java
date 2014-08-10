package com.covas.admon.android.base;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.covas.admon.android.App;
import com.covas.admon.android.util.Logger;

/**
 * 실질적으로 밑바탕이 되는  부모클래스 (Activity)
 * 
 * @author 신기웅(tlsrldnd0418@naver.com)
 * @since 2012. 3. 29.
 * @version 1.0.0
 */
public abstract class BaseActivity extends Activity{
	
	protected App 					  app;
	private static InputMethodManager imm;
	
	protected boolean isFinished = false;
	protected boolean isForceFinished = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		app = (App) getApplicationContext();
		imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		
		
		/**
		 * 타스크킬러 등으로 강제종료 및 메모리 정리 시 앱이 비정상종료되어 앱의 모든 정보가 소실된 상태로 
		 * 다시 어플을 구동 시킬 경우 마지막 활성중이었던 액티비티 부터 시작하게 되는데
		 * 이 때는 앱의 다른 어떤 정보도 있지 않은 상태이기 때문에 강제로 종료시켜줌.
		 * 종료 후에는 자동으로 어플 기본 액티비티가 시작됨
		*/
		if(app.isInitialized() == false){
			forceFinish();
			return;
		}
		
		
		setBase();
		
		init();
	}
	
	private void forceFinish(){
		//isForceFinished = true;
		app.startMain(this);
	}
	
	/**
	 * onCreate를 개념적으로 두개로 나눔<br>
	 * 기본적인 셋팅을 처리
	 */
	protected abstract void setBase();
	
	/**
	 * onCreate를 개념적으로 두개로 나눔<br>
	 * setBase() 이후에 추가적인 셋팅을 처리
	 */
	protected abstract void init();
	
	/**
	 * 간단하게 키보드를 숨겨주는 메소드
	 */
	protected boolean hideKeyboard(){
		if(getCurrentFocus() != null) return imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
		return false;
	}
	
	/**
	 * Toast를 간단히 띄워주는 메소드
	 * @param msg
	 */
	public void toastShow(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * Log를 간단히 띄워주는 메소드
	 * @param msg
	 */
	protected void log(Object caller, String msg){
		//Log.d("FRENz", msg);
		Logger.d(caller,  msg);
	}
	
	@Override
	public void finish() {
		Logger.d( this, "finish()" );
		isFinished = true;
		super.finish();
	}
	
	@Override
	protected void onDestroy() {
		Log.d("test", "디스트로이 = "+this.getClass().getName().substring(this.getClass().getName().lastIndexOf(".")+1));
		super.onDestroy();
	}
	
	@Override
	protected void onStop() {
		Log.d("test", "온 스탑 = "+this.getClass().getName().substring(this.getClass().getName().lastIndexOf(".")+1));
		super.onStop();
	}
	
	@Override
	protected void onPause() {
		Log.d("test", "온 포즈 = "+this.getClass().getName().substring(this.getClass().getName().lastIndexOf(".")+1));
		super.onPause();
	}
	
	@Override
	protected void onResume() {
		Log.d("test", "온 리쥼 = "+this.getClass().getName().substring(this.getClass().getName().lastIndexOf(".")+1));
		super.onResume();
	}
}