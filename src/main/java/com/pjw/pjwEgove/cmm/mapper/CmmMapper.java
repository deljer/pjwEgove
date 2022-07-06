package com.pjw.pjwEgove.cmm.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper
public interface CmmMapper {

	List<Map<String, Object>> selectMenuInfo(Map<String, Object> param);


}
