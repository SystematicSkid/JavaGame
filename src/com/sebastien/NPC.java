package com.sebastien;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

enum RELATION {
    NONE,
    FRIENDLY,
    NEUTRAL,
    HOSTILE
}

class NPC extends GameObject {
    private Attributes m_Stats;
    Race m_Race;
    List<Race> m_Allies;
    List<Race> m_Enemies;
    Inventory m_Inventory;
    Encounter m_Encounter;
    private boolean m_bIsAlive;
    private boolean m_bIsProvoked = false;


    public NPC() {
        super();
        m_Stats = new Attributes();
        m_bIsAlive = true;
        m_Inventory = new Inventory(this);
        //Globals.GetObjectManager().Add(this);
    }

    private static NPC GenerateNPC(Encounter encounter) throws IOException, ParseException {
        int rand = (int) Util.RandInt(1, 4);
        switch (rand) {
            case 1:
                return new Orc(encounter);
            case 2:
                return new Humans(encounter);
            case 3:
                return new DarkElfs(encounter);
            case 4:
                return new WoodElfs(encounter);
        }
        return null;
    }

    static NPC GenerateNPC(Encounter encounter, Race race) throws IOException, ParseException {
        NPC npc = GenerateNPC(encounter);
        while (!Objects.requireNonNull(npc).GetRace().GetName().equals(race.GetName())) {
            npc = GenerateNPC(encounter);
        }
        Globals.GetObjectManager().Add(npc);
        return npc;
    }

    Inventory GetInventory() {
        return m_Inventory;
    }

    protected RELATION GetRelationship() {
        Player localplayer = Globals.GetLocalPlayer();
        for (Race race : m_Allies) {
            if (race.GetName().equalsIgnoreCase(localplayer.GetRace().GetName()))
                return RELATION.FRIENDLY;
        }

        for (Race race : m_Enemies) {
            if (race.GetName().equalsIgnoreCase(localplayer.GetRace().GetName()))
                return RELATION.HOSTILE;
        }
        return RELATION.NEUTRAL;
    }

    public Attributes GetStats() {
        return m_Stats;
    }

    public Race GetRace() {
        return m_Race;
    }

    private void Flee() {
        this.m_Encounter.Leave(this);
    }

    boolean IsAlive() {
        return m_bIsAlive;
    }

    void HandleDamage(Damage source) {
        m_bIsProvoked = true;
        float armor, magicresist;
        float magicpenalty, damage;
        armor = this.GetStats().FindAttribute("mArmor").GetFloat();

        float physicalpenalty = source.m_fPenetration / armor;
        damage = (source.m_fFlat * physicalpenalty) + source.m_fForce;

        if (source.m_bIsMagic) {
            magicresist = this.GetStats().FindAttribute("mMagicResist").GetFloat();
            magicpenalty = source.m_fMagicPenetration / magicresist;
            damage = (source.m_MagicDamage * magicpenalty) + source.m_fForce;
        }
        float health = this.GetStats().FindAttribute("mHealth").GetFloat();
        this.GetStats().FindAttribute("mHealth").SetFloat(health - damage);
    }

    protected void HandleAttack() {
        Player localplayer = Globals.GetLocalPlayer();
        if (localplayer == null)
            return;
        Damage order = new Damage(m_Inventory.m_Items.get(0));
        localplayer.HandleDamage(order);
        System.out.printf("%s has attacked %s with %s!\n", m_szName, localplayer.GetName(), m_Inventory.m_Items.get(0).GetName());
    }

    private void Kill() {
        if (!m_bIsAlive)
            return;
        //Globals.SetLastEvent(this.GetName() + " has been killed!");
        //Globals.GetObjectManager().Remove(this);
        Player localent = Globals.GetLocalPlayer();
        localent.LevelUp();
    }


    public void Update() {
        if (this.GetStats().FindAttribute("mHealth").GetFloat() <= 0) {
            Kill();
            m_bIsAlive = false;
        }
        if (m_bIsAlive) {
            if (m_Race.GetName().equalsIgnoreCase("human")) {
                if (this.GetRace().GetAttributes().FindAttribute("mMorale").GetFloat() < 25.f)
                    Flee();
            }
            if (m_bIsProvoked || GetRelationship() == RELATION.HOSTILE)
                HandleAttack();
        }
    }


}