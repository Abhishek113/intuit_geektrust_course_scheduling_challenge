package com.geektrust.backend.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;

import com.geektrust.backend.dtos.MergedTrainDto;
import com.geektrust.backend.entites.Train;
import com.geektrust.backend.repositories.TrainBogieConfigurationRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("TrainServicesTest")
@ExtendWith(MockitoExtension.class)
public class TrainServicesTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp()
    {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    public void createTainShouldReturnValidTrainObject() 
    {
        try {
            TrainBogieConfigurationRepository trainBogieConfigurationRepository = new TrainBogieConfigurationRepository();
            TrainServices trainService = new TrainServices(trainBogieConfigurationRepository);
            String tarinBogieString = "TRAIN_A ENGINE NDL NDL KRN GHY SLM NJP NGP BLR";
            String[] tokens = tarinBogieString.split(" ");
            String trainName = "TRAIN_A";
            Integer numberOfBogies = tokens.length - 1;

        
            Train train = trainService.createTain(tarinBogieString);
            
            Assertions.assertEquals(trainName, train.getTrainName());
            Assertions.assertEquals(numberOfBogies, train.getBogies().size());
            
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        catch(URISyntaxException e)
        {
            System.out.println(e.getMessage());
        }
    }


    @Test
    public void mergerTrainShouldMergeTwoTrainesSuccessfully()
    {
        String expectedOutput = "DEPARTURE TRAIN_AB ENGINE ENGINE NJP PTA ITJ NGP";
        
        try {
            TrainBogieConfigurationRepository trainBogieConfigurationRepository = new TrainBogieConfigurationRepository();
            TrainServices trainService = new TrainServices(trainBogieConfigurationRepository);
            Train trainA = trainService.createTain("TRAIN_A ENGINE SLM BLR KRN HYB SLM NGP ITJ");
            Train trainB = trainService.createTain("TRAIN_B ENGINE SRR MAO NJP PNE PTA");

            Train trainAB = trainService.mergeTwoTrains(trainA, trainB);
            
            MergedTrainDto mergedTrainDto = new MergedTrainDto(trainAB);
            System.out.println(mergedTrainDto);
            Assertions.assertEquals(expectedOutput, mergedTrainDto.toString().trim());

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        catch(URISyntaxException e)
        {
            System.out.println(e.getMessage());
        }


    }

    @Test
    @DisplayName("printArrivalAndDepartureOuput should print journey ended message")
    public void printArrivalAndDepartureOuputShouldPrintJourneyEndedIfNobogieLeft()
    {
        String expectedOuptut = "ARRIVAL TRAIN_A ENGINE\nARRIVAL TRAIN_B\nJOURNEY_ENDED";

        try {
            TrainBogieConfigurationRepository trainBogieConfigurationRepository = new TrainBogieConfigurationRepository();
            TrainServices trainService = new TrainServices(trainBogieConfigurationRepository);
            
            trainService.printArrivalAndDepartureOuput("inputJourneyEnd.txt");
            Assertions.assertEquals(expectedOuptut, outputStreamCaptor.toString().trim());
            

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        catch(URISyntaxException e)
        {
            System.out.println(e.getMessage());
        }

    }

    @AfterEach
    public void tearDown()
    {
        System.setOut(standardOut);
    }

    

    
}
