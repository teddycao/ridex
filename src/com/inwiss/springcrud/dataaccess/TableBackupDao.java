/*
 * Created on 2005-9-13
 */
package com.inwiss.springcrud.dataaccess;

/**
 * 上传文件时使用的数据库访问接口。
 * 
 * @author wuzixiu
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public interface TableBackupDao {

	/**
	 * 备份参数表数据到备份表
	 * @param sql 用于备份数据的SQL语句
	 * @return 备份结果，是否成功
	 */
	public void backupTable(String sql);
}
