package com.common.plugins.spring.mvc;

import java.beans.PropertyEditorSupport;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeEditor extends PropertyEditorSupport {
	private String format = null;
    
    public String getAsText() {
        Date date = (Date) getValue();
        if (date == null) {
            return "";
        }
        if (format == null) {
            format = "yyyy-MM-dd";
        }
        DateFormat formater = new SimpleDateFormat(format);
        return formater.format(date);
    }
        
    public void setAsText(String text) {
    	
        if (text != null && text.length() > 0) {
            try {
                if (format == null) {
                    format = "yyyy-MM-dd";
                }
                DateFormat formater = new SimpleDateFormat(format);
                setValue(new Date(formater.parse(text).getTime()));
            } catch (ParseException ex) {
            	ex.printStackTrace();
                throw new IllegalArgumentException(ex);
            }
        }
    }
        
    public String getFormat() {
        return format;
    }
        
    public void setFormat(String format) {
        this.format = format;
    }
        
}
