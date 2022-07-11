package com.pjw.pjwEgove.cmm.mapper;

import java.util.List;
import java.util.Map;

import com.pjw.pjwEgove.cmm.vo.FileServiceVo;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper
public interface FileServiceMapper {

	int insertFileUpload(List<FileServiceVo> fileServiceList);

	List<Map<String, Object>> uploadFileList(Map<String, Object> param);
	
}
