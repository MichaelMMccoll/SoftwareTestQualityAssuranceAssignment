import models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.commons.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UtilsTest {
    private Utils util = new Utils();


    @ParameterizedTest
    @DisplayName("Finding Viable Centres With 3 options and has metallic")
    @CsvFileSource(resources = "/FindingViableCenters.csv")
    void findViableCentres(String centers, String location, Double initialWaste) {
        //Arrange
        var recyclingList = CreatingRecyclings(centers);
        var aa = new Historic(GettingLocation(location),initialWaste);

        //Act
        var response = util.findViableCentres(aa,recyclingList);

        //Assert
        assertEquals(response,recyclingList);
    }

    @ParameterizedTest
    @DisplayName("Finding optimal center with all 3 types")
    @CsvFileSource(resources = "/FindOptimalCenters.csv")
    void findOptimalCentre(String centers, String location, Double initialWaste, Integer arrayPlace) {
        //Arrange
        var recyclingList = CreatingRecyclings(centers);
        var aa = new Historic(GettingLocation(location),initialWaste);

        //Act
        var response = util.findOptimalCentre(aa,recyclingList);

        //Assert
        assertEquals(response,recyclingList.get(arrayPlace));
    }

    //Test crashes when no Recycling center is given
    //Should be changed to fail
    @Test
    @DisplayName("Finding optimal center with there are 0 types")
    void findOptimalCentre_When_C_0Centers() {
        //Arrange
        var recyclingList = CreatingRecyclings("");
        var aa = new Historic(Location.C,1251.0);
        var errorMessage = "";
        //Act
        //Assert
        assertThrows(NoSuchElementException.class, () -> util.findOptimalCentre(aa,recyclingList));
    }

    @ParameterizedTest
    @DisplayName("Calculates travel duration")
    @MethodSource("CalculateTravelDuration")
    void calculateTravelDuration(Recycling type, Historic location, Double expectedResponse)
    {
        //Arrange
        //Act
        var response = util.calculateTravelDuration(location,type);
        //Assert
        assertEquals(response, expectedResponse);
    }

    @ParameterizedTest
    @DisplayName("Calculates process duration")
    @MethodSource("CalculateProcessDuration")
    void calculateProcessDuration(Recycling type, Historic location, Double expectedResponse)
    {
        //Arrange
        //Act
        var response = util.calculateProcessDuration(location,type);
        //Assert
        assertEquals(response,expectedResponse);
    }


    //Rules youngest to oldest based on the order of the string
    static List<Recycling> CreatingRecyclings(String options) {
        List<Recycling> recyclingList = new ArrayList<>();

        if(StringUtils.isBlank(options)) {
            return recyclingList;
        }
        var optionList = options.toCharArray();

        var age = 1;
        for(char option : optionList)
        {
            recyclingList.add(GettingRecycling(option,age));
            age++;
        }
        return recyclingList;
    }

    static Recycling GettingRecycling(char option,Integer age)
    {
        return switch (option) {
            case 'A' -> new Alpha(Location.A, age);
            case 'B' -> new Beta(Location.B, age);
            case 'C' -> new Gamma(Location.C, age);
            default -> null;
        };
    }

    private static Stream<Arguments> CalculateTravelDuration(){
        return Stream.of(
                Arguments.of(new Alpha(Location.A,1), new Historic(Location.A,1251.0), 63.0),
                Arguments.of(new Alpha(Location.A,1), new Historic(Location.B,1251.0), 126.0),
                Arguments.of(new Alpha(Location.A,1), new Historic(Location.C,1251.0), 252.0)
        );
    }
    private static Stream<Arguments> CalculateProcessDuration(){
        return Stream.of(
          Arguments.of(new Alpha(Location.A,1), new Historic(Location.A,1251.0), 1251.0),
          Arguments.of(new Alpha(Location.A,1), new Historic(Location.B,1000.0), 1000.0),
          Arguments.of(new Alpha(Location.A,1), new Historic(Location.C,1000.0), 1000.0)
        );
    }

    static Location GettingLocation(String selected)
    {
        return switch (selected) {
            case "A" -> Location.A;
            case "B" -> Location.B;
            case "C" -> Location.C;
            default -> null;
        };
    }
}