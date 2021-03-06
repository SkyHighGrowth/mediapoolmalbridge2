
package com.brandmaker.webservices.theme;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.10
 * Generated source version: 2.1
 * 
 */
@WebFault(name = "ThemeWebServiceFault", targetNamespace = "http://brandmaker.com/webservices/Theme/")
public class ThemeWebServiceFault_Exception
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private final ThemeWebServiceFault faultInfo;

    /**
     * 
     * @param faultInfo
     * @param message
     */
    public ThemeWebServiceFault_Exception(String message, ThemeWebServiceFault faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param faultInfo
     * @param cause
     * @param message
     */
    public ThemeWebServiceFault_Exception(String message, ThemeWebServiceFault faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: com.brandmaker.webservices.theme.ThemeWebServiceFault
     */
    public ThemeWebServiceFault getFaultInfo() {
        return faultInfo;
    }

}
