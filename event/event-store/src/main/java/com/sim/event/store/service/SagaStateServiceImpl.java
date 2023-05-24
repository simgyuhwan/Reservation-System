package com.sim.event.store.service;

import org.springframework.stereotype.Service;

import com.sim.event.store.domain.SagaState;
import com.sim.event.store.repository.SagaStateRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SagaStateServiceImpl implements SagaStateService{
	private final SagaStateRepository sagaStateRepository;

	@Override
	public void saveSagaState(SagaState sagaState) {
		sagaStateRepository.save(sagaState);
	}
}
