package org.inwiss.platform.persistence.hibernate;

public class PropertyEntity extends EntityObject {
    /**
     * 属性名.
     */
    private String name;

    /**
     * 属性值.
     */
    private String value;

    /**
     * 取得类的属性名属性.
     * @hibernate.property
     *     column="name"
     *     type="string"
     *     length="20"
     *     not-null="True"
     * @return  属性名.
     */
    public String getName() {
        return name;
    }

    /**
     * 设置类的属性名属性.
     * @param  name  属性名.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 取得类的属性值属性.
     * @hibernate.property
     *     column="value"
     *     type="text"
     * @return  属性值.
     */
    public String getValue() {
        return value;
    }

    /**
     * 设置类的属性值属性.
     * @param  value  属性值.
     */
    public void setValue(String value) {
        this.value = value;
    }

    public String toString() {
        StringBuffer buff = new StringBuffer(this.getClass().getName());
        buff.append(":name=").append(name);
        buff.append(";value=").append(value);
        return buff.toString();
    }
    
}
