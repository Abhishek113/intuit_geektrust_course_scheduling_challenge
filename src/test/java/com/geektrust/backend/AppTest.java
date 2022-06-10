package com.geektrust.backend;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("AppTest")
@ExtendWith(MockitoExtension.class)
class AppTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    // private static final App app = new App();
    
    @BeforeEach
    public void setUp()
    {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    @DisplayName("run method should display corret output 1")
    public void runMethodShouldDisplayCorrectOutput1()
    {
        String expectedOutput = "OFFERING-DATASCIENCE-BOB\nREG-COURSE-WOO-DATASCIENCE ACCEPTED\nREG-COURSE-ANDY-DATASCIENCE ACCEPTED\nREG-COURSE-ANDY-DATASCIENCE ANDY@GMAIL.COM OFFERING-DATASCIENCE-BOB DATASCIENCE BOB 05062022 CONFIRMED\nREG-COURSE-WOO-DATASCIENCE WOO@GMAIL.COM OFFERING-DATASCIENCE-BOB DATASCIENCE BOB 05062022 CONFIRMED";

        String inputFile = "sample_input/input2.txt";

        String[] args = {inputFile};

        App.run(args);

        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());

    }

    @Test
    @DisplayName("run method should display corret output 2")
    public void runMethodShouldDisplayCorrectOutput2()
    {
        String expectedOutput = "OFFERING-PYTHON-JOHN\n" + "REG-COURSE-WOO-PYTHON ACCEPTED\n"
                                + "REG-COURSE-ANDY-PYTHON ACCEPTED\n" + "REG-COURSE-BOBY-PYTHON ACCEPTED\n"
                                + "REG-COURSE-BOBY-PYTHON CANCEL_ACCEPTED\n"
                                + "REG-COURSE-ANDY-PYTHON ANDY@GMAIL.COM OFFERING-PYTHON-JOHN PYTHON JOHN 05062022 CONFIRMED\n"
                                + "REG-COURSE-WOO-PYTHON WOO@GMAIL.COM OFFERING-PYTHON-JOHN PYTHON JOHN 05062022 CONFIRMED";

    
        String inputFile = "sample_input/input3.txt";

        String[] args = {inputFile};

        App.run(args);

        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());

    }

    @Test
    @DisplayName("run method should display corret output 3")
    public void runMethodShouldDisplayCorrectOutput3()
    {
        String expectedOutput = "OFFERING-DATASCIENCE-BOB\n" + "REG-COURSE-ANDY-DATASCIENCE ACCEPTED\n"
                                + "REG-COURSE-WOO-DATASCIENCE ACCEPTED\n" + "COURSE_FULL_ERROR\n"
                                + "REG-COURSE-ANDY-DATASCIENCE ANDY@GMAIL.COM OFFERING-DATASCIENCE-BOB DATASCIENCE BOB 05062022 CONFIRMED\n"
                                + "REG-COURSE-WOO-DATASCIENCE WOO@GMAIL.COM OFFERING-DATASCIENCE-BOB DATASCIENCE BOB 05062022 CONFIRMED";
        
        
        String inputFile = "sample_input/input4.txt";

        String[] args = {inputFile};

        App.run(args);

        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());

    }

    @AfterEach
    public void tearDown()
    {
        System.setOut(standardOut);
    }
}
