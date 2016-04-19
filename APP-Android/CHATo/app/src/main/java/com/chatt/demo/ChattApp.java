package com.chatt.demo;

import android.app.Application;

//import com.parse.Parse;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * The Class ChattApp is the Main Application class of this app. The onCreate
 * method of this class initializes the Parse.
 */
public class ChattApp extends Application
{
	private Connection connection;

	/* (non-Javadoc)
	 * @see android.app.Application#onCreate()
	 */
	@Override
	public void onCreate()
	{
		super.onCreate();

		//Parse.initialize(this, "aEK4rYnLSxU1AqvXc8Euil0Is8mklk7d4Y3YqC6i",
		//"dYguCg65IT3J2gX2JeFhhDcB25RBjdpceveS8UdU");

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("franciscocabral.com");
		factory.setUsername("guest");
		factory.setPassword("guest");
		try {
			this.connection = factory.newConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
