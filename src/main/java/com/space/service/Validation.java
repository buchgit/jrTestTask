package com.space.service;

import com.space.exception.MyRuntimeException;

import java.util.Calendar;
import java.util.Date;

public class Validation {

    public Long checkLongParser (String o, String message){
        if (o==null)throw new MyRuntimeException("wrong "+ message);
        try{
            return Long.parseLong(o);
        }catch (Exception e){throw new MyRuntimeException("wrong "+ message);}
    }

    public Long checkId(String id){
        return checkLongParser(id,id);
    }

    public void checkName(String name){
        if (name!=null&&name.length()<1||name!=null&&name.length()>50)throw new MyRuntimeException("wrong name length");
    }
    public void checkPlanet(String planet){
        if (planet!=null&&planet.length()<1||planet!=null&&planet.length()>50)throw new MyRuntimeException("wrong planet name length");
    }
    public void checkProdDate(Long after,Long before){
        if(after!=null){
            Date afterDate = new Date(after);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(afterDate);
            if(calendar.get(Calendar.YEAR)<2800)throw new MyRuntimeException("wrong prodDate");
        }
        if(before!=null){
            Date beforeDate = new Date(before);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(beforeDate);
            if(calendar.get(Calendar.YEAR)>3019)throw new MyRuntimeException("wrong prodDate");
        }
    }

    public void checkCrewSize(Integer min,Integer max){
        if((min!=null&&min<1)||(max!=null&&max>9999))throw new MyRuntimeException("wrong crewSize");
    }

    public void checkSpeed(Double min,Double max){
        if((min!=null&&min<0.01D)||(max!=null&&max>0.99D))throw new MyRuntimeException("wrong speed");
    }
}
