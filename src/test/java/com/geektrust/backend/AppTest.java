package com.geektrust.backend;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("AppTest")
class AppTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp()
    {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    public void runMathosShouldPrintOutputCorrectly()
    {
        String expectedOuput = "ARRIVAL TRAIN_A ENGINE HYB NGP ITJ\nARRIVAL TRAIN_B ENGINE NJP PTA\nDEPARTURE TRAIN_AB ENGINE ENGINE NJP PTA ITJ NGP";
        List<String> commandLineArgs = List.of("INPUT_FILE=input.txt");
        App.run(commandLineArgs);
        Assertions.assertEquals(expectedOuput, outputStreamCaptor.toString().trim());
    }

    @AfterEach
    public void tearDown()
    {
        System.setOut(standardOut);
    }
    

}
