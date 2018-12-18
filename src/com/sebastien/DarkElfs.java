package com.sebastien;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public class DarkElfs extends NPC
{
    @Override
    public void Init()
    {
        m_Race = new DarkElf();
    }

    public DarkElfs(Encounter encounter) throws IOException, ParseException {
        int rand = (int) Util.RandInt(0, Globals.Text.m_szNPCNames[2].length - 1);
        m_szName = Globals.Text.m_szNPCNames[2][rand];
        m_Race = new DarkElf();
        m_Inventory.AddItem(json.GetRandomItemType(this, ITEMTYPE.DAGGER));
        this.m_Allies = new ArrayList<>();
        this.m_Enemies = new ArrayList<>();
        this.m_Enemies.add(Race.StringToRace("Human"));
        this.m_Enemies.add(Race.StringToRace("Wood Elf"));
        m_Encounter = encounter;
    }
}
