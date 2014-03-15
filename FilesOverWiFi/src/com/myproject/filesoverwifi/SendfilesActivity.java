package com.myproject.filesoverwifi;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SendfilesActivity extends Activity {

	private Button serverTransmitButton;
	
	/* Called when the activity is first created. */
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sendfiles);

			serverTransmitButton = (Button)findViewById(R.id.button_TCP_server);
			
			serverTransmitButton.setOnClickListener(new View.OnClickListener() 
			{
				public void onClick(View v) 
				{
					Log.i("Start Server Button Clicked", "yipee");

					try 
					{
						// create socket
						// TODO: the port should match the one in Client
						ServerSocket servsock = new ServerSocket(5005);
						while (true) 
						{
							Log.i("************", "Waiting...");

							Socket sock = servsock.accept();
							// blocks until connection opened
							Log.i("************", "Accepted connection : " + sock);

							// sendfile

							// TODO: put the source of the file
							File myFile = new File ("/mnt/sdcard/download/a.JPG");
							byte [] mybytearray  = new byte [(int)myFile.length()];
							Log.i("####### file length = ", String.valueOf(myFile.length()) );
							FileInputStream fis = new FileInputStream(myFile);
							BufferedInputStream bis = new BufferedInputStream(fis);
							bis.read(mybytearray,0,mybytearray.length);
							OutputStream os = sock.getOutputStream();
							Log.i("************", "Sending...");
							os.write(mybytearray,0,mybytearray.length);
							os.flush();
							sock.close();
						}   
						
					} 
					catch (IOException e) 
					{
						Log.i("Io execption ", "e: " + e);
					}

					Log.i("=============== the end of start ==============", "==");
				}	
			});

			
			
	}
}