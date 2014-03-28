package com.songcaster.notifications;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;

import com.parse.ParseException;
import com.parse.ParsePush;
import com.parse.PushService;
import com.parse.SendCallback;

public class SongCasterUtility {

	private static SongCasterUtility _instance;
	private Context context;
	
	private SongCasterUtility() {
		
	}
	
	public static SongCasterUtility getInstance(Context context) {
		if(_instance == null) {
			_instance = new SongCasterUtility();
		}
		_instance.context = context;
		return _instance;
	}
	
	public void subscribeChannel(String channelName, Class<Activity> classObject) {
		PushService.subscribe(_instance.context, channelName, classObject);
	}
	
	public void unsubscribeChannel(String channelName) {
		PushService.unsubscribe(context, channelName);
	}
	
	/*
	 * This is an asynchronous call
	 */
	public void pushMessage(JSONObject object, String channelName) throws JSONException {
		
		// Our broadcast receiver will get only this action, check manifest
		object.put("action", "songcasterpush");
		
		ParsePush push = new ParsePush();
		push.setChannel(channelName);
		push.setData(object);
		
		push.sendInBackground(new SendCallback() {
			
			@Override
			public void done(ParseException e) {
				// Do whatever you need to 
			}
		});
	}
	
	
}
