package com.sebastien;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Scanner;

enum GAMESTATE
{
	START,
	PLAYING,
	END
}


class Globals
{
	static private Player localplayer;
	static private ObjectManager objectmanager;
	static private Scanner scan = new Scanner(System.in);
	static private GAMESTATE gameState;
	static private int currentTown;
	static private Input input;
	static private Visuals GUI;
	static private ACTIONTYPE m_LastActionType;
	static private boolean m_bInEncounter = false;

	static private String m_szLastEvent;
	static public void SetLastEvent(String str) { m_szLastEvent = str; }
	static public String GetLastEvent() { return m_szLastEvent; }
	static public int GetCurrentTown(){return currentTown;}
	static public void SetCurrentTown(int v){currentTown = v;}
	static public Input GetInput() { return input; }
	static public Visuals GetGUI() { return GUI; }
	static public ACTIONTYPE GetLastActionType() { return m_LastActionType; }
	static public void SetLastActionType(ACTIONTYPE type) { m_LastActionType = type; }
	static public boolean IsInEncounter() { return m_bInEncounter; }
	static public void SetIsInEncounter(boolean b) { m_bInEncounter = b; }

	static public Player GetLocalPlayer()
	{
		return localplayer;
	}
	static public void SetLocalPlayer(Player player) {localplayer = player;}
	static public Scanner GetScanner() { return scan; }
	static public GAMESTATE GetGameState(){return gameState; }
	static public void Init()
	{
		GUI = new Visuals();
		input = new Input();
		currentTown = 0;
		objectmanager = new ObjectManager();
		m_szLastEvent = "";

	}

	public static class Text
	{
		static String m_szTownNames[];
		/*
		Orc: 0
		Human: 1
		Elf: 2
		Other guys just dont have names lmao
		 */
		static String m_szNPCNames[][] = new String[4][];

		// Initialize all of our text!
		public static void GetText() throws IOException {
			String contents = new String(Files.readAllBytes(Paths.get(".\\text\\Towns.seb")));
			byte[] decodedTown = Base64.getDecoder().decode(contents);
			String decodedStringTown = new String(decodedTown);
			m_szTownNames = decodedStringTown.split("\r\n|\r|\n");

			// Get orc names
			contents = new String(Files.readAllBytes(Paths.get(".\\text\\Names_Orc.seb"))); // only male names lmao oops
			byte[] decodedName = Base64.getDecoder().decode(contents);
			String decodedStringName = new String(decodedName);
			m_szNPCNames[0] = decodedStringName.split("\r\n|\r|\n");

			contents = new String(Files.readAllBytes(Paths.get(".\\text\\Names_Human.seb")));
			decodedName = Base64.getDecoder().decode(contents);
			decodedStringName = new String(decodedName);
			m_szNPCNames[1] = decodedStringName.split("\r\n|\r|\n");

			contents = new String(Files.readAllBytes(Paths.get(".\\text\\Names_DarkElf.seb")));
			decodedName = Base64.getDecoder().decode(contents);
			decodedStringName = new String(decodedName);
			m_szNPCNames[2] = decodedStringName.split("\r\n|\r|\n");

			contents = new String(Files.readAllBytes(Paths.get(".\\text\\Names_WoodElf.seb")));
			decodedName = Base64.getDecoder().decode(contents);
			decodedStringName = new String(decodedName);
			m_szNPCNames[3] = decodedStringName.split("\r\n|\r|\n");
		}

		public String GetRandomName(Race race)
		{
			String race_name = race.GetName();
			switch (race_name)
			{
				case "Orc":
				{
					return m_szNPCNames[0][(int)(Math.random() * m_szNPCNames[0].length)];
				}
				case "Human":
				{
					return m_szNPCNames[1][(int)(Math.random() * m_szNPCNames[1].length)];
				}

				case "Wood Elf":
				{
					return m_szNPCNames[2][(int)(Math.random() * m_szNPCNames[3].length)];
				}

				case "Dark Elf":
				{
					return m_szNPCNames[2][(int)(Math.random() * m_szNPCNames[2].length)];
				}
			}

			return "";
		}


	}

	public static ObjectManager GetObjectManager()
	{
		return objectmanager;
	}

}
