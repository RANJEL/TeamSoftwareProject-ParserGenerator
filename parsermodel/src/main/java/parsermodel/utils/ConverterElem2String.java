/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parsermodel.utils;

import org.jsoup.nodes.Element;

/**
 * Pomocna trida, ktera z Elementu, vytvori string, ve kterem neni obsah
 * (.text()), jen je zachycena struktura tagů do jediné řádky stringu
 *
 * @author Jan Lejnar
 */
public class ConverterElem2String {

    private static void appendElem(StringBuilder stringBuilder, Element elem) {
        if (elem == null)
            return;
        
        stringBuilder.append(elem.tagName() + ";");

        for (Element child : elem.children()) {
            appendElem(stringBuilder, child);
        }

        stringBuilder.append("/" + elem.tagName() + ";");
    }

    public static String getStructureInString(Element elem) {
        StringBuilder stringBuilder = new StringBuilder();

        appendElem(stringBuilder, elem);

        return stringBuilder.toString();
    }
}
