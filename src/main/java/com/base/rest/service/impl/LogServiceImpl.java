package com.base.rest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.rest.entities.Log;
import com.base.rest.repositories.LogRepository;
import com.base.rest.service.interfaces.LogService;

@Service
@Transactional(readOnly = true)
public class LogServiceImpl implements LogService {
	
	@Autowired
	private LogRepository logRepository;

	@Override
	public List<Log> findAll() {
		
		return logRepository.findAll();
	}

	@Transactional
	@Override
	public void save(Log log) {
		
		logRepository.save(log);
	}

}
