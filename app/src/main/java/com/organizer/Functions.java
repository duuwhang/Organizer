package com.organizer;

import android.content.Context;
import android.widget.Toast;
import java.util.HashMap;
import java.util.List;

public class Functions {
    static HashMap<Integer, List<Task>> dictionary = new HashMap<>();
    static HashMap<Integer, Day> days = new HashMap<>();
    static int dayAmount = 3;
    static int todayD = 15;
    static int todayM = 1;
    static int todayY = 2019;
    static int today = buildId(todayD, todayM, todayY);
    static int screenHeight;
    static int screenWidth;
    static int textViewSize;
    static int actionBarSize;
    public static void addDays(int numDays, int offset) {
        for (int i = offset; i < numDays + offset; i++) {
            days.put(i, new Day(todayD + i, todayM, todayY)); // TODO offset rip
        }
    }
    public static void debug(Context context){
        Toast.makeText(context,"debug",Toast.LENGTH_LONG).show();
    }
    public static void debug(Context context, String msg){
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }
    public static int dpToPx(Context context, int dp){
        return (int) ((float) dp * context.getResources().getDisplayMetrics().density);
    }
    public static int pxToDp(Context context, int px){
        return (int) ((float) px / context.getResources().getDisplayMetrics().density);
    }
    public static int boolToInt(boolean b) {
        if(b) return 1;
        return 0;
    }
    public static boolean intToBool(int x) {
        return x != 0;
    }
    public static int buildId(int d, int m, int y) {
        return (y * 10000) + (m * 100) + d;
    }
    public static int buildD(int id) {
        return id - buildM(id) - buildY(id);

    }
    public static int buildM(int id) {
        return id / 100 - buildY(id);
    }
    public static int buildY(int id) {
        return id / 10000;
    }
    public static boolean idExists(int id){
        Day day = new Day(id);
        return day.dayExists();
    }
    public static void idDebug(int id){
        Day day = new Day(id);
        day.dayDebug();
    }
    static int calculateHeight(double start, double end){
        double dayStart = 6;
        double dayEnd = 24;
        double contentHeight = screenHeight - textViewSize;// - actionBarSize;
        return (int) (contentHeight * ((end - start) / (dayEnd - dayStart)));
    }
    static int calculateY(double start){
        double dayStart = 6;
        double dayEnd = 24;
        double contentHeight = screenHeight - textViewSize;// - actionBarSize;
        return (int) (textViewSize + contentHeight * ((start - dayStart) / (dayEnd - dayStart)));
    }
    public enum Weekdays{
        SU, MO, TU, WE, TH, FR, SA
    }
}
