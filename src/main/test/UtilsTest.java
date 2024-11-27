import models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.platform.commons.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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
    @CsvFileSource(resources = "/CalculateTravelDuration.csv")
    void calculateTravelDuration(Character type, String location, Double initialWaste, Double expectedResponse)
    {
        //Arrange
        var recycle = GettingRecycling(type,1);
        var aa = new Historic(GettingLocation(location),initialWaste);
        //Act
        var response = util.calculateTravelDuration(aa,recycle);
        //Assert
        assertEquals(response, expectedResponse);
    }

    @ParameterizedTest
    @DisplayName("Calculates process duration")
    @CsvFileSource(resources = "/CalculateProcessDuration.csv")
    void calculateProcessDuration(Character type, String location, Double initialWaste, Double expectedResponse)
    {
        //Arrange
        var recycle = GettingRecycling(type,1);
        var aa = new Historic(GettingLocation(location),initialWaste);
        //Act
        var response = util.calculateProcessDuration(aa,recycle);
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