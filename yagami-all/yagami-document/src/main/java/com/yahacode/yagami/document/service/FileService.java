package com.yahacode.yagami.document.service;

import java.util.List;

import com.yahacode.yagami.base.BaseService;
import com.yahacode.yagami.base.BizfwServiceException;
import com.yahacode.yagami.document.model.Document;

/**
 * 文件服务接口
 * 
 * @copyright THINKEQUIP
 * @author zengyongli
 * @date 2017年3月21日
 */
public interface FileService extends BaseService<Document> {

	/**
	 * 新增文件
	 * 
	 * @param document
	 *            文件
	 * @return 主键
	 * @throws BizfwServiceException
	 */
	public String addFile(Document document) throws BizfwServiceException;

	/**
	 * 修改文件（文件名与扩展名）
	 * 
	 * @param document
	 *            文件
	 * @throws BizfwServiceException
	 */
	public void modifyFile(Document document) throws BizfwServiceException;

	/**
	 * 更新文件版本
	 * 
	 * @param document
	 *            文件
	 * @return 文件主键
	 * @throws BizfwServiceException
	 */
	public String updateFile(Document document) throws BizfwServiceException;

	/**
	 * 下载文件
	 * 
	 * @param document
	 *            文件
	 * @throws BizfwServiceException
	 */
	public void downloadFile(Document document) throws BizfwServiceException;

	/**
	 * 删除文件
	 * 
	 * @param document
	 *            文件
	 * @throws BizfwServiceException
	 */
	public void deleteFile(Document document) throws BizfwServiceException;

	/**
	 * 获取文档历史版本信息
	 * 
	 * @param document
	 *            文件
	 * @return 历史文件列表
	 * @throws BizfwServiceException
	 */
	public List<Document> getHistoryFileList(Document document) throws BizfwServiceException;
}
