package org.example.middlewares;

import com.sun.net.httpserver.HttpExchange;
import com.sun.xml.ws.developer.JAXWSProperties;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.namespace.QName;
import javax.xml.soap.*;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.sql.*;
import java.util.Date;
import java.util.Set;

public class Logger implements SOAPHandler<SOAPMessageContext> {
    private void recordToDatabase(SOAPMessageContext smc) throws SOAPException {
        boolean isResponse = (boolean) smc.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        HttpExchange httpExchange = (HttpExchange) smc.get(JAXWSProperties.HTTP_EXCHANGE);

        if (!isResponse) {
            String token = httpExchange.getRequestHeaders().getFirst("Authorization");
            System.out.println(token);
            try {
                SOAPPart soapPart = smc.getMessage().getSOAPPart();
                SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
                SOAPBody soapBody = soapEnvelope.getBody();

                Node operation = soapBody.getChildNodes().item(1);
                String content = String.format("%s", operation.getLocalName());

                NodeList parameters = operation.getChildNodes();
                for (int i = 1; i < parameters.getLength(); i += 2) {
                    content = String.format("%s %s(%s)", content, parameters.item(i).getLocalName(), parameters.item(i).getTextContent());
                }
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/mydatabase", "root", "mysecretpassword");
                PreparedStatement statement = connection.prepareStatement("INSERT INTO log (description, IP, endpoint) VALUES(?, ?, ?)");
                statement.setString(1, content);
                statement.setString(2, httpExchange.getRemoteAddress().getHostString());
                statement.setString(3, httpExchange.getRequestURI().getPath());
                statement.execute();
                connection.close();
            }catch (SQLException error){
                System.out.println(error.getMessage());
            }
        }
    }

    public Set<QName> getHeaders() {
        return null;
    }
    public boolean handleMessage(SOAPMessageContext smc) {
        try {
            recordToDatabase(smc);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean handleFault(SOAPMessageContext smc) {
        try {
            recordToDatabase(smc);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void close(MessageContext messageContext) {
        // DO NOTHING
    }
}
