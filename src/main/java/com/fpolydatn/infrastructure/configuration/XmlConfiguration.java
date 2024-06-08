package com.fpolydatn.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;

/**
 * @author PhongTT35
 * @description N/A
 **/
@Configuration
public class XmlConfiguration {

    @Bean
    public DocumentBuilderFactory documentBuilderFactory() throws Exception {
        return secureDocumentBuilderFactory();
    }

    @Bean
    public TransformerFactory transformerFactory() {
        return secureTransformerFactory();
    }

    public TransformerFactory secureTransformerFactory() {
        TransformerFactory factory = TransformerFactory.newInstance();
        try {
            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        } catch (TransformerFactoryConfigurationError | TransformerConfigurationException e) {
            throw new RuntimeException("Failed to configure TransformerFactory for secure processing", e);
        }
        return factory;
    }

    public DocumentBuilderFactory secureDocumentBuilderFactory() throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        dbf.setFeature("http://xml.org/sax/features/external-general-entities", false);
        dbf.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        dbf.setNamespaceAware(true);
        dbf.setExpandEntityReferences(false);
        return dbf;
    }
}
