package com.nhnacademy.aiot;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class BuildingTest {

  @Test
  void createBuilding_BurjKhalifa() {

    Building burjKhalifa = Building.builder()
        .name("Burj Khalifa")
        .location("Dubai, United Arab Emirates")
        .height(828)
        .floorCount(165)
        .startedDate("2004-09-21")
        .completedDate("2009-10-01")
        .status(Status.Completed)
        .build();

    assertAll(
        () ->
        {
          assertNotNull(burjKhalifa);
          assertNotNull(burjKhalifa.getName());
          assertNotNull(burjKhalifa.getStatus());
          assertEquals(burjKhalifa.getStatus(), Status.Completed);
          assertEquals(burjKhalifa.getStartedDate().getClass(), LocalDateTime.class);
        }
    );
  }

  @Test
  void createBuilding_LotteTower() {

    Building busanLotteTower = Building.builder()
        .name("z")
        .location("Busan, South Korea")
        .height(342)
        .floorCount(73)
        .startedDate("2009-03-02")
        .completedDate(null)
        .status(Status.UnderConstruction)
        .build();

    assertAll(
        () ->
        {
          assertNotNull(busanLotteTower);
          assertNotNull(busanLotteTower.getName());
          assertNotNull(busanLotteTower.getStatus());
          assertEquals(busanLotteTower.getStatus(), Status.UnderConstruction);
          assertThrows(BuildingUnderConstructionException.class, ()-> busanLotteTower.getCompletedDate());
        }
    );
  }
}