package com.sebastien;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class LocationInfo
{
    private Location m_Location;
    private String m_szAction;
    private String m_szActionDescription;
    private Encounter m_Encounter;
    private int m_iEnemies;
    private List<NPC> m_Bodies = new ArrayList<>();
    boolean m_bDestroyed = false;

    public String GetAction() { return m_szAction; }
    public String GetDescription() { return m_szActionDescription; }
    Encounter GetEncounter() { return m_Encounter; }
    public Location GetLocation() { return m_Location; }

    LocationInfo(Location location)
    {
        m_szAction = "Action";
        m_Location = location;
        m_iEnemies = ((Globals.GetLocalPlayer().GetStats().FindAttribute("mLevel").GetInt() > 3) ? 2 : 1);
        //m_Encounter = new Encounter(this);
    }

    void CreateEncounter() throws IOException, ParseException {
        if(!m_bDestroyed) {
            m_Encounter = new Encounter(this);
            Globals.SetIsInEncounter(true);
        }
    }

    void EndEncounter()
    {
        m_Encounter = null;
        m_bDestroyed = true;
        Globals.SetIsInEncounter(false);
    }

    List<NPC> GetBodies() {return m_Bodies; }
    int GetNumEnemies() { return  m_iEnemies; }
}

class Location extends GameObject
{
    private Race m_Owner;
    private LocationInfo m_Info;

    LocationInfo GetInfo() { return m_Info; }
    Race GetOwner() { return m_Owner; }

    public Location(String name) {
        super();
        m_Owner = Util.GetRandomRace();
        m_szName = name;
        m_Info = new LocationInfo(this);
        ShowText();
    }

    public void ShowText()
    {
        System.out.print("\nAs you approach the town, you see a sign with the words " + GetName() + " scribbled on it...\nAs you look around you can see a lot of " + m_Owner.GetName() + "'s lingering around.\n");
    }
}
