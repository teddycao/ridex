/*
 * Created on 2005-9-29
 */
package com.inwiss.springcrud.web.depose;

/**
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */

//TODO 这个有没有投入使用
//如果使用这个类的话，还需要修改list.jsp。
//这个类可以看作一个具体逻辑的入口，在这个类中根据具体的pattern返回具体的渲染后的值。
public class DisplayPatternDelegate
{
    public static String getDisplayPatternAppliedString(String original, String pattern)
    {
        return original+"[pattern]";
    }
}
