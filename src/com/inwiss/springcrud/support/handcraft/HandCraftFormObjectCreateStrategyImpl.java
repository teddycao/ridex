/*
 * Created on 2005-11-27
 */
package com.inwiss.springcrud.support.handcraft;

import java.util.*;

import com.inwiss.springcrud.command.*;

/**
 * HandCraftFormObjectCreateStrategy接口的缺省实现。
 * 
 * <p>客户可以根据具体的需求，提供新的实现类，并配置到HandCraftMeta元数据中。
 * 
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public class HandCraftFormObjectCreateStrategyImpl implements HandCraftFormObjectCreateStrategy
{

    /**
     * @see com.inwiss.springcrud.support.handcraft.HandCraftFormObjectCreateStrategy#createFormObject(boolean, java.util.List, java.util.List, java.util.List)
     */
    public RecordSetCommand createFormObject(boolean formSubmission, List listData, List listFormData, List listTemplateData)
    {
        RecordSetCommand command = new RecordSetCommand();
        boolean emptyListData = listData == null || listData.size() == 0;
        boolean emptyListFormData = listFormData == null || listFormData.size() == 0;
        
        //数据列表中有数据，但ListFormData中没有数据，说明数据暂时不需要再进行修改（如已经提交，或已经审批通过）
        if ( !emptyListData && emptyListFormData )
        {
            command.setRecords(new RecordCommand[0]);
        }
        //数据列表中没有数据，需要根据listTemplateData来构造数据
        else if ( emptyListFormData )
        {
            command.setRecords(new RecordCommand[listTemplateData.size()]);
            for ( int i = 0, nSize = listTemplateData.size(); i < nSize; i++ )
            {
                command.getRecords()[i] = new RecordCommand();
                if ( !formSubmission )
                {
                    command.getRecords()[i].setMapContent((Map)listTemplateData.get(i));
                }
            }
        }
        //ListForm非空
        else
        {
            command.setRecords(new RecordCommand[listFormData.size()]);
            for ( int i = 0, nSize = listFormData.size(); i < nSize; i++ )
            {
                command.getRecords()[i] = new RecordCommand();
                if ( !formSubmission )
                {
                    command.getRecords()[i].setMapContent((Map)listFormData.get(i));
                }
            }
        }
        
        return command;
    }

}
