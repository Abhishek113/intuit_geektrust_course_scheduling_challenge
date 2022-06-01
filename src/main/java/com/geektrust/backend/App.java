package com.geektrust.backend;

import java.io.IOException;
import com.geektrust.backend.repositories.TrainBogieConfigurationRepository;
import com.geektrust.backend.services.TrainServices;

public class App {

	public static void main(String[] args) {
		
		run(args[0]);
	}

	public static void run(String inputFile) {
		
		// String inputFile = commandLineArgs.get(0).split("=")[1];

		try
		{
			TrainBogieConfigurationRepository tainBogieConfigurationRepositories = new TrainBogieConfigurationRepository();
			TrainServices trainServices = new TrainServices(tainBogieConfigurationRepositories);
			trainServices.printArrivalAndDepartureOuput(inputFile);
		}
		catch(IOException e)
		{
			System.out.println(e.getMessage());
		}
		
		

	}

}
