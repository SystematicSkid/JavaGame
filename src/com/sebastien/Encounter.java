package com.sebastien;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class Encounter extends GameObject
{
	int m_iTurnNumber;
	List<String> m_szMessages = new ArrayList<>();
	List<NPC> m_NPCs = new ArrayList<>();
	List<NPC> m_Bodies = new ArrayList<>();
	LocationInfo m_LocationInfo;

	public void Leave(GameObject obj)
	{
		m_szMessages.add(obj.GetName() + " has left the fight!");
		m_NPCs.remove(obj);
		Globals.GetObjectManager().Remove(obj);
	}

	public void Destroy()
	{
		m_iTurnNumber = -1;
		m_NPCs.clear();
		//m_Bodies.clear();
	}

	public Encounter(LocationInfo info) throws IOException, ParseException {
		m_szName = "Encounter";
		m_LocationInfo = info;
		m_NPCs.clear();
		m_iTurnNumber = 0;
		// Generate random npc list based on player level + random seed\
		int numEnemies =  1; //Globals.GetLocalPlayer().GetStats().FindAttribute("mLevel").GetInt() > 3 ? 8 : 2;
		for(int i = 0; i < numEnemies; i++)
		{
			NPC npc = NPC.GenerateNPC(this, info.GetLocation().GetOwner());
			//Globals.GetObjectManager().Add(npc);
			m_NPCs.add(npc);
		}
	}

	@Override
	public void Update()
	{
		m_szMessages.clear();
		m_iTurnNumber++;



		for(NPC npc : m_NPCs)
		{

			if(npc.GetStats().FindAttribute("mHealth").GetFloat() <= 0.f)
			{
				m_Bodies.add(npc);	// move from current npc to dead npcs :)
				System.out.print(npc.GetName() + " has died while in a fight!\n");
				m_NPCs.remove(npc);
				break;
			}
		}

		if(m_NPCs.size() == 0 ) // empty, no more npcs
		{
			Destroy();
			Globals.SetLastEvent("You have successfully wiped out every living being in " + m_LocationInfo.GetLocation().GetName() + "! I hope you're proud...\n");
			return;
		}

	}

	public List<NPC> GetNPCs() { return m_NPCs; }
	public List<NPC> GetBodies() {return m_Bodies; }
	//public void UpdateNPCs()
	//{
	//	for(NPC npc : m_NPCs)
	//		npc.Update();
	//}
}