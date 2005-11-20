/*
   Copyright 2005 Simon Mieth

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package org.kabeja.svg;

import org.kabeja.dxf.DXFDocument;

import org.kabeja.parser.DXFParseException;
import org.kabeja.parser.DXFParser;
import org.kabeja.parser.ParserBuilder;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.helpers.XMLFilterImpl;

import java.io.IOException;
import java.io.InputStream;


/**
 * <p>
 * This is a implementation of the SAX2 driver. You can parse DXF-Files as they
 * are real SVG-XML-Files.
 * </p>
 * <p>
 * Use:
 * </p>
 *
 * <pre>
 * XMLreader driver = new DXFSVGReader();
 * driver.setContentHander(new MyContentHandler());
 * driver.parse(&quot;/path/to/my.dxf&quot;);
 * </pre>
 *
 * @author <a href="mailto:simon.mieth@gmx.de">Simon Mieth</a>
 *
 */
public class DXF2SVGReader extends XMLFilterImpl {
    public static final String PROPERTY_DXF_ENCODING = "encoding";
    public static final String PROPERTY_PARSER_CONFIGURATION_FILENAME = "config-filename";
    public static final String PROPERTY_PARSER_CONFIGURATION_INPUTSTREAM = "config-inputstream";
    public static final String FEATURE_NAMESPACES = "http://xml.org/sax/features/namespaces";
    public static final String FEATURE_NAMESPACES_PREFIX = "http://xml.org/sax/features/namespace-prefixes";
    public static final String FEATURE_VALIDATION = "http://xml.org/sax/features/validation";
    public static final String FEATURE_STRING_INTERNING = "http://xml.org/sax/features/string-interning";
    public static final String FEATURE_EXTERNAL_GENERAL_ENTITIES = "http://xml.org/sax/features/external-general-entities";
    public static final String FEATURE_EXTERNAL_PARAMETER_ENTITIES = "http://xml.org/sax/features/external-parameter-entities";
    protected DTDHandler dtdhandler;
    protected ContentHandler contenthandler;
    protected EntityResolver resolver;
    protected InputSource source;
    protected ErrorHandler errorhandler;
    protected boolean namespaces = true;
    protected boolean namespacesPrefix = false;
    protected boolean stringInterning = false;
    protected boolean validation = false;
    protected boolean externalGeneralEntities = false;
    protected boolean externalParameterEntities = false;
    protected String encoding = null;
    protected String configURL;
    protected DXFParser parser = null;

    /**
     *
     */
    public DXF2SVGReader() {
    }

    /*
     * (non-Javadoc)
     *
     * @see org.xml.sax.XMLReader#parse(java.lang.String)
     */
    public void parse(String systemId) throws IOException, SAXException {
        initialize();

        try {
            parser.parse(systemId, this.encoding);

            DXFDocument doc = parser.getDocument();

            // the xmlConsumer the next component in
            // the pipeline from parent class
            doc.toSAX(this, null);

            // a little help for the GC
            parser.releaseDXFDocument();
            doc = null;
        } catch (DXFParseException e) {
            errorhandler.error(new SAXParseException(e.getMessage(), null));
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.xml.sax.XMLReader#getFeature(java.lang.String)
     */
    public boolean getFeature(String name)
        throws SAXNotRecognizedException, SAXNotSupportedException {
        // throw new SAXNotSupportedException("no feature: " + name);
        if (name.equals(FEATURE_NAMESPACES)) {
            return this.namespaces;
        } else if (name.equals(FEATURE_NAMESPACES_PREFIX)) {
            return this.namespacesPrefix;
        } else if (name.equals(FEATURE_VALIDATION)) {
            return this.validation;
        } else if (name.equals(FEATURE_EXTERNAL_GENERAL_ENTITIES)) {
            return this.externalGeneralEntities;
        } else if (name.equals(FEATURE_EXTERNAL_PARAMETER_ENTITIES)) {
            return this.externalParameterEntities;
        }

        return false;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.xml.sax.XMLReader#setFeature(java.lang.String, boolean)
     */
    public void setFeature(String name, boolean value)
        throws SAXNotRecognizedException, SAXNotSupportedException {
        if (name.equals(FEATURE_NAMESPACES)) {
            this.namespaces = value;
        } else if (name.equals(FEATURE_NAMESPACES_PREFIX)) {
            this.namespacesPrefix = value;
        } else if (name.equals(FEATURE_VALIDATION)) {
            this.validation = value;
        } else if (name.equals(FEATURE_EXTERNAL_GENERAL_ENTITIES)) {
            this.externalGeneralEntities = value;
        } else if (name.equals(FEATURE_EXTERNAL_PARAMETER_ENTITIES)) {
            this.externalParameterEntities = value;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.xml.sax.XMLReader#getContentHandler()
     */
    public ContentHandler getContentHandler() {
        return super.getContentHandler();
    }

    /*
     * (non-Javadoc)
     *
     * @see org.xml.sax.XMLReader#setContentHandler(org.xml.sax.ContentHandler)
     */
    public void setContentHandler(ContentHandler handler) {
        super.setContentHandler(handler);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.xml.sax.XMLReader#getDTDHandler()
     */
    public DTDHandler getDTDHandler() {
        return this.dtdhandler;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.xml.sax.XMLReader#setDTDHandler(org.xml.sax.DTDHandler)
     */
    public void setDTDHandler(DTDHandler handler) {
        this.dtdhandler = handler;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.xml.sax.XMLReader#getEntityResolver()
     */
    public EntityResolver getEntityResolver() {
        return this.resolver;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.xml.sax.XMLReader#setEntityResolver(org.xml.sax.EntityResolver)
     */
    public void setEntityResolver(EntityResolver resolver) {
        this.resolver = resolver;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.xml.sax.XMLReader#getErrorHandler()
     */
    public ErrorHandler getErrorHandler() {
        return this.errorhandler;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.xml.sax.XMLReader#setErrorHandler(org.xml.sax.ErrorHandler)
     */
    public void setErrorHandler(ErrorHandler handler) {
        this.errorhandler = handler;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.xml.sax.XMLReader#parse(org.xml.sax.InputSource)
     */
    public void parse(InputSource input) throws IOException, SAXException {
        initialize();

        try {
            String en = null;

            if (input.getEncoding() != null) {
                en = input.getEncoding();
            } else {
                en = this.encoding;
            }

            this.parser.parse(input.getByteStream(), en);

            DXFDocument doc = this.parser.getDocument();

            // the xmlConsumer the next component in
            // the pipeline from parent class
            doc.toSAX(this, null);

            // a little help for the GC
            this.parser.releaseDXFDocument();
            doc = null;
        } catch (DXFParseException e) {
            this.errorhandler.error(new SAXParseException(e.getMessage(), null));
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.xml.sax.XMLReader#getProperty(java.lang.String)
     */
    public Object getProperty(String name)
        throws SAXNotRecognizedException, SAXNotSupportedException {
        if (name.equals(PROPERTY_DXF_ENCODING)) {
            return this.encoding;
        } else if (name.equals(PROPERTY_PARSER_CONFIGURATION_INPUTSTREAM)) {
            return null;
        } else if (name.equals(PROPERTY_PARSER_CONFIGURATION_FILENAME)) {
            return this.configURL;
        }

        // throw new SAXNotSupportedException("no feature: " + name);
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.xml.sax.XMLReader#setProperty(java.lang.String,
     *      java.lang.Object)
     */
    public void setProperty(String name, Object value)
        throws SAXNotRecognizedException, SAXNotSupportedException {
        if (name.equals(PROPERTY_DXF_ENCODING)) {
            this.encoding = (String) value;
        } else if (name.equals(PROPERTY_PARSER_CONFIGURATION_INPUTSTREAM)) {
            InputStream in = (InputStream) value;
            parser = ParserBuilder.buildFromXML(in);
        } else if (name.equals(PROPERTY_PARSER_CONFIGURATION_FILENAME)) {
            this.configURL = (String) value;
            parser = ParserBuilder.buildFromXML(this.configURL);
        }
    }

    protected void initialize() {
        if (this.parser == null) {
            this.parser = ParserBuilder.createDefaultParser();
        }

        if (this.encoding == null) {
            this.encoding = DXFParser.DEFAULT_ENCODING;
        }

        if (!namespacesPrefix && !namespaces) {
            namespacesPrefix = true;
        }

        if (validation) {
            externalGeneralEntities = true;
            externalParameterEntities = true;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.xml.sax.ContentHandler#startElement(java.lang.String,
     *      java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    public void startElement(String uri, String localName, String qName,
        Attributes atts) throws SAXException {
        if (this.namespacesPrefix) {
            AttributesImpl attributes = new AttributesImpl(atts);

            if (atts.getIndex("xmlns") == -1) {
                attributes.addAttribute("", "xmlns", "xmlns", "CDATA",
                    SVGConstants.SVG_NAMESPACE);
            }

            if (atts.getIndex("xmlns:xlink") == -1) {
                attributes.addAttribute("", "", "xmlns:xlink", "CDATA",
                    SVGConstants.XLINK_NAMESPACE);
            }

            super.startElement(uri, localName, qName, attributes);
        } else {
            super.startElement(uri, localName, qName, atts);
        }
    }
}