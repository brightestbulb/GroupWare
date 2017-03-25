package com.study.groupware.daoimpl;

import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import com.study.groupware.dao.ScheduleDAO;
import com.study.groupware.vo.ScheduleVO;

@Repository
public class ScheduleDAOImpl implements ScheduleDAO {
	@Inject
	private SqlSession session;

	private static String namespace = "com.study.groupware.mapper.ScheduleMapper";

	@Override
	public List<ScheduleVO> selectSchedule(Map params) throws Exception {
		return session.selectList(namespace + ".selectSchedule", params);
	}

	@Override
	public int insertSchedule(ScheduleVO vo) throws Exception {
		return session.insert(namespace + ".insertSchedule", vo);
	}
	
	@Override
	public ScheduleVO readSchedule(Map params) throws Exception {
		return session.selectOne(namespace + ".readSchedule", params);
	}
	
	@Override
	public int updateSchedule(Map params) throws Exception {
		return session.update(namespace + ".updateSchedule", params);
	}
	
	@Override
	public int deleteSchedule(Map params) throws Exception {
		return session.delete(namespace + ".deleteSchedule", params);
	}
}
