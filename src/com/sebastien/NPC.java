package com.sebastien;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Attr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

enum RELATION
{
	NONE,
	FRIENDLY,
	NEUTRAL,
	HOSTILE
}

class NPC extends GameObject
{
	Attributes m_Stats;
  	Race m_Race;
	List<Race> m_Allies;
	List<Race> m_Enemies;
	Inventory m_Inventory;
	Encounter m_Encounter;
	boolean   m_bIsAlive;


	public Inventory GetInventory()
	{
		return m_Inventory;
	}

  	public RELATION GetRelationship()
	{
		Player localplayer = Globals.GetLocalPlayer();
		for(Race race : m_Allies)
		{
			if(race.GetName().equalsIgnoreCase(localplayer.GetRace().GetName()))
				return RELATION.FRIENDLY;
		}

		for(Race race : m_Enemies)
		{
			if(race.GetName().equalsIgnoreCase(localplayer.GetRace().GetName()))
				return RELATION.HOSTILE;
		}
		return RELATION.NEUTRAL;
	}

	public Attributes GetStats() { return m_Stats; }
	public Race GetRace() {return m_Race;}

	public NPC()
	{
		m_Stats = new Attributes();
		m_bIsAlive = true;
		m_Inventory = new Inventory(this);
		//Globals.GetObjectManager().Add(this);
	}

	void Flee()
	{
		this.m_Encounter.Leave(this);
	}

	public boolean IsAlive()
	{
		return m_bIsAlive;
	}

	public void HandleDamage(Damage source)
	{
		float armor, magicresist;
		float magicpenalty, damage;
		armor = this.GetStats().FindAttribute("mArmor").GetFloat();

		float physicalpenalty = source.m_fPenetration / armor;
		damage = (source.m_fFlat * physicalpenalty) + source.m_fForce;

		if(source.m_bIsMagic)
		{
			magicresist = this.GetStats().FindAttribute("mMagicResist").GetFloat();
			magicpenalty = source.m_fMagicPenetration / magicresist;
			damage = (source.m_MagicDamage * magicpenalty) + source.m_fForce;
		}
		float health = this.GetStats().FindAttribute("mHealth").GetFloat();
		this.GetStats().FindAttribute("mHealth").SetFloat(health - damage);
	}

	public void HandleAttack()
	{
		Player localplayer = Globals.GetLocalPlayer();
		if(localplayer == null)
			return;
		Damage order = new Damage(m_Inventory.m_Items.get(0));
		localplayer.HandleDamage(order);
		System.out.printf("%s has attacked %s with %s!\n", m_szName, localplayer.GetName(), m_Inventory.m_Items.get(0).GetName());
		return;
	}

	public static NPC GenerateNPC(Encounter encounter) throws IOException, ParseException {
		int rand = (int) Util.RandInt(1, 4);
		switch (rand)
		{
			case 1: return new Orc(encounter);
			case 2: return new Humans(encounter);
            case 3: return new DarkElfs(encounter);
            case 4: return new WoodElfs(encounter);
		}
		return null;
	}

	public static NPC GenerateNPC(Encounter encounter, Race race) throws IOException, ParseException {
		NPC npc = GenerateNPC(encounter);
		while(!npc.GetRace().GetName().equals(race.GetName())) {
            npc = GenerateNPC(encounter);
        }
		Globals.GetObjectManager().Add(npc);
		return npc;
	}

	void Kill()
	{
		Globals.SetLastEvent(this.GetName() + " has been killed!");
		this.m_Encounter.GetNPCs().remove(this);
		Globals.GetObjectManager().Remove(this);
	}



	public void Update()
	{
		if(this.GetStats().FindAttribute("mHealth").GetFloat() <= 0)
			m_bIsAlive = false;
		if(m_bIsAlive) {
			if (m_Race.GetName().equalsIgnoreCase("human")) {
				if (this.GetRace().GetAttributes().FindAttribute("mMorale").GetFloat() < 25.f)
					Flee();
			}
			HandleAttack();
		}
	}


}