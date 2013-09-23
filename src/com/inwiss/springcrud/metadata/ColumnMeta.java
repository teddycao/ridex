/*
 * Created on 2005-8-1
 */
package com.inwiss.springcrud.metadata;

import java.util.Map;

import com.inwiss.springcrud.support.ContextRelativeStringGenerator;
import com.inwiss.springcrud.support.RequestContextStringRetriever;
import com.inwiss.springcrud.support.dictionary.DictFactory;
import com.inwiss.springcrud.validation.CrudValidateRule;


/**
 * 支持CRUD操作的模型中的列的定义元数据类。
 * 
 * @author Raidery
 */
public class ColumnMeta
{
    /**列对应的标题*/
    private String title = null;
    
    /**取出的结果集的列名*/
    private String colName = null;
    
    /**表达式，可以是COL1+COL2这样的形式*/
    private String colExpression = null;
    
    /**字段类型，目前仅有String和Number型*/
    private String type  = ColumnTypeEnum.STR_TYPE;
    
    /**对应Extjs的显示类*/
    private String viewType  = "textfield";
    

	/**用户输入该字段值时的提示信息*/
    private String hint = null;
    
    /**输入选择View的名称*/
    private String inputValueSelectViewName;
    
    /**是否为主键，用于拼接条件*/
    private boolean primaryKey = false;
    
    /**是否为可空字段，这个属性不与校验相关联，框架仅仅会根据该值展现相应的提示信息*/
    private boolean nullable = true;
    
    /**在列表界面中是否展现*/
    private boolean showInList = true;
    
    /**展现时是否应用字典替换*/
    private boolean needDictReplace = true;
    
    /**在新增表单中是否展现*/
    private boolean showInAddForm = true;
    
    /**在编辑表单中是否展现*/
    private boolean showInEditForm = true;
    
    /**在编辑界面中是否可编辑*/
    private boolean editable = true;
    
    /**新增记录时是否可以输入，一般用于使用业务规则生成一个备选值的情形*/
    private boolean inputable = true;
    
    /**是不是引申字段，比如非表中字段，或者用做其他用途的字段（仅仅是为了字典替换）*/
    private boolean derived = false;
    
    /**在有输入选择View的时候，还是否允许用户直接手工输入*/
    private boolean directInputable = true;
    
    /**新增数据时使用的备选值生成器*/
    private RequestContextStringRetriever candidateValueGenerator4Add;
    
    /**编辑数据时使用的备选值生成器*/
    private RequestContextStringRetriever candidateValueGenerator4Edit;
    
    /**新增数据持续化之前的最终值生成器*/
    private ContextRelativeStringGenerator ultimateValueGenerator4Add; 
    
    /**编辑数据持续化之前的最终值生成器*/
    private ContextRelativeStringGenerator ultimateValueGenerator4Edit; 
    
    /**用于批量更新的值生成器*/
    private RequestContextStringRetriever valueGenerator4UpdateBatch;
    
    /**展现使用的模式，如针对数字的格式化，或者取出一个较长字段值的一部分*/
    private String dispPattern = null;
    
    /**字段取值对应的字典*/
    private String dictName = null;
    
    /**字典工厂，外界获取的dictMap（即通过getDictMap()方法）就是通过dictFactory即时获取的*/
    private DictFactory dictFactory;
    
    /**输入方式，如是下拉框，还是radio box*/
    private String inputManner = ColumnTypeEnum.DROPDOWN;
    
    /**本列是否作为用户可使用的过滤条件*/
    private boolean useAsDataFilter = true;
    
    /**是否应用模糊匹配*/
    private boolean applyLike = true;
    
    /**Extjs 对应的json格式配置{allowBlank:false,blankText:'请输入'}*/
    private String config = null;
    
    private boolean allowBlank = true;
    
    private String value = null;
    
    private boolean readOnly = false;
    


	/**Extjs 对应的Ext.grid.ColumnModelExt中 renderer一般用于Grid中对列模型的渲染上 其详细信息记录在 Ext.grid.ColumnModel 中的setRenderer( Number col, Function fn ) 方法里
	renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){

	}*/
    private String renderer;
    

	/**数据校验规则*/
    private CrudValidateRule[] validationRules;
    
    
    /**
     * @return Returns the applyLike.
     */
    public boolean isApplyLike()
    {
        return applyLike;
    }
    /**
     * @param applyLike The applyLike to set.
     */
    public void setApplyLike(boolean applyLike)
    {
        this.applyLike = applyLike;
    }

    /**
     * @return Returns the colExpression.
     */
    public String getColExpression()
    {
        return colExpression;
    }
    
    /**
     * @return Returns the hint.
     */
    public String getHint()
    {
        return hint;
    }
    /**
     * @param hint The hint to set.
     */
    public void setHint(String hint)
    {
        this.hint = hint;
    }
    /**
     * @param colExpression The colExpression to set.
     */
    public void setColExpression(String colExpression)
    {
        this.colExpression = colExpression;
    }
    /**
     * @return Returns the colName.
     */
    public String getColName()
    {
        return colName;
    }
    /**
     * @param colName The colName to set.
     */
    public void setColName(String colName)
    {
        this.colName = colName;
    }
    
    /**
     * @return Returns the type.
     */
    public String getType()
    {
        return type;
    }
    /**
     * @param type The type to set.
     */
    public void setType(String type)
    {
        this.type = type;
    }
    /**
     * @return Returns the dictMap.
     */
    public Map getDictMap()
    {
        if ( dictName != null && !"".equals(dictName) )
            return dictFactory.getDictionaryByName(dictName);
        else
            return null;
    }
    /**
     * @return Returns the dictName.
     */
    public String getDictName()
    {
        return dictName;
    }
    /**
     * @param dictName The dictName to set.
     */
    public void setDictName(String dictName)
    {
        this.dictName = dictName;
    }
    /**
     * @return Returns the dispPattern.
     */
    public String getDispPattern()
    {
        return dispPattern;
    }
    /**
     * @param dispPattern The dispPattern to set.
     */
    public void setDispPattern(String dispPattern)
    {
        this.dispPattern = dispPattern;
    }
    /**
     * @return Returns the editable.
     */
    public boolean isEditable()
    {
        return editable;
    }
    /**
     * @param editable The editable to set.
     */
    public void setEditable(boolean editable)
    {
        this.editable = editable;
    }
    /**
     * @return Returns the directInputable.
     */
    public boolean isDirectInputable()
    {
        return directInputable;
    }
    /**
     * @param directInputable The directInputable to set.
     */
    public void setDirectInputable(boolean directInputable)
    {
        this.directInputable = directInputable;
    }
    /**
     * @return Returns the inputManner.
     */
    public String getInputManner()
    {
        return inputManner;
    }
    /**
     * @param inputManner The inputManner to set.
     */
    public void setInputManner(String inputManner)
    {
        this.inputManner = inputManner;
    }

    /**
     * @return Returns the primaryKey.
     */
    public boolean isPrimaryKey()
    {
        return primaryKey;
    }
    /**
     * @param primaryKey The primaryKey to set.
     */
    public void setPrimaryKey(boolean primaryKey)
    {
        this.primaryKey = primaryKey;
    }
    
    /**
     * @return Returns the nullable.
     */
    public boolean isNullable()
    {
        return nullable;
    }
    /**
     * @param nullable The nullable to set.
     */
    public void setNullable(boolean notNull)
    {
        this.nullable = notNull;
    }
    /**
     * @return Returns the showInEditForm.
     */
    public boolean isShowInEditForm()
    {
        return showInEditForm;
    }
    /**
     * @param showInEditForm The showInEditForm to set.
     */
    public void setShowInEditForm(boolean showInForm)
    {
        this.showInEditForm = showInForm;
    }
    /**
     * @return Returns the showInList.
     */
    public boolean isShowInList()
    {
        return showInList;
    }
    /**
     * @param showInList The showInList to set.
     */
    public void setShowInList(boolean showInList)
    {
        this.showInList = showInList;
    }
    
    /**
     * @return Returns the needDictReplace.
     */
    public boolean isNeedDictReplace()
    {
        return needDictReplace;
    }
    /**
     * @param needDictReplace The needDictReplace to set.
     */
    public void setNeedDictReplace(boolean needDictReplace)
    {
        this.needDictReplace = needDictReplace;
    }
    
    /**
     * @return Returns the showInAddForm.
     */
    public boolean isShowInAddForm()
    {
        return showInAddForm;
    }
    /**
     * @param showInAddForm The showInAddForm to set.
     */
    public void setShowInAddForm(boolean showInAddForm)
    {
        this.showInAddForm = showInAddForm;
    }
    /**
     * @return Returns the title.
     */
    public String getTitle()
    {
        return title;
    }
    /**
     * @param title The title to set.
     */
    public void setTitle(String title)
    {
        this.title = title;
    }
    /**
     * @return Returns the useAsDataFilter.
     */
    public boolean isUseAsDataFilter()
    {
        return useAsDataFilter;
    }
    /**
     * @param useAsDataFilter The useAsDataFilter to set.
     */
    public void setUseAsDataFilter(boolean useAsDataFilter)
    {
        this.useAsDataFilter = useAsDataFilter;
    }
    /**
     * @return Returns the validationRule.
     */
    public CrudValidateRule[] getValidationRules()
    {
        return validationRules;
    }
    /**
     * @param validationRule The validationRule to set.
     */
    public void setValidationRules(CrudValidateRule[] validationRules)
    {
        this.validationRules = validationRules;
    }
    
    /**
     * @return Returns the candidateValueGenerator4Add.
     */
    public RequestContextStringRetriever getCandidateValueGenerator4Add()
    {
        return candidateValueGenerator4Add;
    }
    /**
     * @param candidateValueGenerator4Add The candidateValueGenerator4Add to set.
     */
    public void setCandidateValueGenerator4Add(
            RequestContextStringRetriever candidateValueGenerator4Add)
    {
        this.candidateValueGenerator4Add = candidateValueGenerator4Add;
    }
    /**
     * @return Returns the candidateValueGenerator4Edit.
     */
    public RequestContextStringRetriever getCandidateValueGenerator4Edit()
    {
        return candidateValueGenerator4Edit;
    }
    /**
     * @param candidateValueGenerator4Edit The candidateValueGenerator4Edit to set.
     */
    public void setCandidateValueGenerator4Edit(
            RequestContextStringRetriever candidateValueGenerator4Edit)
    {
        this.candidateValueGenerator4Edit = candidateValueGenerator4Edit;
    }
    
    
    /**
     * @param dictFactory The dictFactory to set.
     */
    public void setDictFactory(DictFactory dictFactory)
    {
        this.dictFactory = dictFactory;
    }
    /**
     * @return Returns the ultimateValueGenerator.
     */
    public ContextRelativeStringGenerator getUltimateValueGenerator4Add()
    {
        return ultimateValueGenerator4Add;
    }
    /**
     * @param ultimateValueGenerator The ultimateValueGenerator to set.
     */
    public void setUltimateValueGenerator4Add(
            ContextRelativeStringGenerator ultimateValueGenerator4Add)
    {
        this.ultimateValueGenerator4Add = ultimateValueGenerator4Add;
    }
    
    /**
     * @return Returns the ultimateValueGenerator4Edit.
     */
    public ContextRelativeStringGenerator getUltimateValueGenerator4Edit()
    {
        return ultimateValueGenerator4Edit;
    }
    /**
     * @param ultimateValueGenerator4Edit The ultimateValueGenerator4Edit to set.
     */
    public void setUltimateValueGenerator4Edit(
            ContextRelativeStringGenerator ultimateValueGenerator4Edit)
    {
        this.ultimateValueGenerator4Edit = ultimateValueGenerator4Edit;
    }
    
    /**
     * @return Returns the valueGenerator4UpdateBatch.
     */
    public RequestContextStringRetriever getValueGenerator4UpdateBatch()
    {
        return valueGenerator4UpdateBatch;
    }
    /**
     * @param valueGenerator4UpdateBatch The valueGenerator4UpdateBatch to set.
     */
    public void setValueGenerator4UpdateBatch(
            RequestContextStringRetriever valueGenerator4UpdateBatch)
    {
        this.valueGenerator4UpdateBatch = valueGenerator4UpdateBatch;
    }
	/**
	 * @return Returns the derived.
	 */
	public boolean isDerived() {
		return derived;
	}
	/**
	 * @param derived The derived to set.
	 */
	public void setDerived(boolean derived) {
		this.derived = derived;
	}
	/**
	 * @return Returns the inputable.
	 */
	public boolean isInputable() {
		return inputable;
	}
	/**
	 * @param inputable The inputable to set.
	 */
	public void setInputable(boolean inputable) {
		this.inputable = inputable;
	}
	
    /**
     * @return Returns the inputValueSelectViewName.
     */
    public String getInputValueSelectViewName()
    {
        return inputValueSelectViewName;
    }
    /**
     * @param inputValueSelectViewName The inputValueSelectViewName to set.
     */
    public void setInputValueSelectViewName(String inputValueSelectViewName)
    {
        this.inputValueSelectViewName = inputValueSelectViewName;
    }
    
    /**
	 * @return the viewType
	 */
	public String getViewType() {
		return viewType;
	}
	/**
	 * @param viewType the viewType to set
	 */
	public void setViewType(String viewType) {
		this.viewType = viewType;
	}
	
    /**
	 * @return the cfg
	 */
	public String getConfig() {
		return config;
	}
	/**
	 * @param cfg the cfg to set
	 */
	public void setConfig(String cfg) {
		this.config = cfg;
	}
	
	/**
	 * @return the allowBlank
	 */
	public boolean isAllowBlank() {
		return allowBlank;
	}
	/**
	 * @param allowBlank the allowBlank to set
	 */
	public void setAllowBlank(boolean allowBlank) {
		this.allowBlank = allowBlank;
	}
	
	public String getRenderer() {
		return renderer;
	}
	/**
	 * Ext中 renderer一般用于Grid中对列模型的渲染上 其详细信息记录在 Ext.grid.ColumnModel 中的setRenderer( Number col, Function fn ) 方法里
	 renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
   	1.value是当前单元格的值
	2.cellmeta里保存的是cellId单元格id，css是这个单元格的css样式。
	3.record是这行的所有数据，record.data["id"]这样可获得id
	4.rowIndex 行号，不是从头往下数的意思，而是计算了分页以后的结果。
	5.columnIndex 列号
	6.store 表格里所有的数据
		}
	 * @param renderer
	 */
	public void setRenderer(String renderer) {
		this.renderer = renderer;
	}
	
    public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public boolean isReadOnly() {
		return readOnly;
	}
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}
}
