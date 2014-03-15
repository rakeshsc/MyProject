package com.myproject.filesoverwifi;


import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoadappActivity extends Activity {
	TextView WifiState;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loadapp);
		
		Button receive=(Button)findViewById(R.id.btnreceive);
		Button btn=(Button) findViewById(R.id.button1);
		
		receive.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View v) 
			{
				Log.i("Button Clicked", "yipee");

				try 
				{
					int filesize=900000; // filesize temporary hardcoded

					long start = System.currentTimeMillis();
					int bytesRead;
					int current = 0;
					// localhost for testing
					// TODO: server's IP address. Socket should match one above in server
					Socket sock = new Socket("172.16.97.242",5005);

					Log.i("************", "Connecting...");

					// receive file
					byte [] mybytearray  = new byte [filesize];
					InputStream is = sock.getInputStream();
					
					// TODO: Put where you want to save the file
					/* N.B.:
					 * * To view if the file transfer was successful:
					 *       * use `./adb shell` 
					 *       * use the app: File Manager
					 * 
					 * * If you downloaded to '/mnt/sdcard/download', 
					 *   your download might not show up in 'Downloads'
					 *   
					 * * You might not have '/mnt/sdcard/download' directory
					 *   if you have never downloaded anything on your iPhone
					 */
					
					FileOutputStream fos = new FileOutputStream("/mnt/sdcard/download/b.JPG");
					BufferedOutputStream bos = new BufferedOutputStream(fos);
					bytesRead = is.read(mybytearray,0,mybytearray.length);
					current = bytesRead;
					do 
					{
						bytesRead =
						is.read(mybytearray, current, (mybytearray.length-current));
						if(bytesRead >= 0) current += bytesRead;
					}
					while(bytesRead > -1);

					bos.write(mybytearray, 0 , current);
					bos.flush();
					long end = System.currentTimeMillis();
					Log.i("************ end-start = ", String.valueOf(end-start));
					bos.close();
					sock.close();
				} 
				catch ( UnknownHostException e ) 
				{
					Log.i("******* :( ", "UnknownHostException");
				}
				catch (IOException e)
				{
					Log.i("Read has IOException", "e: " + e);
				}

				Log.i("=============== the end of read ===============", "==");

			}
		});

		
		
	btn.setOnClickListener(new View.OnClickListener()
	 {

		@Override
		public void onClick(View v) {
	
			Intent i = new Intent(getApplicationContext(),LoadFiles.class);
	        startActivity(i);
			
		}
	 });
	}
}
		
	

