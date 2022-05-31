package com.geektrust.backend;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.geektrust.backend.repositories.TrainBogieConfigurationRepository;
import com.geektrust.backend.services.TrainServices;

public class App {

	public static void main(String[] args) {
		List<String> commandLineArgs = new LinkedList<>(Arrays.asList(args));
        String expectedSequence = "INPUT_FILE";
        String actualSequence = commandLineArgs.stream()
                .map(a -> a.split("=")[0])
                .collect(Collectors.joining("$"));
        if(expectedSequence.equals(actualSequence)){
            run(commandLineArgs);
        }
		
	}

	public static void run(List<String> commandLineArgs) {
		
		String inputFile = commandLineArgs.get(0).split("=")[1];

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
