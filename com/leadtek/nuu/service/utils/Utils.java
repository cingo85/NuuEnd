package com.leadtek.nuu.service.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Utils {
	
	public static String newPassword(){
		int[] word = new int[8];
		char[] punctuation = new char[]{'!','@','#'};
		int mod;//{'!','@','#','$','%','^','&','*','+','-'};
		for(int i = 0; i < word.length; i++){
			mod = (int)(( Math.random() * 4));
			if(mod ==0){ //數字
				word[i]=(int)((Math.random()*10) + 48);
			}else if(mod ==1){ //大寫英文
				word[i] = (char)((Math.random()*26) + 65);
			}else if(mod ==2){ //小寫英文
				word[i] = (char)((Math.random()*26) + 97);
			}else{
				word[i] = punctuation[(int)(Math.random()*3)];
			} 
		}
		StringBuffer newPassword = new StringBuffer();
		for(int j = 0; j < word.length ; j++){
			newPassword.append((char)word[j]);
		}
		return newPassword.toString();
	}
	
	public static boolean isExpire(Date date){
		if( date == null )
			return true;
		Calendar cutoff = Calendar.getInstance();
		cutoff.setTime(date);
		Calendar cal = Calendar.getInstance();//今天日期
		cal.add(Calendar.DAY_OF_WEEK, - 1);	//減一天
		return ( cal.compareTo(cutoff) >= 0 ); 
	}
	
	public static boolean isExpire(Date start, Date end){
		boolean sb = true, eb = true;
		if( start == null && end == null ) return true;
		if( start != null ){
			Calendar scal = Calendar.getInstance();
			scal.setTime(start);
			sb = ( Calendar.getInstance().compareTo(scal) > 0 );
		}
		if( end != null ){
			Calendar ecal = Calendar.getInstance();
			ecal.setTime(end);
			ecal.add(Calendar.DAY_OF_WEEK, +1);
			eb = ( Calendar.getInstance().compareTo(ecal) < 0 );
		}
		return !( sb && eb ); 
	}
	
	public static Integer yyy() {
        return (Calendar.getInstance().get(Calendar.YEAR)) - 1911;      
    }
	
	public static String[] stringToArray(String str ) {
        if( str != null && str.length() > 0 ) {
        	
        	String temp[] = str.split(",");
        	Set<String> intSet = new HashSet<String>();
        	for(String element : temp) {
        		intSet.add(element);
        	}
        	
        	String result[] = new String[intSet.size()];
        	
        	Object tempresult[] =intSet.toArray();
        	
        	for(int i = 0 ; i<tempresult.length;i++) {
        		result[i] = (String)tempresult[i];
        	}
        	
        	return result;
        	
        }
        return new String[0]; 
    }
	
	public static String arrayToString(String[] array ) {
        StringBuilder arTostr = new StringBuilder();
        if (array != null && array.length > 0 ) {
            arTostr.append(array[0]);
            for (int i = 1; i < array.length; i++) {
                arTostr.append("," + array[i]);
            }
        }
        return arTostr.toString();
    }
	
}
