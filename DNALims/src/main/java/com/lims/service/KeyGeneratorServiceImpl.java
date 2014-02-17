/**
 * KeyGeneratorServiceImpl.java
 *
 * 2013-5-26
 */
package com.lims.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

/**
 * @author lizhihua
 *
 */
@Service("keyGeneratorService")
public class KeyGeneratorServiceImpl implements KeyGeneratorService {


	@Override
	public String getNextKey() {
		return UUID.randomUUID().toString();
	}

}
