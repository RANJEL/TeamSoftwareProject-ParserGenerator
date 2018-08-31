package parser.utils;

import parsermodel.tree.nodes.Node;
import parsermodel.tree.nodes.TagNode;

/**
 * @author Jakub Šedý
 */
public class ConverterNode2String {

    public static void appendNode(StringBuilder stringBuilder, Node n) {
        TagNode tn = (TagNode) n;
        if (tn == null)
            return;

        stringBuilder.append(tn.getTagName() + ";");
        if (tn.isLastTagNode()) {
            stringBuilder.append("/" + tn.getTagName() + ";");
            return;
        }

        for (int i = 0; i < n.getSuccessorNodes().size(); i++) {
            appendNode(stringBuilder, n.getSuccessorNodes().get(i));
        }

        stringBuilder.append("/" + tn.getTagName() + ";");
    }

    public static String getStructureInString(Node n) {
        StringBuilder stringBuilder = new StringBuilder();

        appendNode(stringBuilder, n);

        return stringBuilder.toString();
    }
}
