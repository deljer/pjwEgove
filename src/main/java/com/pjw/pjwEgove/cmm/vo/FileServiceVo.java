package com.pjw.pjwEgove.cmm.vo;

import egovframework.rte.psl.dataaccess.util.CamelUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileServiceVo {
	
	private String fileId;
	private String fileOrizinNm;
	private String fileNm;
	private String filePath;
	private long fileSize;
	
	
}
