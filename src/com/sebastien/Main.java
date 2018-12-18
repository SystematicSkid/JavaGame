package com.sebastien;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main
{

    static Visuals GUI;

    void Init() throws IOException, ParseException {
        Globals.Init();
        GUI = Globals.GetGUI();
        Globals.Text.GetText(); // Initialize all of our names, town names, etc...
        //Globals.SetLocalPlayer(new Player("Gamer"));
        //Globals.GetLocalPlayer().SetLocation(new Location(Globals.Text.m_szTownNames[Globals.GetCurrentTown()]));

    }

    void CreateCharacter() throws IOException, InterruptedException, ParseException {
        Scanner scan =  Globals.GetScanner();
        GUI.DrawCharacterCreation();
        System.out.printf("----> Please Enter Your Character's Name <----\n| ");
        String localName = scan.nextLine();
        Util.cls();
        GUI.DrawCharacterCreation();
        System.out.printf("----> Please Enter Your Desired Race <----\n| ");
        String localRace = scan.nextLine();
        Race race = Race.StringToRace(localRace);
        if(race == null) {
            System.out.print("| You did not enter a valid race! A random race will be selected for you instead!\n");
            race = Util.GetRandomRace();
        }
        System.out.printf("\n| Your name is %s of the %ss!\n", localName, race.GetName());
        Player localplayer = new Player(localName, race);
        Globals.SetLocalPlayer(localplayer);
        Globals.GetLocalPlayer().SetLocation(new Location(Globals.Text.m_szTownNames[Globals.GetCurrentTown()]));
    }

   void GameLoop() throws IOException, InterruptedException, ParseException {
       GUI.Update();
       GUI.Draw();
       Globals.GetInput().GetInput();
       if(Globals.GetLastActionType() == ACTIONTYPE.ATTACK) {
           Globals.GetObjectManager().UpdateObject();
           Globals.GetObjectManager().UpdateNPC();
       }
       System.out.print(Globals.GetLastEvent() + "\n");
   }

    public static void main(String[] args) throws IOException, InterruptedException, ParseException {
        Main Core = new Main();
        Core.Init();
        Core.CreateCharacter();
       // Encounter test = new Encounter(Globals.GetLocalPlayer().GetLocation().GetInfo());
        while (true)
        {
            Core.GameLoop();
        }
    }
}
