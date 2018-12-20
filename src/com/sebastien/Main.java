package com.sebastien;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Scanner;

public class Main
{

    static Visuals GUI;

    private void Init() throws IOException {
        Globals.Init();
        GUI = Globals.GetGUI();
        Globals.Text.GetText(); // Initialize all of our names, town names, etc...
        //Globals.SetLocalPlayer(new Player("Gamer"));
        //Globals.GetLocalPlayer().SetLocation(new Location(Globals.Text.m_szTownNames[Globals.GetCurrentTown()]));

    }

    private void CreateCharacter() throws IOException, InterruptedException, ParseException {
        Scanner scan =  Globals.GetScanner();
        GUI.DrawCharacterCreation();
        System.out.print("----> Please Enter Your Character's Name <----\n| ");
        String localName = scan.nextLine();
        Util.cls();
        GUI.DrawCharacterCreation();
        System.out.print("----> Please Enter Your Desired Race <----\n|(Orc, Human, Dark Elf, Wood Elf)\n| ");
        String localRace = scan.nextLine();
        Race race = Race.StringToRace(localRace);
        if(race == null) {
            System.out.print("| You did not enter a valid race! A random race will be selected for you instead!\n");
            race = Util.GetRandomRace();
        }
        System.out.printf("| Your name is %s of the %ss!\n", localName, race.GetName());
        Player localplayer = new Player(localName, race);
        Util.Wait();
        Util.cls();
        scan.nextLine();
        System.out.print("----> Please Enter Your Desired Class <----\n|(Mage, Tank, Assassin, Bruiser, Fighter)\n| ");
        String localClass = scan.nextLine();
        localClass = localClass.toLowerCase();
        switch (localClass)
        {
            case "mage": localplayer.GetInventory().AddItem(json.GetRandomItemType(localplayer, ITEMTYPE.MAGIC)); System.out.print("You have become a Mage!\n"); break;
            case "tank": localplayer.GetInventory().AddItem(json.GetRandomItemType(localplayer, ITEMTYPE.AXE)); System.out.print("You have become a Tank!\n"); break;
            case "assassin": localplayer.GetInventory().AddItem(json.GetRandomItemType(localplayer, ITEMTYPE.DAGGER)); System.out.print("You have become an Assassin!\n"); break;
            case "bruiser": localplayer.GetInventory().AddItem(json.GetRandomItemType(localplayer, ITEMTYPE.FLAIL)); System.out.print("You have become a Bruiser!\n"); break;
            case "fighter": localplayer.GetInventory().AddItem(json.GetRandomItemType(localplayer, ITEMTYPE.SWORD)); System.out.print("You have become a Fighter!\n"); break;
            default: System.out.print("Unknown class, defaulting to Mage...\n"); localplayer.GetInventory().AddItem(json.GetRandomItemType(localplayer, ITEMTYPE.MAGIC)); break;
        }
        Util.Wait();
        Util.cls();
        Globals.SetLocalPlayer(localplayer);
        System.out.print("\nYou set off on your journey!\n");
        Util.Wait();
        Util.cls();
        GUI.Update();
        GUI.Draw();
        Globals.GetLocalPlayer().SetLocation(new Location(Globals.Text.m_szTownNames[Globals.GetCurrentTown()]));
        scan.nextLine();
        Util.Wait();
        Util.cls();
    }

   private void GameLoop() throws IOException, InterruptedException, ParseException {
       GUI.Update();
       GUI.Draw();
       Globals.SetLastEvent("");
       Globals.GetInput().GetInput();
       if(Globals.GetLastActionType() == ACTIONTYPE.ATTACK) {
           Globals.GetObjectManager().UpdateObject();
           Globals.GetObjectManager().UpdateNPC();
       }
       System.out.print(Globals.GetLastEvent() + "\n");
       Util.Wait();
   }

    public static void main(String[] args) throws IOException, InterruptedException, ParseException {
        Main Core = new Main();
        Util.cls();
        Core.Init();
        Core.CreateCharacter();
       // Encounter test = new Encounter(Globals.GetLocalPlayer().GetLocation().GetInfo());
        while (true)
        {
            Core.GameLoop();
        }
    }
}
