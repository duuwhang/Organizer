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
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
    public static int pxToDp(Context context, int dp){ // TODO
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
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
        double contentHeight = screenHeight - actionBarSize - textViewSize;
        return (int) (contentHeight * ((end - start) / (dayEnd - dayStart)));
    }
    static int calculateY(double start){
        double dayStart = 6;
        double dayEnd = 24;
        double contentHeight = screenHeight - actionBarSize - textViewSize;
        return (int) (textViewSize + contentHeight * ((start - dayStart) / (dayEnd - dayStart)));
    }
    public enum Weekdays{
        SU, MO, TU, WE, TH, FR, SA
    }
    public static int max(int... x){
        int max = x[0];
        for(int i = 0; i < x.length - 1; i++){
            if(x[i] > max){
                max = x[i];
            }
        }
        return max;
    }
    public static int min(int... x){
        int min = x[0];
        for(int i = 0; i < x.length - 1; i++){
            if(x[i] < min){
                min = x[i];
            }
        }
        return min;
    }
    public static int minmax(int... x){
        int minmax = x[0];
        for(int i = 0; i < x.length - 1; i++){
            if(x[i] < x[0] || x[i] > x[x.length - 1]){
                minmax = x[i];
            }
        }
        return minmax;
    }
}
