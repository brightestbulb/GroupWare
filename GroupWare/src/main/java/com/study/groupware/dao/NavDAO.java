package com.study.groupware.dao;

import java.util.Map;

public interface NavDAO {
	public Map<String, Object> myInfo(String param) throws Exception;
	public int stfPwUpdate(Map param) throws Exception;
}
