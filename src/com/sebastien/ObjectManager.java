package com.sebastien;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ObjectManager {
    List<GameObject> m_Objects = new ArrayList<>();
    List<NPC> m_NPCs = new ArrayList<>();

    public GameObject GetObject(int idx) {
        return m_Objects.get(idx);
    }

    public NPC GetNPC(int idx) {
        return m_NPCs.get(idx);
    }

    public GameObject FindObject(String name) {
        for (GameObject obj : m_Objects) {
            if (obj.GetName().equals(name))
                return obj;
        }
        return null;
    }

    public NPC FindNPC(String name) {
        for (NPC obj : m_NPCs) {
            if (obj.GetName().equals(name))
                return obj;
        }
        return null;
    }

    public void UpdateNPC() {
        for (NPC obj : m_NPCs)
            obj.Update();
    }

    public void UpdateObject() throws IOException {
        for (GameObject obj : m_Objects)
            if (!(obj instanceof NPC)) // make sure it's not also an NPC....
            {
                obj.Update();
            }
    }

    public void Add(GameObject obj) {
        if (!m_Objects.contains(obj))
            m_Objects.add(obj);
    }

    public void Add(NPC npc) {
        if (!m_NPCs.contains(npc))
            m_NPCs.add(npc);
    }

    public void Remove(GameObject obj) {
        m_Objects.remove(obj);
    }

    public void Remove(NPC obj) {
        m_NPCs.remove(obj);
    }
}
