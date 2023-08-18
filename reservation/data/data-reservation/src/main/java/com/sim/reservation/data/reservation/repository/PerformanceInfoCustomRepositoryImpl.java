package com.sim.reservation.data.reservation.repository;

import static com.sim.reservation.data.reservation.domain.QPerformanceInfo.performanceInfo;
import static com.sim.reservation.data.reservation.domain.QPerformanceSchedule.performanceSchedule;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sim.reservation.data.reservation.domain.PerformanceInfo;
import com.sim.reservation.data.reservation.dto.PerformanceInfoDto;
import com.sim.reservation.data.reservation.dto.PerformanceInfoSearchDto;
import com.sim.reservation.data.reservation.type.PerformanceType;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * PerformanceCustomRepositoryImpl.java 공연 관련 QueryDsl Repository
 *
 * @author sgh
 * @since 2023.04.20
 */
@Repository
@RequiredArgsConstructor
public class PerformanceInfoCustomRepositoryImpl implements PerformanceInfoCustomRepository {

  private final JPAQueryFactory queryFactory;

  @Transactional
  @Override
  public Page<PerformanceInfoDto> selectPerformanceReservation(
      PerformanceInfoSearchDto performanceSearchDto,
      Pageable pageable) {
    List<PerformanceInfo> performanceInfos = queryFactory.selectFrom(performanceInfo)
        .join(performanceInfo.performanceSchedules, performanceSchedule)
        .fetchJoin()
        .where(typeEq(performanceSearchDto.getType()),
            nameLike(performanceSearchDto.getName()),
//            includeSearchStartDate(performanceSearchDto.getStartDate()),
//            includeSearchEndDate(performanceSearchDto.getEndDate()),
            includeSearchRange(performanceSearchDto.getStartDate(),
                performanceSearchDto.getEndDate()),
            startTimeEq(performanceSearchDto.getStartTime()),
            placeLike(performanceSearchDto.getPlace()))
        .orderBy(performanceSchedule.startDate.asc(), performanceInfo.name.asc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    List<PerformanceInfoDto> performanceInfoDtos = performanceInfos.stream()
        .map(PerformanceInfoDto::from).toList();

    return new PageImpl<>(performanceInfoDtos, pageable, performanceInfoDtos.size());
  }

  private BooleanExpression typeEq(String type) {
    if (type != null) {
      return performanceInfo.type.eq(PerformanceType.findByType(type));
    }
    return null;
  }

  private BooleanExpression nameLike(String name) {
    if (name != null) {
      return performanceInfo.name.like("%" + name + "%");
    }
    return null;
  }

  private BooleanExpression includeSearchStartDate(LocalDate searchStart) {
    if (searchStart != null) {
      return performanceSchedule.startDate.goe(searchStart)
          .and(performanceSchedule.endDate.loe(searchStart));
    }
    return null;
  }

  private BooleanExpression includeSearchEndDate(LocalDate searchEndDate) {
    if (searchEndDate != null) {
      return performanceSchedule.startDate.goe(searchEndDate)
          .and(performanceSchedule.endDate.loe(searchEndDate));
    }
    return null;
  }

  private BooleanExpression includeSearchRange(LocalDate searchStartDate, LocalDate searchEndDate) {
    if (searchEndDate != null && searchStartDate == null) {
      return performanceSchedule.startDate.goe(searchEndDate)
          .and(performanceSchedule.endDate.loe(searchEndDate));
    }

    if (searchEndDate == null && searchStartDate != null) {
      return performanceSchedule.startDate.goe(searchStartDate)
          .and(performanceSchedule.endDate.loe(searchStartDate));
    }

    if (searchEndDate != null && searchStartDate != null) {
      return (performanceSchedule.startDate.goe(searchEndDate)
          .and(performanceSchedule.endDate.loe(searchEndDate))).or(
          performanceSchedule.startDate.goe(searchStartDate)
              .and(performanceSchedule.endDate.loe(searchStartDate))
      );
    }
    return null;
  }

  private BooleanExpression startTimeEq(LocalTime startTime) {
    if (startTime != null) {
      return performanceSchedule.startTime.eq(startTime);
    }
    return null;
  }

  private BooleanExpression placeLike(String place) {
    if (place != null) {
      return performanceInfo.place.like("%" + place + "%");
    }
    return null;
  }
}
