//package uk.ac.ebi.chebi.ontology.core.parser;
//
//import uk.ac.ebi.chebi.ontology.core.Definition;
//import uk.ac.ebi.chebi.ontology.core.matcher.FormulaMatcher;
//import uk.ac.ebi.chebi.ontology.core.matcher.IMatcher;
//import uk.ac.ebi.chebi.ontology.core.matcher.logical.AndMatcher;
//import uk.ac.ebi.chebi.ontology.core.matcher.skeleton.FragmentMatcher;
//import uk.ac.ebi.chebi.ontology.core.matcher.skeleton.RGroupMatcher;
//import uk.ac.ebi.chebi.ontology.core.matcher.skeleton.SubStructureMatcher;
//import org.w3c.dom.Document;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
//import org.xml.sax.SAXException;
//
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.parsers.ParserConfigurationException;
//import java.io.*;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Class for parsing XML definition
// *
// * @author Duan Lian
// */
//public class XMLParser {
//    /**
//     * Parse XML Definition
//     *
//     * @param file File object to parse
//     * @return
//     * @throws ParserConfigurationException
//     * @throws IOException
//     * @throws SAXException
//     */
//    public List<Definition> parse(File file) throws ParserConfigurationException, IOException, SAXException {
//        return this.parse(new FileInputStream(file));
//    }
//
//    /**
//     * Parse XML Definition
//     *
//     * @param fileName filename of File to parse
//     * @return
//     * @throws ParserConfigurationException
//     * @throws IOException
//     * @throws SAXException
//     */
//    public List<Definition> parse(String fileName) throws ParserConfigurationException, IOException, SAXException {
//        return this.parse(new FileInputStream(fileName));
//    }
//
//    /**
//     * Parse XML Definition from String content
//     *
//     * @param content String content to parse
//     * @return
//     * @throws ParserConfigurationException
//     * @throws IOException
//     * @throws SAXException
//     */
//    public List<Definition> parseContent(String content) throws ParserConfigurationException, IOException, SAXException {
//        return this.parse(new ByteArrayInputStream(content.getBytes()));
//    }
//
//    /**
//     * Parse XML Definition
//     *
//     * @param is InputStream to parse
//     * @return
//     * @throws ParserConfigurationException
//     * @throws IOException
//     * @throws SAXException
//     */
//    public List<Definition> parse(InputStream is) throws ParserConfigurationException, IOException, SAXException {
//        List<Definition> result = new ArrayList<Definition>();
//        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//        DocumentBuilder db = dbf.newDocumentBuilder();
//        Document doc = db.parse(is);
//        doc.getDocumentElement().normalize();
//        NodeList nodeList = doc.getElementsByTagName("definition");
//        for (int i = 0; i < nodeList.getLength(); i++) {
//            Node node = nodeList.item(i);
//            result.add(parseDefNode(node));
//        }
//        return result;
//    }
//
//    /**
//     * Parse definition node.
//     *
//     * @param node Node to parse
//     * @return Definition class
//     */
//    private Definition parseDefNode(Node node) {
//        Definition definition = new Definition();
//
//        String id = getNodeAttribute(node, "id");
//        String comment = getNodeAttribute(node, "comment");
//        if (id != null) definition.setId(id);
//        if (comment != null) definition.setComment(comment);
//
//        NodeList childNodes = node.getChildNodes();
//        List<IMatcher> matchers = new ArrayList<IMatcher>();
//        for (int i = 0; i < childNodes.getLength(); i++) {
//            Node matcherNode = childNodes.item(i);
//            IMatcher matcher = this.parseMatcherNode(matcherNode);
//            if (matcher != null) matchers.add(matcher);
//        }
//
//        if (matchers.size() == 1) {
//            definition.setMatcher(matchers.get(0));
//        } else if (matchers.size() > 1) {
//            AndMatcher andMatcher = new AndMatcher();
//            andMatcher.addAll(matchers);
//            definition.setMatcher(andMatcher);
//        }
//
//        return definition;
//    }
//
//    /**
//     * Parse matcher node
//     *
//     * @param node Node to parse
//     * @return IMatcher class
//     */
//    private IMatcher parseMatcherNode(Node node) {
//        if (node.getNodeName().equals("formula")) {
//            FormulaMatcher formulaMatcher = new FormulaMatcher();
//            return formulaMatcher;
//        } else if (node.getNodeName().equals("fragment")) {
//            FragmentMatcher fragmentMatcher = null;
//            if (this.getNodeAttribute(node, "match_type").equals("substructure")) {
//                fragmentMatcher = new SubStructureMatcher();
//            }else if (this.getNodeAttribute(node, "match_type").equals("rgroup")) {
//                fragmentMatcher = new RGroupMatcher();
//            }
//            if (fragmentMatcher != null) {
//                fragmentMatcher.readQueryStructure(this.getNodeAttribute(node, "file_format"), node.getTextContent());
//            }
//            return fragmentMatcher;
//        }
//        return null;
//    }
//
//    private String getNodeAttribute(Node node, String attribute) {
//        Node attributeNode = node.getAttributes().getNamedItem(attribute);
//        if (attributeNode == null) {
//            return null;
//        }
//        return attributeNode.getNodeValue();
//    }
//
//}
