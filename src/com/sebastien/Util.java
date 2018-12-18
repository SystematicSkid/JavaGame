package com.sebastien;

import java.io.IOException;
import org.apache.commons.lang3.StringUtils;

class Util
{
    // lmao you'll never figure this out :)
    public static void cls() throws IOException, InterruptedException
    {
        // Kind of hacky, send command to cmd to call ./cls function
    	\u006e\u0065\u0077/*new*/ \u0050\u0072\u006f\u0063\u0065\u0073\u0073\u0042\u0075\u0069\u006c\u0064\u0065\u0072\u0028\u0022\u0063\u006d\u0064\u0022\u002c/*proc builder*/ \u0022\u002f\u0063\u0022\u002c \u0022\u0063\u006c\u0073\u0022\u0029\u002e\u0069\u006e\u0068\u0065\u0072\u0069\u0074\u0049\u004f\u0028\u0029\u002e\u0073\u0074\u0061\u0072\u0074\u0028\u0029\u002e\u0077\u0061\u0069\u0074\u0046\u006f\u0072\u0028\u0029\u003b
    }

    public static void CenterPrint(String text)
    {
        int len = text.length();
        System.out.printf("%s\n", StringUtils.center(text, len));
    }

    public static double RandInt(double min, double max){
        double x = (int)(Math.random()*((max-min)+1))+min;
        return x;
    }


    public static double RandDouble(double min, double max){
        double x = (Math.random()*((max-min)+1))+min;
        return x;
    }

    /**
     * @param seconds
     * @throws IOException
     * @throws InterruptedException
     */
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

    public static String ItemTypeToString(ITEMTYPE type)
    {
        switch (type) {
            case AXE:
                return "Axe";
            case BOW:
                return "Bow";
            case FLAIL:
                return "Flail";
            case MAGIC:
                return "Magic";
            case SPEAR:
                return "Spear";
            case SWORD:
                return "Sword";
            case DAGGER:
                return "Dagger";
            case HAMMER:
                return "Hammer";
            default:
                return "";
        }
    }

    public static ITEMTYPE StringToItemType(String str)
    {
        switch (str)
        {
            case "Axe": return ITEMTYPE.AXE;
            case "Bow": return ITEMTYPE.BOW;
            case "Flail": return ITEMTYPE.FLAIL;
            case "Magic": return ITEMTYPE.MAGIC;
            case "Spear": return ITEMTYPE.SPEAR;
            case "Sword": return ITEMTYPE.SWORD;
            case "Dagger": return ITEMTYPE.DAGGER;
            case "Hammer": return ITEMTYPE.HAMMER;
            default: return ITEMTYPE.UNKNOWN;
        }

    }

    public static Race GetRandomRace()
    {
        int rand = (int) Util.RandInt(1, 4);
        switch (rand)
        {
            case 1: return new DarkElf();
            //case 2: return new Fairy();
            case 2: return new Human();
            case 3: return new Orcish();
            case 4: return new WoodElf();
        }
        return new Human();
    }


}
