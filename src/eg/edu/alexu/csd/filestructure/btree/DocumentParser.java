package eg.edu.alexu.csd.filestructure.btree;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.management.RuntimeErrorException;
import javax.xml.parsers.*;
import java.io.*;
import java.util.HashMap;

public class DocumentParser {
	public DocumentParser() {

	}

	public HashMap<String, HashMap<String, Integer>> parse(String path) {
		HashMap<String, HashMap<String, Integer>> fileMap = new HashMap<>();
		File fXmlFile = new File(path);
		if (!fXmlFile.exists()) {
			throw new RuntimeErrorException(new Error("file not found"));
		}
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			Element root = doc.getDocumentElement();
			NodeList rootDocuments = root.getElementsByTagName("doc");
			for (int i = 0; i < rootDocuments.getLength(); i++) {
				Node n = rootDocuments.item(i);
				String body = n.getTextContent();
				HashMap<String, Integer> frequencyMap = indexBody(body);
				Node attributeID = n.getAttributes().item(0);
				fileMap.put(attributeID.getNodeValue(), frequencyMap);
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fileMap;
	}

	public HashMap<String, Integer> indexBody(String body) {
		HashMap<String, Integer> frequencyMap = new HashMap<>();
		body = body.trim();
		String[] words = body.split("\\s+");
		for (int i = 0; i < words.length; i++) {
			if (frequencyMap.get(words[i].toLowerCase()) == null) {
				frequencyMap.put(words[i].toLowerCase(), 1);
			} else {
				frequencyMap.put(words[i].toLowerCase(), frequencyMap.get(words[i].toLowerCase()) + 1);
			}
		}
		return frequencyMap;
	}
}
