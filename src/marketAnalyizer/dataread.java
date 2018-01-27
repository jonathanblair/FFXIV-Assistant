/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Scanner;
import java.io.File;
import java.util.Calendar;
import java.util.Date;

//TO DO: STUFF WITH TIME ZONES FOR DATA



/**
 *
 * @author Jon
 */
public class dataread {
    
    
    public static void main(String args[])
    {
        //String datafile = "C:\\Users\\Jon\\Desktop\\javafiletest\\5106.csv";
        String datafile = "resources/marketdata/5106.csv";
        List<record> dataArray = new ArrayList<>();

        try
        {
            Scanner scan = new Scanner(new File(datafile));
            record next;
            while (scan.hasNextLine())
            {
                next = new record(scan.nextLine().split(","));
                dataArray.add(next);
            }
            for (record r : dataArray)
            {
                System.out.println(r + " " + r.time);
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        
        //average myavg = new average(dataArray);
        //System.out.println(myavg.avg);
        //System.out.println(myavg.num);
        //System.out.println(myavg.den);
        //System.out.println(dataArray.get(1).hq);
        
        dataAvg myavg = new dataAvg(dataArray, "all");
        dataAvg testavg = new dataAvg(dataArray, 7);
        System.out.println(testavg.mycalendar.getTime());
        dataAvgMonth testmonth = new dataAvgMonth(dataArray, "january");
        System.out.println(testmonth.avg);
        System.out.println(testmonth.cal.MONTH);
        System.out.println(testmonth.numMonth);
        System.out.println(testmonth.time);
    }
}

class record
{
    boolean hq;
    int quantity;
    int price;
    Date time;
    
    public record(boolean hq, int qty, int price, Date time)
    {
        this.hq = hq;
        this.quantity = qty;
        this.price = price;
        this.time = time;
    }
    
    public record(String[] vars)
    {
        this.hq = Integer.parseInt(vars[0]) == 1;
        this.price = Integer.parseInt(vars[1]);
        this.quantity = Integer.parseInt(vars[2]);
        this.time = new Date(Long.parseLong(vars[3]));
    }
    
    public String toString()
    {
        return "record: sold " + quantity + (hq ? " hq":"") + " at " + price + " ea";
    }   
}

class dataAvg 
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


class dataAvgWeekday
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

class dataAvgMonth
{
    int num = 0;
    int den = 0;
    int avg = 0;
    int numMonth = 1;
    Calendar cal = Calendar.getInstance();
    Date time = cal.getTime();
    
    public dataAvgMonth(List<record> datain, String month) //sort data by month only
    {
        Calendar recordCal = Calendar.getInstance();
        monthToInt mti = new monthToInt(month);
        numMonth = mti.number;
        
        for (record r: datain)
        {
            recordCal.setTime(r.time);
            if (recordCal.get(Calendar.MONTH) == numMonth)
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
    
    public dataAvgMonth(List<record> datain, String month, String quality)
    {
        Calendar recordCal = Calendar.getInstance();
        monthToInt mti = new monthToInt(month);
        numMonth = mti.number;
        
        for (record r : datain)
        {
            recordCal.setTime(r.time);
            if (recordCal.get(Calendar.MONTH) == numMonth)
            {
                switch (quality)
                {
                    case "hq":
                    {
                        if (r.hq == true)
                        {
                            num += r.price;
                            den++;
                        }
                        break;
                    }
                    case "nq":
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
                    }
                    break;
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

class monthToInt
{
    int number = 1;
    public monthToInt(String month)
    {
        switch (month)
        {
            case "january":
            {
                number = 1;
                break;
            }
            case "february":
            {
                number = 2;
                break;
            }
            case "march":
            {
                number = 3;
                break;
            }
            case "april":
            {
                number = 4;
                break;
            }
            case "may":
            {
                number = 5;
                break;
            }
            case "june":
            {
                number = 6;
                break;
            }
            case "july":
            {
                number = 7;
                break;
            }
            case "august":
            {
                number = 8;
                break;
            }
            case "september":
            {
                number = 9;
                break;
            }
            case "october":
            {
                number = 10;
                break;
            }
            case "november":
            {
                number = 11;
                break;
            }
            case "december":
            {
                number = 12;
                break;
            }
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
