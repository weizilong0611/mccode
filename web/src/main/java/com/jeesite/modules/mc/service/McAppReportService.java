/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.mc.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.QCrudService;
import com.jeesite.modules.mc.entity.McAppReport;
import com.jeesite.modules.mc.dao.McAppReportDao;
import com.jeesite.modules.file.utils.FileUploadUtils;

/**
 * app报告下载Service
 * @author fidelzhang
 * @version 2020-08-21
 */
@Service
@Transactional(readOnly=true)
public class McAppReportService extends QCrudService<McAppReportDao, McAppReport> {
	
	/**
	 * 获取单条数据
	 * @param mcAppReport
	 * @return
	 */
	@Override
	public McAppReport get(McAppReport mcAppReport) {
		return super.get(mcAppReport);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param mcAppReport
	 * @return
	 */
	@Override
	public Page<McAppReport> findPage(Page<McAppReport> page, McAppReport mcAppReport) {
		return super.findPage(page, mcAppReport);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param mcAppReport
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(McAppReport mcAppReport) {
		super.save(mcAppReport);
		// 保存上传图片
		FileUploadUtils.saveFileUpload(mcAppReport.getId(), "mcAppReport_image");
		// 保存上传附件
		FileUploadUtils.saveFileUpload(mcAppReport.getId(), "mcAppReport_file");
	}
	
	/**
	 * 更新状态
	 * @param mcAppReport
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(McAppReport mcAppReport) {
		super.updateStatus(mcAppReport);
	}
	
	/**
	 * 删除数据
	 * @param mcAppReport
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(McAppReport mcAppReport) {
		super.delete(mcAppReport);
	}
	
}