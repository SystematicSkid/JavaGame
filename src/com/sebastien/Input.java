package com.sebastien;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Input {
    List<String> m_Parameters;

    void GetInput() throws IOException, ParseException {
        Scanner scan = Globals.GetScanner();
        m_Parameters = new ArrayList<>();
        String input = scan.nextLine();
        String[] szAll = input.split("\\s+");
        String action = szAll[0];
        if (szAll.length > 1 && szAll[1].contains("\"") && szAll[2].contains("\"")) {
            String param = szAll[1] + " " + szAll[2];
            param.replaceAll("\"", " ");

            StringBuilder finalParam = new StringBuilder(param);
            finalParam.setCharAt(param.length() - 1, ' ');
            finalParam.setCharAt(0, ' ');
            param = String.valueOf(finalParam);
            param = param.trim();
            m_Parameters.add(param);
        }
        if (szAll.length > 4 && szAll[3].contains("\"") && szAll[4].contains("\"")) {
            String param = szAll[3] + " " + szAll[4];
            StringBuilder finalParam = new StringBuilder(param);
            finalParam.setCharAt(param.length() - 1, ' ');
            finalParam.setCharAt(0, ' ');
            param = String.valueOf(finalParam);
            param = param.trim();

            m_Parameters.add(param);
        } else if (szAll.length == 3) {
            if (szAll.length > 1)
                m_Parameters.add(szAll[1]);
            if (szAll.length > 2)
                m_Parameters.add(szAll[2]);
        } else if (szAll.length == 2)
            m_Parameters.add(szAll[1]);

        Action order = new Action(action, m_Parameters);
        order.Send();
        System.out.print(order.GetOutput());
       // m_Parameters.clear();       // clean
    }
}