/*
 * $Id$version Apr 25, 2004 5:18:30 PM $user Exp $
 * 
 * Copyright 2003 (C) Sam Pullara. All Rights Reserved.
 * 
 * Redistribution and use of this software and associated documentation
 * ("Software"), with or without modification, are permitted provided that the
 * following conditions are met: 1. Redistributions of source code must retain
 * copyright statements and notices. Redistributions must also contain a copy of
 * this document. 2. Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution. 3. The
 * name "groovy" must not be used to endorse or promote products derived from
 * this Software without prior written permission of The Codehaus. For written
 * permission, please contact info@codehaus.org. 4. Products derived from this
 * Software may not be called "groovy" nor may "groovy" appear in their names
 * without prior written permission of The Codehaus. "groovy" is a registered
 * trademark of The Codehaus. 5. Due credit should be given to The Codehaus -
 * http://groovy.codehaus.org/
 * 
 * THIS SOFTWARE IS PROVIDED BY THE CODEHAUS AND CONTRIBUTORS ``AS IS'' AND ANY
 * EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE CODEHAUS OR ITS CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *  
 */
package groovy.xml.dom;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import java.util.Iterator;

/**
 * @author sam
 * @author paulk
 */
public class DOMCategory {

    public static Object get(Element element, String elementName) {
        return getAt(element, elementName);
    }

    public static Object getAt(Element element, int i) {
        if (element.hasChildNodes()) {
            NodeList nodeList = element.getChildNodes();
        		return nodeList.item(i);
        }
        return null;
    }

    public static Object getAt(Element element, String elementName) {
        if (elementName.startsWith("@")) {
            String attrName = elementName.substring(1);
            	return element.getAttribute(attrName);
        }
        if (element.hasChildNodes()) {
            NodeList nodeList = element.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Object node = nodeList.item(i);
                if (node instanceof Element) {
                    Element child = (Element) node;
                    child.hasChildNodes();
                    if(child.getTagName().equals(elementName)) {
                        return child;
                    }
                }
            }
        }
        return null;
    }

    public static String name(Element element) {
        return element.getNodeName();
    }

    public static Node parent(Node node) {
        return node.getParentNode();
    }

    public static String text(Element element) {
        if (!element.hasChildNodes()) {
            return null;
        }
        if (element.getFirstChild().getNodeType() != Node.TEXT_NODE) {
            return null;
        }
        return element.getFirstChild().getNodeValue();
    }

    public static Iterator iterator(NodeList nodeList) {
        return new NodeListIterator(nodeList);
    }

    private static class NodeListIterator implements Iterator {
        private NodeList nodeList;
        private int currentItem;

        public NodeListIterator(NodeList nodeList) {
            this.nodeList = nodeList;
            currentItem = 0;
        }

        public void remove() {
            Node node = nodeList.item(currentItem);
            node.getParentNode().removeChild(node);
        }

        public boolean hasNext() {
            return currentItem < nodeList.getLength();
        }

        public Object next() {
            return nodeList.item(currentItem++);
        }
    }
}