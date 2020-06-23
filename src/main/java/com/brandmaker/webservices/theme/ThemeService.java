
package com.brandmaker.webservices.theme;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.4-b01-
 * Generated source version: 2.1
 * 
 */
@WebServiceClient(name = "ThemeService", targetNamespace = "http://brandmaker.com/webservices/Theme/", wsdlLocation = "classpath:wsdl/Theme.wsdl")
public class ThemeService
    extends Service
{

    private final static URL THEMESERVICE_WSDL_LOCATION;
    private final static Logger logger = LoggerFactory.getLogger(com.brandmaker.webservices.theme.ThemeService.class.getName());

    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = com.brandmaker.webservices.theme.ThemeService.class.getResource(".");
            url = new URL(baseUrl, "classpath:wsdl/Theme.wsdl");
        } catch (MalformedURLException e) {
            logger.warn("Failed to create URL for the wsdl Location: 'classpath:wsdl/Theme.wsdl', retrying as a local file");
            logger.warn(e.getMessage());
        }
        THEMESERVICE_WSDL_LOCATION = url;
    }

    public ThemeService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ThemeService() {
        super(THEMESERVICE_WSDL_LOCATION, new QName("http://brandmaker.com/webservices/Theme/", "ThemeService"));
    }

    /**
     * 
     * @return
     *     returns ThemeWebServicePort
     */
    @WebEndpoint(name = "ThemePort")
    public ThemeWebServicePort getThemePort() {
        return super.getPort(new QName("http://brandmaker.com/webservices/Theme/", "ThemePort"), ThemeWebServicePort.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ThemeWebServicePort
     */
    @WebEndpoint(name = "ThemePort")
    public ThemeWebServicePort getThemePort(WebServiceFeature... features) {
        return super.getPort(new QName("http://brandmaker.com/webservices/Theme/", "ThemePort"), ThemeWebServicePort.class, features);
    }

}