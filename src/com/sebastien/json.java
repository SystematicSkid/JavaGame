package com.sebastien;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

class json {
    static JSONParser parser = new JSONParser();

    public json() {
    }

    private static Item GetItem(GameObject owner, String name, ITEMTYPE type) throws IOException, ParseException {
        String file = Util.ItemTypeToString(type) + "_" + name;
        String path = ".\\items\\" + file + ".seb";
        String contents = new String(Files.readAllBytes(Paths.get(path)));
        byte[] decodedTown = Base64.getDecoder().decode(contents);
        String decodedString = new String(decodedTown);
        //System.out.println(decodedString);

        JSONObject item = (JSONObject) parser.parse(decodedString);
        JSONObject stats = (JSONObject) item.get("stats");

        return new Item(owner, name, type, item, stats);
    }

    static Item GetRandomItem(GameObject owner) throws IOException, ParseException {
        List<String> files = new ArrayList<>();
        final File itemfolder = new File(".\\items\\");
        for (final File fileEntry : Objects.requireNonNull(itemfolder.listFiles())) {
            files.add(fileEntry.getName());
        }
        String rand = files.get((int)(Math.random() * files.size()));
        rand = rand.substring(0, rand.indexOf('.'));
        String name = rand.substring(rand.indexOf('_') + 1);
        String type = rand.substring(0, rand.indexOf('_'));
        //System.out.println(rand + "\n" + name + "\n" + type);
        return json.GetItem(owner, name, Util.StringToItemType(type));
    }

    public static Item GetRandomItemType(GameObject owner, ITEMTYPE type) throws IOException, ParseException {
        List<String> files = new ArrayList<>();
        final File itemfolder = new File(".\\items\\");
        for (final File fileEntry : Objects.requireNonNull(itemfolder.listFiles())) {
            files.add(fileEntry.getName());
        }
        ITEMTYPE itemtype = ITEMTYPE.UNKNOWN;
        String name ="";
        while(itemtype != type) {
            String rand = files.get((int) (Math.random() * files.size()));
            rand = rand.substring(0, rand.indexOf('.'));
            name = rand.substring(rand.indexOf('_') + 1);
            String strtype = rand.substring(0, rand.indexOf('_'));
            itemtype = Util.StringToItemType(strtype);
        }
        //System.out.println(rand + "\n" + name + "\n" + type);
        return json.GetItem(owner, name, itemtype);
    }
}
