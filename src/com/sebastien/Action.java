package com.sebastien;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;

enum ACTIONSTATUS
{
    SUCCESS,
    FAILED,
    UNKNOWN
}

enum ACTIONTYPE {
    UNKNOWN,
    ATTACK,
    FLEE,
    LOOT,
    MOVE,
    HELP,
    UPGRADE,
    SHOW,
    TALK
}

public class Action {
    ACTIONTYPE m_Type;
    String m_szAction;
    List<String> m_Parameters;
    String m_szOutput;
    ACTIONSTATUS m_Status = ACTIONSTATUS.UNKNOWN;

    public String toString()
    {
        switch (m_Type)
        {
            case UNKNOWN:   return "Unknown";
            case FLEE:      return "Flee";
            case LOOT:      return "Loot";
            case MOVE:      return "Move";
            case ATTACK:    return "Attack";
            case HELP:      return "Help";
            case UPGRADE:   return "Upgrade";
            case SHOW:      return "Show";
            case TALK:      return "Talk";
        }
        return "Unknown";
    }

    public static ACTIONTYPE toActionType(String str)
    {
        str = str.toLowerCase();
        switch (str)
        {
            case "unknown": return ACTIONTYPE.UNKNOWN;
            case "flee":    return ACTIONTYPE.FLEE;
            case "loot":    return ACTIONTYPE.LOOT;
            case "move":    return ACTIONTYPE.MOVE;
            case "attack":  return ACTIONTYPE.ATTACK;
            case "help":    return ACTIONTYPE.HELP;
            case "upgrade": return ACTIONTYPE.UPGRADE;
            case "show":    return ACTIONTYPE.SHOW;
            case "talk":    return ACTIONTYPE.TALK;
        }
        return ACTIONTYPE.UNKNOWN;
    }

    void FleeAction() throws IOException, ParseException // wont take any params
    {
        Player localent = Globals.GetLocalPlayer();
        if(localent.GetLocation() == null || localent.GetLocation().GetInfo() == null || localent.GetLocation().GetInfo().GetEncounter() == null) // ensure we are in an encounter/location
        {
            m_szOutput = "You cannot flee right now!\n";
            m_Status = ACTIONSTATUS.FAILED;
            return;
        }
        if(Globals.GetCurrentTown() == 0)
        {
            m_szOutput = "You cannot flee any further!\n";
            m_Status = ACTIONSTATUS.FAILED;
            return;
        }

        m_szOutput = "Fleeing " + localent.GetLocation().GetName() + "...\n";
        localent.GetLocation().GetInfo().GetEncounter().Destroy();
        Globals.SetCurrentTown(Globals.GetCurrentTown() - 1);
        localent.SetLocation(new Location(Globals.Text.m_szTownNames[Globals.GetCurrentTown()]));
        m_Status = ACTIONSTATUS.SUCCESS;
    }

    void LootAction() // params: body(name)
    {
        Player localent = Globals.GetLocalPlayer();

        if(localent.GetLocation() == null || localent.GetLocation().GetInfo() == null || localent.GetLocation().GetInfo().GetEncounter() == null) // ensure we are in an encounter/location
        {
            m_szOutput = "You cannot loot right now!\n";
            m_Status = ACTIONSTATUS.FAILED;
            return;
        }

        String bodyname = m_Parameters.get(0); // name
        if(bodyname == null)
        {
            m_szOutput = "You must specify a body!\n";
            m_Status = ACTIONSTATUS.FAILED;
            return;
        }

        for(NPC body : localent.GetLocation().GetInfo().GetEncounter().GetBodies())
        {
            if(body.IsAlive()) // got ressurected or something lol
            {
                System.out.printf("Cannot loot %s right now!\n", body.GetName());
                m_Status = ACTIONSTATUS.FAILED;
                return;
            }

            if(body.GetName().equalsIgnoreCase(bodyname)) {
                Item loot = body.GetInventory().GetItems().get(0); // Get the first item, dont plan on having more than 1 on npcs
                m_szOutput = "You found a " + loot.GetName() + "! Adding to inventory...\n";
                localent.GetInventory().AddItem(loot);
                m_Status = ACTIONSTATUS.SUCCESS;
                return;
            }
        }
        m_szOutput = "There is no body with the name " + bodyname + "!\n";
        m_Status = ACTIONSTATUS.FAILED;
    }

    void MoveAction()
    {

    }

    void AttackAction() // params: target, weapon
    {
        Player localent = Globals.GetLocalPlayer();

        if(localent.GetLocation() == null || localent.GetLocation().GetInfo() == null || localent.GetLocation().GetInfo().GetEncounter() == null) // ensure we are in an encounter/location
        {
            m_szOutput = "You cannot attack right now!\n";
            m_Status = ACTIONSTATUS.FAILED;
            return;
        }

        String targetname = m_Parameters.get(0); // target
        if(targetname == null)
        {
            m_szOutput = "You must specify a target!\n";
            m_Status = ACTIONSTATUS.FAILED;
            return;
        }

        String weaponname = m_Parameters.get(1); // weapon
        if(weaponname == null)
        {
            m_szOutput = "You must specify a weapon to use!\n";
            m_Status = ACTIONSTATUS.FAILED;
            return;
        }

        Item weapon = null;
        for(Item item : localent.GetInventory().GetItems())
        {
            if(item.GetName().equalsIgnoreCase(weaponname))
            {
                weapon = item;
                break;
            }
            if(item == localent.GetInventory().GetItems().get(localent.GetInventory().GetItems().size() - 1))
            {
                m_szOutput = "Item not found!\n";
                m_Status = ACTIONSTATUS.FAILED;
                return;
            }
        }

        NPC target = null;
        for(NPC npc : localent.GetLocation().GetInfo().GetEncounter().GetNPCs())
        {
            if(npc.GetName().equalsIgnoreCase(targetname))
            {
                target = npc;
                break;
            }

            if(npc == localent.GetLocation().GetInfo().GetEncounter().GetNPCs().get(localent.GetLocation().GetInfo().GetEncounter().GetNPCs().size() - 1))
            {
                m_szOutput = "Target not found!\n";
                m_Status = ACTIONSTATUS.FAILED;
                return;
            }
        }


        Damage damage = new Damage(weapon);
        target.HandleDamage(damage);
        m_szOutput = "Attacking " + target.GetName() + " with " + weapon.GetName() +"!\n" + damage.GetOutput();
        m_Status = ACTIONSTATUS.SUCCESS;
    }


    void UpgradeAction() // params: stat
    {
        Player localent = Globals.GetLocalPlayer();
        String stat = m_Parameters.get(0);
        if(stat == null)
        {
            m_szOutput = "Please enter an attribute.\n";
            m_Status = ACTIONSTATUS.FAILED;
            return;
        }
        stat = "m" + stat.substring(0, 1).toUpperCase() + stat.substring(1);
        Attribute attribute = localent.GetStats().FindAttribute(stat);
        if(attribute == null)
        {
            m_szOutput = "Invalid attribute specified!\n";
            m_Status = ACTIONSTATUS.FAILED;
            return;
        }

        int currentSkillpoints = localent.GetStats().FindAttribute("mSkillpoints").GetInt();
        if(currentSkillpoints <= 0)
        {
            m_szOutput = "You do not have enough skill points to upgrade that attribute!\n";
            m_Status = ACTIONSTATUS.FAILED;
            return;
        }

        float old = attribute.GetFloat();
        attribute.SetFloat(attribute.GetFloat() + 10.f);
        m_szOutput = attribute.GetName().substring(1) + " increased from " + old + " to " + attribute.GetFloat() + "!\n";
        localent.GetStats().FindAttribute("mSkillpoints").SetInt(currentSkillpoints - 1);
        m_Status = ACTIONSTATUS.SUCCESS;
    }

    void HelpAction() // params: action
    {
        String str = m_Parameters.get(0);
        ACTIONTYPE action = toActionType(str);
        switch (action)
        {
            case UPGRADE:   m_szOutput = str + " --> (attribute) Upgrade an attribute one level.\n"; break;
            case HELP:      m_szOutput = str + " --> Get help on certain functions.\n"; break;
            case ATTACK:    m_szOutput = str + " --> (target, weapon) Send an attack order to specified NPC.\n"; break;
            case LOOT:      m_szOutput = str + " --> (target) Loot the specified dead NPC.\n"; break;
            case FLEE:      m_szOutput = str + " --> Flee from the current encounter back towards the last location.\n"; break;
            case SHOW:      m_szOutput = str + " --> (list) Show a specified list (stats, inventory)"; break;
            case TALK:      m_szOutput = str + " --> (target) Attempt to talk to the NPC."; break;
            default:        m_szOutput = "Unknown command specified\n"; break;
        }
    }

    void ShowAction() // params: list to show
    {
        String list = m_Parameters.get(0);
        list = list.toLowerCase();
        switch (list)
        {
            case "inventory":
            {
                Globals.GetGUI().DrawInventoryItems();
                m_szOutput = "";
                break;
            }

            case "attributes":
            case "stats": Globals.GetGUI().DrawStats(); m_szOutput = ""; break;
        }
    }

    void TalkAction()
    {
        String szTarget = m_Parameters.get(0);

        Player localent = Globals.GetLocalPlayer();

        NPC target = null;

        for(NPC npc : localent.GetLocation().GetInfo().GetEncounter().GetNPCs())
        {
            if(npc.GetName().equalsIgnoreCase(szTarget))
            {
                target = npc;
                break;
            }

            if(npc == localent.GetLocation().GetInfo().GetEncounter().GetNPCs().get(localent.GetLocation().GetInfo().GetEncounter().GetNPCs().size() - 1))
            {
                m_szOutput = "Target not found!\n";
                m_Status = ACTIONSTATUS.FAILED;
                return;
            }
        }

        RELATION toPlayer = target.GetRelationship();
        switch (toPlayer)
        {
            case FRIENDLY:  m_szOutput = target.GetName() + " says: Hey there friend, how fares your travels?\n"; break;
            case HOSTILE:   m_szOutput = target.GetName() + " says: What do you think you're doing?! Get em!\n"; break;
            case NEUTRAL:   m_szOutput = target.GetName() + " simply grunts, not acknowledging you.\n"; break;
            case NONE:      m_szOutput = target.GetName() + " does not react.\n"; break;
        }
    }

    void ExecuteAction() throws IOException, ParseException {
        m_Type = toActionType(m_szAction);
        ACTIONTYPE lastAction = ACTIONTYPE.UNKNOWN;
        switch (m_Type)
        {
            case UNKNOWN:
            {
                m_szOutput = "Unknown action!\n";
                break;
            }
            case FLEE:
            {
                FleeAction();
                lastAction = ACTIONTYPE.FLEE;
                break;
            }
            case LOOT:
            {
                LootAction();
                lastAction = ACTIONTYPE.LOOT;
                break;
            }
            case MOVE:
            {
                MoveAction();
                lastAction = ACTIONTYPE.MOVE;
                break;
            }
            case ATTACK:
            {
                AttackAction();
                lastAction = ACTIONTYPE.ATTACK;
                break;
            }
            case HELP:
            {
                HelpAction();
                lastAction = ACTIONTYPE.HELP;
                break;
            }
            case UPGRADE:
            {
                UpgradeAction();
                lastAction = ACTIONTYPE.UPGRADE;
                break;
            }
            case SHOW:
            {
                ShowAction();
                lastAction = ACTIONTYPE.SHOW;
                break;
            }
            case TALK:
            {
                TalkAction();
                lastAction = ACTIONTYPE.TALK;
                break;
            }
        }
        Globals.SetLastActionType(lastAction);
    }

    public Action(String action, List<String> parameters) throws IOException, ParseException {
        m_szAction = action;
        m_Parameters = parameters;
    }

    public void Send() throws IOException, ParseException {
        ExecuteAction();
    }

    public String GetOutput()
    {
        return m_szOutput;
    }
    public ACTIONSTATUS GetStatus() { return m_Status; }

}
