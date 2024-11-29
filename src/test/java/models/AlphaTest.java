package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AlphaTest {
    private Alpha alpha;
    @BeforeEach
        public void beforeEachTest() { alpha = new Alpha(Location.A,10); }
    @DisplayName("Getting generation when Alpha")
    @Test
    void getGeneration() {
    //Arrange
    //Act
       var response = alpha.getGeneration();
    //Assert
        assertEquals("Alpha", response);
    }

    @DisplayName("Getting Alpha Rates")
    @Test
    void getRates() {
        //Arrange
        List<Double> alphaList = List.of(1.0, 1.0, 1.0);
        //Act
        var response = alpha.getRates();
        //Assert
        assertEquals(alphaList, response);
    }
}