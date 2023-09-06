package com.sim.reservation.data.reservation.support;

import com.sim.reservation.data.reservation.config.QueryDslTestConfig;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

/**
 * Created by Gyuhwan
 */
@DataJpaTest
@Import(QueryDslTestConfig.class)
public abstract class RepositoryTestSupport {

}
