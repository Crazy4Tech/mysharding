package org.sharding.configuration;

import java.util.List;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 
 * @author pc
 *
 */
public abstract class AbstractBulider {

	public Document createDocument(InputStream inputStream) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    factory.setNamespaceAware(false);
	    factory.setIgnoringComments(true);
	    factory.setIgnoringElementContentWhitespace(false);
	    factory.setCoalescing(false);
	    factory.setExpandEntityReferences(true);

	    DocumentBuilder builder = factory.newDocumentBuilder();
	    return builder.parse(inputStream);
	}
	
	/**
	 * simple xpath
	 * @param document
	 * @param xpath
	 * @return
	 */
	public List<Node> xpath (Document document, String xpath){
		String[] nodenames = xpath.split("/");
		NodeList nodelist  = document.getChildNodes();
		final List<Node> results = new ArrayList<Node>();
		child(results, nodelist, nodenames, 1);
		return results; 
	}
	
	private void child(final List<Node> results, NodeList nodelist, String[] nodenames, int index)
	{
		
		for(int i=0; i<nodelist.getLength(); i++)
		{
			Node node = nodelist.item(i);
			if(node.getNodeType()== Node.ELEMENT_NODE)
			{
				if(node.getNodeName().equals(nodenames[index]))
				{
					if(index<nodenames.length-1){
						child(results,node.getChildNodes(), nodenames, ++index);
						break;
					}else{
						results.add(node);
					}
				}
			}
		}
	}
	
	
}
