package com.sim.event.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sim.event.store.domain.SagaState;

public interface SagaStateRepository extends JpaRepository<SagaState, String> {
}
