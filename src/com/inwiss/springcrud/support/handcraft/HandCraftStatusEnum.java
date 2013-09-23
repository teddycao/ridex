/*
 * Created on 2005-11-27
 */
package com.inwiss.springcrud.support.handcraft;

/**
 * 描述手工补录数据状态的枚举接口。
 * 
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public interface HandCraftStatusEnum
{
    /**初始态*/
    public String INIT_STATE = "0";
    
    /**已保存态*/
    public String SAVE_STATE = "1";
    
    /**已提交审批态*/
    public String SUBMIT_STATE = "2";
    
    /**审批通过态*/
    public String AUDIT_PASSED_STATE = "3";
    
    /**审批退回态*/
    public String AUDIT_REJECT_STATE = "4";
}
