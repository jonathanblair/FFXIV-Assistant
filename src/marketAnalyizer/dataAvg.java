/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.time.Instant;
import java.util.Calendar;

/**
 *
 * @author Jon
 * 
 * allows average by date, hq/nq/all, maybe by volume later
 * date sorting: past week, 2 weeks, past 30 days, past 90 days, past year (90 days/year low priority
 * also sort by day of week or time of day?
 */
public class dataAvg 
{
    int num = 0;
    int den = 0;
    int avg = 0;
    Calendar mycalendar = Calendar.getInstance();
    Date time = mycalendar.getTime();
    
    public dataAvg(List<record> datain, String quality) //sort by only quality
    {
        switch (quality) //sorts based on quality
        {
        case "hq": //sort by high quality only
            for (record r : datain)
            {
                if (r.hq == true)
                {
                    num += r.price;
                    den++;
                }
            }
            break;
            
        case "nq": //sort by normal quality only
            for (record r : datain)
            {
                if (r.hq == false)
                {
                    num += r.price;
                    den++;
                }
            }
            break;
                
        case "all": //ignore quality
            for (record r : datain)
            {
                num += r.price;
                den++;
            }
            break;       
        }
        
        if (den != 0)
        {
            this.avg = num / den;
        } else
        {
            this.avg = 0;
        }
        System.out.println(time);
        
    }
    
    public dataAvg(List<record> datain, int timeHistory) //timeHistory is in days
    {
        Calendar recordCal = Calendar.getInstance();
        mycalendar.add(Calendar.DAY_OF_YEAR, -1 * timeHistory);
        for (record r : datain)
        {
            recordCal.setTime(r.time);
            if (recordCal.after(mycalendar))
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
    
    public dataAvg(List<record> datain, int timeHistory, Date timeSort) //timeHistory is the extent (7 days, 14 days, 30 days, etc. timeSort is sorting by day of week, hour of day etc.
    {
        //DO WITHOUT timeSort FIRST
        //Made into different class
    }
    
    public int toint()
    {
        return avg;
    }
    
}
