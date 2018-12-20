package com.sebastien;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

class Humans extends NPC
{
    Humans(Encounter encounter) throws IOException, ParseException {
        int rand = (int) Util.RandInt(0, Globals.Text.m_szNPCNames[1].length - 1);
        m_szName = Globals.Text.m_szNPCNames[1][rand];
        m_Race = new Human();
        m_Inventory.AddItem(json.GetRandomItemType(this, ITEMTYPE.SWORD));
        this.m_Allies = new ArrayList<>();
        this.m_Allies.add(Race.StringToRace("Human"));
        this.m_Allies.add(Race.StringToRace("Wood Elf"));
        this.m_Enemies = new ArrayList<>();
        this.m_Enemies.add(Race.StringToRace("Dark Elf"));
        this.m_Enemies.add(Race.StringToRace("Orc"));
        m_Encounter = encounter;
    }
}
