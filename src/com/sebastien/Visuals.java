package com.sebastien;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Visuals {
    private List<String> m_Elements = new ArrayList<>();
    private Player localent;

    public Visuals() {

    }

    private void GetHealth() {
        float health = localent.GetStats().FindAttribute("mHealth").GetFloat();
        m_Elements.add("HP: " + Float.toString(health));
    }

    private void GetLevel() {
        int level = localent.GetStats().FindAttribute("mLevel").GetInt();
        m_Elements.add("Level: " + Integer.toString(level));
    }

    private void GetMana() {
        float mana = localent.GetStats().FindAttribute("mMana").GetFloat();
        m_Elements.add("Mana: " + Float.toString(mana));
    }

    private void GetLocation() {
        if (localent.GetLocation() != null)
            m_Elements.add("Location: " + localent.GetLocation().GetName());
        else
            m_Elements.add("Location: None");
    }

    private void DrawHeader(String title) {
        int desiredlength = 100;
        int strlength = title.length();
        int perside = (desiredlength - strlength) / 2;
        for (int i = 0; i < perside; i++)
            System.out.print("-");
        System.out.print(title);
        for (int i = 0; i < perside; i++)
            System.out.print("-");
        System.out.print("\n");
    }

    private void DrawHUD() {
        for (String element : m_Elements) {
            System.out.printf("|\t%s\t", element);
        }
        System.out.print("|\n");
    }

    private void DrawEncounter() {
        if (localent.GetLocation() == null)
            return;

        if (localent.GetLocation().GetInfo() == null)
            return;

        if (localent.GetLocation().GetInfo().GetEncounter() == null)
            return;

        DrawHeader("Encounter");
        Encounter encounter = localent.GetLocation().GetInfo().GetEncounter();
        for (NPC npc : encounter.GetNPCs()) {
            float health = npc.GetStats().FindAttribute("mHealth").GetFloat();
            int level = npc.GetStats().FindAttribute("mLevel").GetInt();

            System.out.print("| ");
            System.out.printf("[%s] ", npc.GetRace().GetName());
            System.out.print(npc.GetName());
            System.out.printf(" | HP: %s\t| Level: %s\n", Float.toString(health), Integer.toString(level));

        }
        DrawHeader("");
    }

    void DrawInventoryItems() {
        if (localent.GetInventory() == null)
            return;
        if (localent.GetInventory().GetItems().size() == 0)
            return;

        DrawHeader("Inventory");

        for (Item item : localent.GetInventory().GetItems()) {
            System.out.print("| ");
            System.out.printf("[%s] ", Util.ItemTypeToString(item.GetType()));
            System.out.print(item.GetName());
            System.out.printf(" | Physical Damage: %s\t| Magic Damage: %s\n", Float.toString(item.GetStats().FindAttribute("mPhysicalDamage").GetFloat()), Float.toString(item.GetStats().FindAttribute("mMagicDamage").GetFloat()));
        }

        DrawHeader("");
    }

    void DrawStats() {
        if (localent.GetStats() == null)
            return;

        DrawHeader("Attributes");
        for (Attribute attribute : localent.GetStats().BaseAttributes) {
            String name = attribute.GetName().substring(1);
            System.out.print("| ");
            if (attribute.GetType() == TYPE.FLOAT)
                System.out.printf("[%s] ", Float.toString(attribute.GetFloat()));
            else
                System.out.printf("[%s] ", Integer.toString(attribute.GetInt()));
            System.out.print(name);
            System.out.print("\n");

        }
        for (Attribute attribute : localent.GetStats().AdditionalAttributes) {
            String name = attribute.GetName().substring(1);
            System.out.print("| ");
            if (attribute.GetType() == TYPE.FLOAT)
                System.out.printf("[%s]\t", Float.toString(attribute.GetFloat()));
            else
                System.out.printf("[%s]\t", Integer.toString(attribute.GetInt()));
            System.out.print(name);
            System.out.print("\n");

        }

        for (Attribute attribute : localent.GetRace().GetAttributes().AdditionalAttributes) {
            String name = attribute.GetName().substring(1);
            System.out.print("| ");
            if (attribute.GetType() == TYPE.FLOAT)
                System.out.printf("[%s]\t", Float.toString(attribute.GetFloat()));
            else
                System.out.printf("[%s]\t", Integer.toString(attribute.GetInt()));
            System.out.print(name);
            System.out.print("\n");

        }

        DrawHeader("");
    }

    void DrawCharacterCreation() {
        DrawHeader("Character Creation");
    }

    void Draw() throws IOException, InterruptedException {
        // Start drawing by clearing the screen
        Util.cls();
        DrawHeader("Information");
        DrawHUD();
        DrawHeader("");
        System.out.print("\n");
        DrawEncounter();
        System.out.print("\n");


    }

    /**
     * Updates all needed variables for the gui
     */
    public void Update() {
        localent = Globals.GetLocalPlayer();
        m_Elements.clear();
        GetHealth();
        GetLevel();
        GetMana();
        GetLocation();
    }
}
