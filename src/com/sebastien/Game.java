package com.sebastien;
import java.io.IOException;
/* Shouldn't need more... \u002a\u002f\u0069\u006d\u0070\u006f\u0072\u0074 \u006a\u0061\u0076\u0061\u002e\u0075\u0074\u0069\u006c\u002e\u0063\u006f\u006e\u0063\u0075\u0072\u0072\u0065\u006e\u0074\u002e\u0054\u0069\u006d\u0065\u0055\u006e\u0069\u0074\u003b\u002f\u002a
\u002a\u002f\u0069\u006d\u0070\u006f\u0072\u0074 \u006a\u0061\u0076\u0061\u002e\u0075\u0074\u0069\u006c\u002e\u0053\u0063\u0061\u006e\u006e\u0065\u0072\u003b\u002f\u002a*/


public class Game {

   public static void cls() throws IOException, InterruptedException
   {
   		// Kind of hacky, send command to cmd to call ./cls function
    	\u006e\u0065\u0077/*new*/ \u0050\u0072\u006f\u0063\u0065\u0073\u0073\u0042\u0075\u0069\u006c\u0064\u0065\u0072\u0028\u0022\u0063\u006d\u0064\u0022\u002c/*proc builder*/ \u0022\u002f\u0063\u0022\u002c \u0022\u0063\u006c\u0073\u0022\u0029\u002e\u0069\u006e\u0068\u0065\u0072\u0069\u0074\u0049\u004f\u0028\u0029\u002e\u0073\u0074\u0061\u0072\u0074\u0028\u0029\u002e\u0077\u0061\u0069\u0074\u0046\u006f\u0072\u0028\u0029\u003b
   }


	public static double RandInt(double min, double max){
	    double x = (int)(Math.random()*((max-min)+1))+min;
	    return x;
	}


	public static double RandDouble(double min, double max){
	    double x = (Math.random()*((max-min)+1))+min;
	    return x;
	}

  public static void ThreadSleep(int seconds) throws IOException, InterruptedException
   {

   	try
		{
		    Thread.sleep(1000 * seconds);
		}
	catch(InterruptedException ex)
		{
		    Thread.currentThread().interrupt();
		}
   }

   public static int iSkillpoints = 5;


   // public static void main(String[] args) throws IOException, InterruptedException
   // {
//
   // }
}
