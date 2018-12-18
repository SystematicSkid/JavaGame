package com.sebastien;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

class Orc extends NPC
{


	@Override
	public void Init()
	{
		m_Race = new Orcish();
	}

	public Orc(Encounter encounter) throws IOException, ParseException
	{
		m_Encounter = encounter;
		int rand = (int) Util.RandInt(0, Globals.Text.m_szNPCNames[0].length - 1);
		m_szName = Globals.Text.m_szNPCNames[0][rand];
		m_Inventory.AddItem(json.GetRandomItemType(this, ITEMTYPE.AXE));
		this.m_Allies = new ArrayList<>();
		this.m_Allies.add(Race.StringToRace("Orc"));
		this.m_Allies.add(Race.StringToRace("Dark Elf"));
		this.m_Enemies = new ArrayList<>();
		this.m_Enemies.add(Race.StringToRace("Human"));
		Init();
	}
}