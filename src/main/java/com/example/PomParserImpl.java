package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;
import java.util.*;

public class PomParserImpl implements PomParser {

    private static final Logger logger = LoggerFactory.getLogger(PomParserImpl.class);

    @Override
    public List<Dependency> parse(String path) {
        List<Dependency> deps = new ArrayList<>();

        try {
            File file = new File(path);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);

            NodeList nodes = doc.getElementsByTagName("dependency");

            for (int i = 0; i < nodes.getLength(); i++) {
                Element el = (Element) nodes.item(i);

                String groupId = getTag(el, "groupId");
                String artifactId = getTag(el, "artifactId");
                String version = getTag(el, "version");

                if (groupId != null && artifactId != null && version != null) {
                    deps.add(new Dependency(groupId, artifactId, version));
                }
            }

        } catch (Exception e) {
            logger.error("Error parsing pom.xml at {}", path, e);
        }

        return deps;
    }

    private String getTag(Element el, String tag) {
        NodeList list = el.getElementsByTagName(tag);
        if (list.getLength() > 0) {
            return list.item(0).getTextContent();
        }
        return null;
    }
}
