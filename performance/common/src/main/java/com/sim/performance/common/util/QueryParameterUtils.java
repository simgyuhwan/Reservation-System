package com.sim.performance.common.util;

import static java.util.stream.Collectors.*;

import java.util.List;

/**
 * QueryParameterUtils.java
 * 쿼리 파라미터 유틸
 *
 * @author sgh
 * @since 2023.04.12
 */
public class QueryParameterUtils {

	public static String buildQueryString(List<QueryParameter> queryParameters) {
		return queryParameters.stream()
			.map(QueryParameter::toString)
			.collect(joining("&"));
	}
}
