import models.Location;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Stream;

class MainTest {
    private final String[] args = {};

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final List<String> exit = new ArrayList<String>(
                                                    Arrays.asList(
                                                            "Exiting...\r",
                                                            "End\r"
                                                    )
    );
    private final List<String> showOptions = new ArrayList<String>(
                                                            Arrays.asList(
                                                                    "Options (enter number to select):\r",
                                                                    "1. Configure/Run Scenario.\r",
                                                                    "2. About.\r",
                                                                    "3. Exit.\r"
                                                            )

    );
    private final List<String> collectLocation = new ArrayList<>(
                                                            Arrays.asList(
                                                                    "Enter a location (A, B, or C):\r"
                                                            )
    );
    private final String collectGeneration = "Enter the generation of the recycling centre (Alpha, Beta, or Gamma):\r";
    private final String collectGeneration_error = "Invalid choice. Please try again.\r";
    private final List<String> collectLocation_error = new ArrayList<>(
            Arrays.asList(
                    "Invalid choice. Peeas try again.\r"
            )
    );
    private final List<String> showAbout = new ArrayList<String>(
                                                        Arrays.asList(
                                                            "About:\r",
                                                            "- This is a prototype of the Landfill Labs Worker Service.\r",
                                                            "- Use it to configure and run a single waste management scenario.\r",
                                                            "- To run multiple scenarios, you should run the aplicato multiple times.\r",
                                                            "- If you're confused, you may wish to consult the application specification or contact Four Walls Software.\r"));

    @BeforeEach
    public void setUp() {
        // Redirect System.out to a ByteArrayOutputStream
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void restoreSystemOut() {
        // Restore the original System.out after each test
        System.setOut(originalOut);
    }

    @Test
    void Enter_About_Exit_002(){
        //Arrange
        var expectedOutput = Stream.of(showAbout,showOptions,exit)
                .flatMap(Collection::stream)
                .toList();
        String input ="2 3";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        //Act
            Main.main(args);
        //Assert
        var output = Arrays.stream(outputStream.toString().split("\n")).toList();
        Assertions.assertTrue(output.containsAll(expectedOutput));
    }

    @Test
    void Enter_And_Exit_001() {
    //Arrange
        var expectedOutput = Stream.of(showOptions,exit)
                .flatMap(Collection::stream)
                .toList();
        String input = "3";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
    //Act
        Main.main(args);
    //Assert
        var output = Arrays.stream(outputStream.toString().split("\n")).toList();
        Assertions.assertTrue(output.containsAll(expectedOutput));
    }

    @ParameterizedTest
    @MethodSource("collectLocation")
    void Collect_Location_A_to_C_003_to_005(String input, Location location) throws Exception {
        //Arrange
        var showOptionsMethod = Main.class.getDeclaredMethod("collectLocation");
        showOptionsMethod.setAccessible(true);

        var in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        //Act
        var response = (Location) showOptionsMethod.invoke(null);

        //Assert
        Assertions.assertEquals(location, response);
        var output = Arrays.stream(outputStream.toString().split("\n")).toList();
        Assertions.assertTrue(output.containsAll(collectLocation));
    }

    @Test
    void Collect_Location_Invalid_Input_006() throws Exception {
        //Arrange
        var expectedOutput = Stream.of(collectLocation,
                                       collectLocation_error)
                .flatMap(Collection::stream)
                .toList();
        var showOptionsMethod = Main.class.getDeclaredMethod("collectLocation");
        showOptionsMethod.setAccessible(true);

        var input = "D C";
        var in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        //Act
        var response = (Location) showOptionsMethod.invoke(null);

        //Assert
        Assertions.assertEquals(Location.C, response);
        var output = Arrays.stream(outputStream.toString().split("\n")).toList();
        Assertions.assertTrue(output.containsAll(expectedOutput));
    }

    @ParameterizedTest
    @MethodSource("collectGeneration")
    void Collect_Generation_Alpha_to_Gamma_007_to_009(String input) throws Exception {
        //Arrange
        var showOptionsMethod = Main.class.getDeclaredMethod("collectGeneration");
        showOptionsMethod.setAccessible(true);

        var in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        //Act
        var response = (String) showOptionsMethod.invoke(null);

        //Assert
        Assertions.assertEquals(input, response);
        var output = Arrays.stream(outputStream.toString().split("\n")).toList();
        Assertions.assertTrue(output.contains(collectGeneration));
    }

    @Test
    void CollectGenerationTest_With_Wrong_Input() throws Exception {
        //Arrange
        var expectedOutput = Stream.of(collectGeneration,
                                       collectGeneration_error)
                .toList();
        var showOptionsMethod = Main.class.getDeclaredMethod("collectGeneration");
        showOptionsMethod.setAccessible(true);

        var input = "D Alpha";
        var in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        //Act
        var response = (String) showOptionsMethod.invoke(null);

        //Assert
        Assertions.assertEquals("Alpha", response);
        var output = Arrays.stream(outputStream.toString().split("\n")).toList();
        Assertions.assertTrue(output.containsAll(expectedOutput));
    }


    private static Stream<Arguments> collectLocation() {
        return Stream.of(
                Arguments.of("A",Location.A),
                Arguments.of("B",Location.B),
                Arguments.of("C",Location.C));
    }
    private static Stream<Arguments> collectGeneration() {
        return Stream.of(
                Arguments.of("Alpha"),
                Arguments.of("Beta"),
                Arguments.of("Gama"));
    }
}