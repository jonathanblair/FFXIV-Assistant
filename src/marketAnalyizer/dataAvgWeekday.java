/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.data;

import java.util.List;
import java.util.Date;
import java.util.Calendar;

/**
 *
 * @author Jon
 */
public class dataAvgWeekday
{
    int num = 0;
    int den = 0;
    int avg = 0;
    int weekNumDay = 1;
    Calendar cal = Calendar.getInstance();
    Date time = cal.getTime();
    
    public dataAvgWeekday(List<record> datain, String weekday)
    {
        Calendar recordCal = Calendar.getInstance();
        weekStrToInt week = new weekStrToInt(weekday);
        weekNumDay = week.number;
//        System.out.println(recordCal.get(Calendar.DAY_OF_WEEK));        
        for (record r : datain)
        {
            recordCal.setTime(r.time);
            if (recordCal.get(Calendar.DAY_OF_WEEK) == weekNumDay)
            {
                num += r.price;
                den++;
                System.out.println(r + " " + r.time);
            }
        }
        
        if (den != 0)
        {
            this.avg = num / den;
        } else
        {
            this.avg = 0;
        }
    }
    
    public dataAvgWeekday(List<record> datain, String weekday, int timeHistory)
    {
        Calendar recordCal = Calendar.getInstance();
        weekStrToInt week = new weekStrToInt(weekday);
        weekNumDay = week.number;
        cal.add(Calendar.DAY_OF_YEAR, -1 * timeHistory);
        
        for (record r : datain)
        {
            recordCal.setTime(r.time);
            if ((recordCal.get(Calendar.DAY_OF_WEEK) == weekNumDay) && (recordCal.after(cal)))
            {
                num += r.price;
                den++;
            }
        }
        
        if (den != 0)
        {
            this.avg = num / den;
        } else
        {
            this.avg = 0;
        }
    }
    
    public dataAvgWeekday(List<record> datain, String weekday, String quality)
    {
        Calendar recordCal = Calendar.getInstance();
        weekStrToInt week = new weekStrToInt(weekday);
        weekNumDay = week.number;
        
        for (record r : datain)
        {
            recordCal.setTime(r.time);
            if (recordCal.get(Calendar.DAY_OF_WEEK) == weekNumDay)
            {
                switch (quality)
                {
                    case "hq": //high quality and day of week only
                    {
                        if (r.hq == true)
                        {
                            num += r.price;
                            den++;
                        }
                        break;
                    }
                    case "nq": //normal quality and day of week only
                    {
                        if (r.hq == false)
                        {
                            num += r.price;
                            den++;
                        }
                        break;
                    }
                    case "all": //any quality and day of week only
                    {
                        num += r.price;
                        den++;
                        break;
                    }
                }
            }
        }
        
        if (den != 0)
        {
            this.avg = num / den;
        } else
        {
            this.avg = 0;
        }
    }
    
    public dataAvgWeekday(List<record> datain, String weekday, String quality, int timeHistory)
    {
        Calendar recordCal = Calendar.getInstance();
        weekStrToInt week = new weekStrToInt(weekday);
        weekNumDay = week.number;
        cal.add(Calendar.DAY_OF_YEAR, -1 * timeHistory);
        
        for (record r : datain)
        {
            recordCal.setTime(r.time);
            if ((recordCal.get(Calendar.DAY_OF_WEEK) == weekNumDay) && (recordCal.after(cal)))
            {
                switch (quality)
                {
                    case "hq": //high quality, day of week, and within past time period
                    {
                        if (r.hq == true)
                        {
                            num += r.price;
                            den++;
                        }
                        break;
                    }
                    case "nq": //normal quality, day of week, and within past time period
                    {
                        if (r.hq == false)
                        {
                            num += r.price;
                            den++;
                        }
                        break;
                    }
                    case "all":
                    {
                        num += r.price;
                        den++;
                        break;
                    }
                }
            }
        }
        
        if (den != 0)
        {
            this.avg = num / den;
        } else
        {
            this.avg = 0;
        }
    }
}

class weekStrToInt
{
    int number = 1;
    public weekStrToInt(String weekday)
    {
        switch (weekday)
        {
            case "sunday":
            {
                number = 1;
                break;
            }
            case "monday":
            {
                number = 2;
                break;
            }
            case "tuesday":
            {
                number = 3;
                break;
                
            }
            case "wednesday":
            {
                number = 4;
                break;
            }
            case "thursday":
            {
                number = 5;
                break;
            }
            case "friday":
            {
                number = 6;
                break;
            }
            case "saturday":
            {
                number = 7;
                break;
            }
        }
    }
    
    public int toint()
    {
        return number;
    }
}
