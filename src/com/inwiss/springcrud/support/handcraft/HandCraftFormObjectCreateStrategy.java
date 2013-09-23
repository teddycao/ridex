/*
 * Created on 2005-11-27
 */
package com.inwiss.springcrud.support.handcraft;

import com.inwiss.springcrud.command.*;
import java.util.*;

/**
 * 用于手工补录的表单对象构造策略接口。
 * 
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public interface HandCraftFormObjectCreateStrategy
{
    /**
     * 创建表单对象。
     * @param formSubmission 是否为表单提交动作
     * @param listData 一个上下文环境中牵涉到的补录数据的全集
     * @param listFormData 用于构造表单对象数据 
     * @param listTemplateData 用于构造表单对象的模板数据
     * @return 表单对象
     */
    public RecordSetCommand createFormObject(boolean formSubmission, List listData, List listFormData, List listTemplateData);
}
