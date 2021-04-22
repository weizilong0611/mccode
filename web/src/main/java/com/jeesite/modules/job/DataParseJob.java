package com.jeesite.modules.job;

import java.io.File;
import java.util.List;
import java.util.function.Consumer;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.batch.BatchProperties.Job;

import com.jeesite.common.codec.Md5Utils;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import com.jeesite.common.utils.SpringUtils;
import com.jeesite.modules.geo.dataParse.DataParse;
import com.jeesite.modules.geo.entity.McGeofolder;
import com.jeesite.modules.geo.service.McGeofolderService;
import com.jeesite.modules.mc.entity.McTextfiledata;
import com.jeesite.modules.mc.service.McTextfiledataService;

public class DataParseJob  {
	
	
	public static void doJob() {
		McTextfiledataService mcTextfiledataService = SpringUtils.getBean("mcTextfiledataService");
		McGeofolderService mcGeofolderService = SpringUtils.getBean("mcGeofolderService");
		
		McTextfiledata mcTextfiledata = new McTextfiledata();
		mcTextfiledata.getSqlMap().getWhere().and("filemd5", QueryType.EQ,"");
		mcTextfiledataService.delete(mcTextfiledata);
		
		
		McGeofolder mcGeofolder = new McGeofolder();
		//mcGeofolder.setFileType("xls");
		mcGeofolder.getSqlMap().getWhere().and("file_type", QueryType.NE, "folder");//.and("tree_code", QueryType.EQ, "1287933495755292672");
		List<McGeofolder> mc = mcGeofolderService.findList(mcGeofolder);
		mc.forEach(new Consumer<McGeofolder>() {

			@Override
			public void accept(McGeofolder folder) {
				List<String> path = DataParse.getPathList(folder);
				
				path.forEach(new Consumer<String>() {
					@Override
					public void accept(String t) {
					
						String md5 = Md5Utils.md5File(new File(t));
						String md5Old = mcTextfiledataService.findMd5ByPath(t);
						
					
						if(StringUtils.isEmpty(md5Old) || !md5.equals(md5Old)) {
							
							if(!StringUtils.isEmpty(md5Old)) {
								System.out.println("文件发生修改");
								mcTextfiledataService.deleteByPath(t); 
							}else {
								mcTextfiledataService.deleteByPath(t); 
								System.out.println("新数据，准备push到数据库");	
							}
							try {
								t = t.toLowerCase();
								if(t.endsWith(".xls") || t.endsWith(".xlsx")) {		//判断是否为excel，还需判断csv等等等
									List<McTextfiledata> list = DataParse.parseExcel(t, folder);
									mcTextfiledataService.insertBatch(list);	
								}
							} catch (Exception e) {
								System.out.println("异常！" + t ); 
								e.printStackTrace();
							}
						}else if(md5.equals(md5Old)) {
							System.out.println("文件未修改");
						}
					}
				});
			}
		});
		
		System.out.println();
	}
	
	 
}
