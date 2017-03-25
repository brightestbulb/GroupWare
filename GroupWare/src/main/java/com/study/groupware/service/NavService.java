package com.study.groupware.service;

import java.util.Map;

public interface NavService {
	public Map<String, Object> myInfo(String param) throws Exception;
	public int stfPwUpdate(Map param) throws Exception;
}
