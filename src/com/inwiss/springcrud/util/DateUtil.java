/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.inwiss.springcrud.util;


import java.util.Calendar;
import java.util.Date;

/**
 * @version $Id: OgnlTool.java,v 1.1 2005/04/15 09:16:51 cvsroot Exp $
 * @author Matt Ho <a href="mailto:matt@enginegreen.com">&lt;matt@enginegreen.com&gt;</a>
 */
public class DateUtil {
    //~ Static fields/initializers /////////////////////////////////////////////

    private static DateUtil instance = new DateUtil();

    //~ Constructors ///////////////////////////////////////////////////////////

    private DateUtil() {
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    public static DateUtil getInstance() {
        return instance;
    }
    /**
     * 
     * @param date
     * @return
     */
    public String getWeekDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int WEEK = cal.get(Calendar.DAY_OF_WEEK);
        
        switch (WEEK) {
         case 1: return "星期日";
         case 2: return "星期一";
         case 3: return "星期二";
         case 4: return "星期三";
         case 5: return "星期四";
         case 6: return "星期五";
         case 7: return "星期六";
        }
        return "";
    }
	
    public Date getNextDay(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH,+1);
        return cal.getTime();
    }
    
    
    public Date getPreDay(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH,-1);
        return cal.getTime();
    }
    
    
    public Date getNextWeek(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.WEEK_OF_MONTH,+1);
        return cal.getTime();
    }
    
    public Date getPreWeek(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.WEEK_OF_MONTH,-1);
        return cal.getTime();
    }
    /**
     * 比较时间,使用的task模块
     * @param time1 int 时间,比如10
     * @param time2 time2 Date型时间
     * @return
     */
    public static boolean compareTime(int time1,Date time2){
        Calendar cal = Calendar.getInstance();
        cal.setTime(time2);
        int ct2 = cal.get(Calendar.HOUR_OF_DAY);
        
        return (ct2 >= time1 && ct2< time1+1);
        
    }

}
