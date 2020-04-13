package com.space.service;

import com.space.exception.BadRequestException;

import java.util.Calendar;
import java.util.Date;

public class Validation {

    public Long checkId (String id){
        if (id==null||id.isEmpty())throw new BadRequestException("wrong id");
        try{
            Long longId = Long.parseLong(id);
            if (longId<=0L)throw new BadRequestException("wrong id");
            return longId;
        }catch (Exception e){throw new BadRequestException("wrong id");}
    }

    public void checkName(String name){
        if (
                name!=null&&name.length()<1||
                name!=null&&name.length()>50)
            throw new BadRequestException("wrong name");
    }

    public void checkPlanet(String planet){
        if (planet!=null&&planet.length()<1||planet!=null&&planet.length()>50)
            throw new BadRequestException("wrong planet name");
    }

    public void checkProdDate(Long after,Long before){
        if(after!=null){
            Date afterDate = new Date(after);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(afterDate);
            if(calendar.get(Calendar.YEAR)<2800)throw new BadRequestException("wrong prodDate");
        }
        if(before!=null){
            Date beforeDate = new Date(before);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(beforeDate);
            if(calendar.get(Calendar.YEAR)>3019)throw new BadRequestException("wrong prodDate");
        }
    }

    public void checkProdDate(Date prodDate){
        if(prodDate!=null){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(prodDate);
            if(calendar.get(Calendar.YEAR)<2800||calendar.get(Calendar.YEAR)>3019)throw new BadRequestException("wrong prodDate");
        }
    }

    public void checkCrewSize(Integer min,Integer max){
        if((min!=null&&min<1)||(max!=null&&max>9999))throw new BadRequestException("wrong crewSize");
    }

    public void checkCrewSize(Integer crewSize){
        if((crewSize!=null&&(crewSize<1||crewSize>9999)))throw new BadRequestException("wrong crewSize");
    }

    public void checkSpeed(Double min,Double max){
        if((min!=null&&min<0.01D)||(max!=null&&max>0.99D))throw new BadRequestException("wrong speed");
    }

    public void checkSpeed(Double speed){
        if(speed==null||speed!=null&&(speed<0.01D||speed>0.99D))throw new BadRequestException("wrong speed");
    }

}
