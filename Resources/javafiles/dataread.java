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
import java.util.Date;



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
                System.out.println(r);
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
        System.out.println(myavg.avg);
        System.out.println(myavg.num);
        System.out.println(myavg.den);
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

class average //attempt to use data
{
    int num = 0;//will be the total
    int den = 0;// will be number of entries, as in average = total/)number of items)
    int avg = 0;
    
    public average(List<record> input)
    {
        for (record r : input)
        {
            num += input.get(0).price; //This is only going to use the first one, but this was a test anyway and I didn't realize that then
            den++;
        }
        
        this.avg = num / den;
        
        
    }
    
    public int toint()
    {
        return avg;
    }
}
