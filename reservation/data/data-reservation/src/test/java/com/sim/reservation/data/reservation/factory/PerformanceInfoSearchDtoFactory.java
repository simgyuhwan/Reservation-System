package com.sim.reservation.data.reservation.factory;

import com.sim.reservation.data.reservation.dto.PerformanceInfoSearchDto;
import java.time.LocalDate;

/**
 * PerformanceInfoSearchDtoFactory.java Class 설명을 작성하세요.
 *
 * @author sgh
 * @since 2023.05.15
 */
public class PerformanceInfoSearchDtoFactory {

  public static PerformanceInfoSearchDto createNullPerformanceSearchDto() {
    return PerformanceInfoSearchDto.builder().build();
  }

  public static PerformanceInfoSearchDto createPerformanceSearchDtoByName(String name) {
    return PerformanceInfoSearchDto.builder()
        .name(name)
        .build();
  }

  public static PerformanceInfoSearchDto createPerformanceSearchDtoByPlace(String place) {
    return PerformanceInfoSearchDto.builder()
        .place(place)
        .build();
  }

  public static PerformanceInfoSearchDto createPerformanceSearchDtoByType(String type) {
    return PerformanceInfoSearchDto.builder()
        .type(type)
        .build();
  }

  public static PerformanceInfoSearchDto createPerformanceSearchDtoByStartDate(
      LocalDate startDate) {
    return PerformanceInfoSearchDto.builder()
        .startDate(startDate)
        .build();
  }

  public static PerformanceInfoSearchDto createPerformanceSearchDtoByEndDate(LocalDate endDate) {
    return PerformanceInfoSearchDto.builder()
        .endDate(endDate)
        .build();
  }
}
