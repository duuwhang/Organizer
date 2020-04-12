package com.organizer;

import static com.organizer.Functions.*;
import static java.lang.Math.*;

public class Day {
    int d, m, y, id;
    String weekday;
    int monthdays[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    public Day(int id){
        this.d = buildD(id);
        this.m = buildM(id);
        this.y = buildY(id);
        this.id = id;
        initDay();
    }
    public Day(int d, int m, int y){
        this.d = d;
        this.m = m;
        this.y = y;
        this.id = buildId(d, m ,y);
        initDay();
    }
    private void initDay(){
        dayDebug();
        int[] t = {0, 3, 2, 5, 0, 3, 5, 1, 4, 6, 2, 4};
        int yy = 2000 + y - boolToInt(m < 3);
        weekday = Weekdays.values()[(yy + yy/4 - yy/100 + yy/400 + t[m-1] + d) % 7].toString();
    }
    public void addDays(int days){ //+-
        this.d += days;
        dayDebug();
    }
    public void addMonths(int months){ //+-
        this.m += months;
        dayDebug();
    }
    public void addYears(int years){ //+-
        this.y += years;
        dayDebug();
    }
    public boolean dayExists(){
        if((this.y % 4) == 0 && (this.y % 100 != 0 || this.y % 400 == 0)){
            monthdays[1] = 29;
        }
        return !(this.d > monthdays[this.m - 1] || this.d < 1 || this.m > 12 || this.m < 1);
    }
    public void dayDebug(){
        while(!dayExists()){
            if((this.y % 4) == 0 && (this.y % 100 != 0 || this.y % 400 == 0)){
                monthdays[1] = 29;
            }
            int dd = this.d;
            this.d -= max(1, this.d / (monthdays[this.m - 1] + 1)) * monthdays[this.m - 1];
            this.m += max(1, dd / (monthdays[this.m - 1] + 1));
            this.y += (this.m % 12) * (this.m / 12);
            this.m -= ((this.m % 12) * (this.m / 12)) * 12;
        }
        this.id = buildId(this.d, this.m, this.y);
    }
}
