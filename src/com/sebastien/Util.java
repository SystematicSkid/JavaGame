package com.sebastien;

import java.io.IOException;

class Util
{
    // lmao you'll never figure this out :)
    public static void cls() {
        // Kind of hacky, send command to cmd to call ./cls function
    	\u006e\u0065\u0077/*new*/ \u0050\u0072\u006f\u0063\u0065\u0073\u0073\u0042\u0075\u0069\u006c\u0064\u0065\u0072\u0028\u0022\u0063\u006d\u0064\u0022\u002c/*proc builder*/ \u0022\u002f\u0063\u0022\u002c \u0022\u0063\u006c\u0073\u0022\u0029\u002e\u0069\u006e\u0068\u0065\u0072\u0069\u0074\u0049\u004f\u0028\u0029\u002e\u0073\u0074\u0061\u0072\u0074\u0028\u0029\u002e\u0077\u0061\u0069\u0074\u0046\u006f\u0072\u0028\u0029\u003b
    }

    public static double RandInt(double min, double max){
        return (int)(Math.random()*((max-min)+1))+min;
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

    static void Wait() throws IOException {
        System.out.print("\n\nPlease press enter to continue...\n");
        System.in.read();
    }


}
