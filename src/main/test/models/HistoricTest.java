package models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HistoricTest {

    @ParameterizedTest
    @MethodSource("settingUpHistoric")
    @DisplayName("Getting remaining Waste")
    void getRemainingWaste(Historic historic) {
        //Arrange
        //Act
        var response = historic.getRemainingWaste();
        //Assert
        var expectedResponse = gettingExpectedDouble(historic);
        assertEquals(expectedResponse,response);
    }


    @ParameterizedTest
    @MethodSource("settingUpHistoric")
    @DisplayName("Setting remaining waste")
    void setRemainingWaste(Historic historic) {
        //Arrange
        //Act
        historic.setRemainingWaste(13);
        //Assert
        assertEquals(13,historic.getRemainingWaste());
    }

    @ParameterizedTest
    @MethodSource("settingUpHistoric")
    @DisplayName("Getting Plastic Glass")
    void getPlasticGlass(Historic historic) {
        //Arrange
        //Act
        var response = historic.getPlasticGlass();
        //Assert
        double expectedResponse = gettingExpectedDouble(historic);
        assertEquals((expectedResponse>1250)?expectedResponse*0.3:expectedResponse*0.5,response);
    }

    @Test
    void setPlasticGlass() {
    }

    @DisplayName("Getting Paper")
    @ParameterizedTest
    @MethodSource("settingUpHistoric")
    void getPaper(Historic historic) {
        //Arrange
        //Act
        var response = historic.getPaper();
        //Assert
        double expectedResponse = gettingExpectedDouble(historic);
        assertEquals(expectedResponse*0.5,response);
    }

    @Test
    void setPaper() {
    }

    @DisplayName("Getting Metallic")
    @ParameterizedTest
    @MethodSource("settingUpHistoric")
    void getMetallic(Historic historic) {
        //Arrange
        //Act
        var response = historic.getMetallic();
        //Assert
        double expectedResponse = gettingExpectedDouble(historic);
        assertEquals((expectedResponse>1250)?expectedResponse*0.2:expectedResponse*0,response);
    }

    @Test
    void setMetallic() {
    }

    static List<Historic> settingUpHistoric() {

        List<Historic> historicList = new ArrayList<>();
        var above_METALLIC_THRESH =  new Historic(Location.A,1251.0);
        var below_METALLIC_THRESH =  new Historic(Location.B,1249.0);
        historicList.add(above_METALLIC_THRESH);
        historicList.add(below_METALLIC_THRESH);
        return historicList;
    };

    static Double gettingExpectedDouble(Historic historic) {
        return (historic.getLocation()== Location.A)?1251.0:1249.0;
    }
}