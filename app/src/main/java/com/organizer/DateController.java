package com.organizer;

import java.util.HashMap;
import java.util.List;

public class DateController
{
    public HashMap<Integer, List<Task>> dictionary = new HashMap<>();
    public HashMap<Integer, Day> days = new HashMap<>();
    int todayD = 7;
    int todayM = 4;
    int todayY = 2021;
    public int today = buildId(todayD, todayM, todayY);
    public int textViewSize;
    
    public enum Weekdays
    {
        SU,
        MO,
        TU,
        WE,
        TH,
        FR,
        SA
    }
    
    public void addDays(int numDays, int offset)
    {
        for (int i = offset; i < numDays + offset; i++)
        {
            days.put(i, new Day(buildId(todayD + i, todayM, todayY))); // TODO offset rip
        }
    }
    
    public int buildId(int d, int m, int y)
    {
        return (y * 10000) + (m * 100) + d;
    }
    
    public int buildD(int id)
    {
        return id - (buildM(id) * 100) - (buildY(id) * 10000);
    }
    
    public int buildM(int id)
    {
        return id / 100 - (buildY(id) * 100);
    }
    
    public int buildY(int id)
    {
        return id / 10000;
    }
    
    public boolean idExists(int id)
    {
        Day day = new Day(id);
        return day.dayExists();
    }
    
    public void idDebug(int id)
    {
        Day day = new Day(id);
        day.dayDebug();
    }
    
    int calculateHeight(double start, double end)
    {
        double dayStart = 6;
        double dayEnd = 24;
        double contentHeight = MainActivity.getDisplayMetricsController().getScreenHeight() - textViewSize;
        return (int) (contentHeight * ((end - start) / (dayEnd - dayStart)));
    }
    
    int calculateY(double start)
    {
        double dayStart = 6;
        double dayEnd = 24;
        double contentHeight = MainActivity.getDisplayMetricsController().getScreenHeight() - textViewSize;
        return (int) (textViewSize + contentHeight * ((start - dayStart) / (dayEnd - dayStart)));
    }
}
