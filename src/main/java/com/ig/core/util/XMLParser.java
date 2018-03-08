package com.ig.core.util;

import com.ig.web.exception.InvalidResponseException;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

@Component
public class XMLParser {

    public Object unmarshal(String xml, Class clazz) throws InvalidResponseException {

        if (xml == null || clazz == null) {
            throw new InvalidResponseException("Unable to unmarshal xml");
        }

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);

            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            StringReader reader = new StringReader(xml);
            return unmarshaller.unmarshal(reader);

        } catch (JAXBException e) {
            LoggerFactory.getLogger(XMLParser.class).error("{}", e.getStackTrace());
            throw new InvalidResponseException("Unable to unmarshal xml");
        }
    }
}
