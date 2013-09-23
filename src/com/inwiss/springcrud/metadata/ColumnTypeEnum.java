/*
 * Created on 2005-9-22
 */
package com.inwiss.springcrud.metadata;

/**
 * 列类型枚举接口。
 * 
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public interface ColumnTypeEnum
{
    /**字符串类型*/
    public final String STR_TYPE = "String";
    
    /**数值类型*/
    public final String NUMBER_TYPE = "Number";
    
    
    /**Radio型输入方式*/
    public final static String RADIO = "radio";
    
    /**下拉框输入方式*/
    public final static String DROPDOWN = "dropdown";
    
    public enum ViewType {
    	textfield,checkbox , combo ,datefield,radio;
    }
}
