
package com.brandmaker.webservices.theme;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.brandmaker.webservices.theme package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetSortedThemeTree_QNAME = new QName("http://brandmaker.com/webservices/Theme/", "getSortedThemeTree");
    private final static QName _CreateSortedTheme_QNAME = new QName("http://brandmaker.com/webservices/Theme/", "createSortedTheme");
    private final static QName _ReceiveThemeInformationByPathResponse_QNAME = new QName("http://brandmaker.com/webservices/Theme/", "receiveThemeInformationByPathResponse");
    private final static QName _ThemeWebServiceFault_QNAME = new QName("http://brandmaker.com/webservices/Theme/", "ThemeWebServiceFault");
    private final static QName _DeleteThemeByIdResponse_QNAME = new QName("http://brandmaker.com/webservices/Theme/", "deleteThemeByIdResponse");
    private final static QName _GetDetailedSortedThemeTreeResponse_QNAME = new QName("http://brandmaker.com/webservices/Theme/", "getDetailedSortedThemeTreeResponse");
    private final static QName _ReceiveThemeInformationByIdResponse_QNAME = new QName("http://brandmaker.com/webservices/Theme/", "receiveThemeInformationByIdResponse");
    private final static QName _UpdateThemeById_QNAME = new QName("http://brandmaker.com/webservices/Theme/", "updateThemeById");
    private final static QName _ReceiveThemeInformationByPath_QNAME = new QName("http://brandmaker.com/webservices/Theme/", "receiveThemeInformationByPath");
    private final static QName _UpdateThemeByIdResponse_QNAME = new QName("http://brandmaker.com/webservices/Theme/", "updateThemeByIdResponse");
    private final static QName _UpdateSortedThemeResponse_QNAME = new QName("http://brandmaker.com/webservices/Theme/", "updateSortedThemeResponse");
    private final static QName _CreateThemeResponse_QNAME = new QName("http://brandmaker.com/webservices/Theme/", "createThemeResponse");
    private final static QName _ReceiveFullThemeTreeInformationResponse_QNAME = new QName("http://brandmaker.com/webservices/Theme/", "receiveFullThemeTreeInformationResponse");
    private final static QName _ReceiveFullThemeTreeInformation_QNAME = new QName("http://brandmaker.com/webservices/Theme/", "receiveFullThemeTreeInformation");
    private final static QName _CreateSortedThemeResponse_QNAME = new QName("http://brandmaker.com/webservices/Theme/", "createSortedThemeResponse");
    private final static QName _GetSortedThemeTreeResponse_QNAME = new QName("http://brandmaker.com/webservices/Theme/", "getSortedThemeTreeResponse");
    private final static QName _GetDetailedSortedThemeTree_QNAME = new QName("http://brandmaker.com/webservices/Theme/", "getDetailedSortedThemeTree");
    private final static QName _CreateTheme_QNAME = new QName("http://brandmaker.com/webservices/Theme/", "createTheme");
    private final static QName _ReceiveFullThemeTreeResponse_QNAME = new QName("http://brandmaker.com/webservices/Theme/", "receiveFullThemeTreeResponse");
    private final static QName _ReceiveFullThemeTree_QNAME = new QName("http://brandmaker.com/webservices/Theme/", "receiveFullThemeTree");
    private final static QName _UpdateSortedTheme_QNAME = new QName("http://brandmaker.com/webservices/Theme/", "updateSortedTheme");
    private final static QName _DeleteThemeById_QNAME = new QName("http://brandmaker.com/webservices/Theme/", "deleteThemeById");
    private final static QName _ReceiveThemeInformationById_QNAME = new QName("http://brandmaker.com/webservices/Theme/", "receiveThemeInformationById");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.brandmaker.webservices.theme
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ThemesResult }
     * 
     */
    public ThemesResult createThemesResult() {
        return new ThemesResult();
    }

    /**
     * Create an instance of {@link Theme }
     * 
     */
    public Theme createTheme() {
        return new Theme();
    }

    /**
     * Create an instance of {@link ReceiveFullThemeTreeInformationResponse }
     * 
     */
    public ReceiveFullThemeTreeInformationResponse createReceiveFullThemeTreeInformationResponse() {
        return new ReceiveFullThemeTreeInformationResponse();
    }

    /**
     * Create an instance of {@link ReceiveFullThemeTreeInformation }
     * 
     */
    public ReceiveFullThemeTreeInformation createReceiveFullThemeTreeInformation() {
        return new ReceiveFullThemeTreeInformation();
    }

    /**
     * Create an instance of {@link UpdateSortedThemeResponse }
     * 
     */
    public UpdateSortedThemeResponse createUpdateSortedThemeResponse() {
        return new UpdateSortedThemeResponse();
    }

    /**
     * Create an instance of {@link CreateThemeResponse }
     * 
     */
    public CreateThemeResponse createCreateThemeResponse() {
        return new CreateThemeResponse();
    }

    /**
     * Create an instance of {@link ReceiveThemeInformationByPath }
     * 
     */
    public ReceiveThemeInformationByPath createReceiveThemeInformationByPath() {
        return new ReceiveThemeInformationByPath();
    }

    /**
     * Create an instance of {@link UpdateThemeByIdResponse }
     * 
     */
    public UpdateThemeByIdResponse createUpdateThemeByIdResponse() {
        return new UpdateThemeByIdResponse();
    }

    /**
     * Create an instance of {@link DeleteThemeByIdResponse }
     * 
     */
    public DeleteThemeByIdResponse createDeleteThemeByIdResponse() {
        return new DeleteThemeByIdResponse();
    }

    /**
     * Create an instance of {@link GetDetailedSortedThemeTreeResponse }
     * 
     */
    public GetDetailedSortedThemeTreeResponse createGetDetailedSortedThemeTreeResponse() {
        return new GetDetailedSortedThemeTreeResponse();
    }

    /**
     * Create an instance of {@link ReceiveThemeInformationByIdResponse }
     * 
     */
    public ReceiveThemeInformationByIdResponse createReceiveThemeInformationByIdResponse() {
        return new ReceiveThemeInformationByIdResponse();
    }

    /**
     * Create an instance of {@link UpdateThemeById }
     * 
     */
    public UpdateThemeById createUpdateThemeById() {
        return new UpdateThemeById();
    }

    /**
     * Create an instance of {@link ThemeWebServiceFault }
     * 
     */
    public ThemeWebServiceFault createThemeWebServiceFault() {
        return new ThemeWebServiceFault();
    }

    /**
     * Create an instance of {@link ReceiveThemeInformationByPathResponse }
     * 
     */
    public ReceiveThemeInformationByPathResponse createReceiveThemeInformationByPathResponse() {
        return new ReceiveThemeInformationByPathResponse();
    }

    /**
     * Create an instance of {@link GetSortedThemeTree }
     * 
     */
    public GetSortedThemeTree createGetSortedThemeTree() {
        return new GetSortedThemeTree();
    }

    /**
     * Create an instance of {@link CreateSortedTheme }
     * 
     */
    public CreateSortedTheme createCreateSortedTheme() {
        return new CreateSortedTheme();
    }

    /**
     * Create an instance of {@link DeleteThemeById }
     * 
     */
    public DeleteThemeById createDeleteThemeById() {
        return new DeleteThemeById();
    }

    /**
     * Create an instance of {@link ReceiveThemeInformationById }
     * 
     */
    public ReceiveThemeInformationById createReceiveThemeInformationById() {
        return new ReceiveThemeInformationById();
    }

    /**
     * Create an instance of {@link ReceiveFullThemeTree }
     * 
     */
    public ReceiveFullThemeTree createReceiveFullThemeTree() {
        return new ReceiveFullThemeTree();
    }

    /**
     * Create an instance of {@link UpdateSortedTheme }
     * 
     */
    public UpdateSortedTheme createUpdateSortedTheme() {
        return new UpdateSortedTheme();
    }

    /**
     * Create an instance of {@link ReceiveFullThemeTreeResponse }
     * 
     */
    public ReceiveFullThemeTreeResponse createReceiveFullThemeTreeResponse() {
        return new ReceiveFullThemeTreeResponse();
    }

    /**
     * Create an instance of {@link GetDetailedSortedThemeTree }
     * 
     */
    public GetDetailedSortedThemeTree createGetDetailedSortedThemeTree() {
        return new GetDetailedSortedThemeTree();
    }

    /**
     * Create an instance of {@link CreateTheme }
     * 
     */
    public CreateTheme createCreateTheme() {
        return new CreateTheme();
    }

    /**
     * Create an instance of {@link GetSortedThemeTreeResponse }
     * 
     */
    public GetSortedThemeTreeResponse createGetSortedThemeTreeResponse() {
        return new GetSortedThemeTreeResponse();
    }

    /**
     * Create an instance of {@link CreateSortedThemeResponse }
     * 
     */
    public CreateSortedThemeResponse createCreateSortedThemeResponse() {
        return new CreateSortedThemeResponse();
    }

    /**
     * Create an instance of {@link ThemeModule }
     * 
     */
    public ThemeModule createThemeModule() {
        return new ThemeModule();
    }

    /**
     * Create an instance of {@link ThemeResult }
     * 
     */
    public ThemeResult createThemeResult() {
        return new ThemeResult();
    }

    /**
     * Create an instance of {@link com.brandmaker.webservices.theme.SubThemesOrder }
     * 
     */
    public com.brandmaker.webservices.theme.SubThemesOrder createSubThemesOrder() {
        return new com.brandmaker.webservices.theme.SubThemesOrder();
    }

    /**
     * Create an instance of {@link ThemePosition }
     * 
     */
    public ThemePosition createThemePosition() {
        return new ThemePosition();
    }

    /**
     * Create an instance of {@link ThemeName }
     * 
     */
    public ThemeName createThemeName() {
        return new ThemeName();
    }

    /**
     * Create an instance of {@link ThemesResult.Themes }
     * 
     */
    public ThemesResult.Themes createThemesResultThemes() {
        return new ThemesResult.Themes();
    }

    /**
     * Create an instance of {@link Theme.Names }
     * 
     */
    public Theme.Names createThemeNames() {
        return new Theme.Names();
    }

    /**
     * Create an instance of {@link Theme.Order }
     * 
     */
    public Theme.Order createThemeOrder() {
        return new Theme.Order();
    }

    /**
     * Create an instance of {@link Theme.SubThemesOrder }
     * 
     */
    public Theme.SubThemesOrder createThemeSubThemesOrder() {
        return new Theme.SubThemesOrder();
    }

    /**
     * Create an instance of {@link Theme.Modules }
     * 
     */
    public Theme.Modules createThemeModules() {
        return new Theme.Modules();
    }

    /**
     * Create an instance of {@link Theme.UserAccess }
     * 
     */
    public Theme.UserAccess createThemeUserAccess() {
        return new Theme.UserAccess();
    }

    /**
     * Create an instance of {@link Theme.OrganizationalAccess }
     * 
     */
    public Theme.OrganizationalAccess createThemeOrganizationalAccess() {
        return new Theme.OrganizationalAccess();
    }

    /**
     * Create an instance of {@link Theme.Themes }
     * 
     */
    public Theme.Themes createThemeThemes() {
        return new Theme.Themes();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSortedThemeTree }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://brandmaker.com/webservices/Theme/", name = "getSortedThemeTree")
    public JAXBElement<GetSortedThemeTree> createGetSortedThemeTree(GetSortedThemeTree value) {
        return new JAXBElement<GetSortedThemeTree>(_GetSortedThemeTree_QNAME, GetSortedThemeTree.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateSortedTheme }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://brandmaker.com/webservices/Theme/", name = "createSortedTheme")
    public JAXBElement<CreateSortedTheme> createCreateSortedTheme(CreateSortedTheme value) {
        return new JAXBElement<CreateSortedTheme>(_CreateSortedTheme_QNAME, CreateSortedTheme.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReceiveThemeInformationByPathResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://brandmaker.com/webservices/Theme/", name = "receiveThemeInformationByPathResponse")
    public JAXBElement<ReceiveThemeInformationByPathResponse> createReceiveThemeInformationByPathResponse(ReceiveThemeInformationByPathResponse value) {
        return new JAXBElement<ReceiveThemeInformationByPathResponse>(_ReceiveThemeInformationByPathResponse_QNAME, ReceiveThemeInformationByPathResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ThemeWebServiceFault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://brandmaker.com/webservices/Theme/", name = "ThemeWebServiceFault")
    public JAXBElement<ThemeWebServiceFault> createThemeWebServiceFault(ThemeWebServiceFault value) {
        return new JAXBElement<ThemeWebServiceFault>(_ThemeWebServiceFault_QNAME, ThemeWebServiceFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteThemeByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://brandmaker.com/webservices/Theme/", name = "deleteThemeByIdResponse")
    public JAXBElement<DeleteThemeByIdResponse> createDeleteThemeByIdResponse(DeleteThemeByIdResponse value) {
        return new JAXBElement<DeleteThemeByIdResponse>(_DeleteThemeByIdResponse_QNAME, DeleteThemeByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDetailedSortedThemeTreeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://brandmaker.com/webservices/Theme/", name = "getDetailedSortedThemeTreeResponse")
    public JAXBElement<GetDetailedSortedThemeTreeResponse> createGetDetailedSortedThemeTreeResponse(GetDetailedSortedThemeTreeResponse value) {
        return new JAXBElement<GetDetailedSortedThemeTreeResponse>(_GetDetailedSortedThemeTreeResponse_QNAME, GetDetailedSortedThemeTreeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReceiveThemeInformationByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://brandmaker.com/webservices/Theme/", name = "receiveThemeInformationByIdResponse")
    public JAXBElement<ReceiveThemeInformationByIdResponse> createReceiveThemeInformationByIdResponse(ReceiveThemeInformationByIdResponse value) {
        return new JAXBElement<ReceiveThemeInformationByIdResponse>(_ReceiveThemeInformationByIdResponse_QNAME, ReceiveThemeInformationByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateThemeById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://brandmaker.com/webservices/Theme/", name = "updateThemeById")
    public JAXBElement<UpdateThemeById> createUpdateThemeById(UpdateThemeById value) {
        return new JAXBElement<UpdateThemeById>(_UpdateThemeById_QNAME, UpdateThemeById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReceiveThemeInformationByPath }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://brandmaker.com/webservices/Theme/", name = "receiveThemeInformationByPath")
    public JAXBElement<ReceiveThemeInformationByPath> createReceiveThemeInformationByPath(ReceiveThemeInformationByPath value) {
        return new JAXBElement<ReceiveThemeInformationByPath>(_ReceiveThemeInformationByPath_QNAME, ReceiveThemeInformationByPath.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateThemeByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://brandmaker.com/webservices/Theme/", name = "updateThemeByIdResponse")
    public JAXBElement<UpdateThemeByIdResponse> createUpdateThemeByIdResponse(UpdateThemeByIdResponse value) {
        return new JAXBElement<UpdateThemeByIdResponse>(_UpdateThemeByIdResponse_QNAME, UpdateThemeByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateSortedThemeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://brandmaker.com/webservices/Theme/", name = "updateSortedThemeResponse")
    public JAXBElement<UpdateSortedThemeResponse> createUpdateSortedThemeResponse(UpdateSortedThemeResponse value) {
        return new JAXBElement<UpdateSortedThemeResponse>(_UpdateSortedThemeResponse_QNAME, UpdateSortedThemeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateThemeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://brandmaker.com/webservices/Theme/", name = "createThemeResponse")
    public JAXBElement<CreateThemeResponse> createCreateThemeResponse(CreateThemeResponse value) {
        return new JAXBElement<CreateThemeResponse>(_CreateThemeResponse_QNAME, CreateThemeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReceiveFullThemeTreeInformationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://brandmaker.com/webservices/Theme/", name = "receiveFullThemeTreeInformationResponse")
    public JAXBElement<ReceiveFullThemeTreeInformationResponse> createReceiveFullThemeTreeInformationResponse(ReceiveFullThemeTreeInformationResponse value) {
        return new JAXBElement<ReceiveFullThemeTreeInformationResponse>(_ReceiveFullThemeTreeInformationResponse_QNAME, ReceiveFullThemeTreeInformationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReceiveFullThemeTreeInformation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://brandmaker.com/webservices/Theme/", name = "receiveFullThemeTreeInformation")
    public JAXBElement<ReceiveFullThemeTreeInformation> createReceiveFullThemeTreeInformation(ReceiveFullThemeTreeInformation value) {
        return new JAXBElement<ReceiveFullThemeTreeInformation>(_ReceiveFullThemeTreeInformation_QNAME, ReceiveFullThemeTreeInformation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateSortedThemeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://brandmaker.com/webservices/Theme/", name = "createSortedThemeResponse")
    public JAXBElement<CreateSortedThemeResponse> createCreateSortedThemeResponse(CreateSortedThemeResponse value) {
        return new JAXBElement<CreateSortedThemeResponse>(_CreateSortedThemeResponse_QNAME, CreateSortedThemeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSortedThemeTreeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://brandmaker.com/webservices/Theme/", name = "getSortedThemeTreeResponse")
    public JAXBElement<GetSortedThemeTreeResponse> createGetSortedThemeTreeResponse(GetSortedThemeTreeResponse value) {
        return new JAXBElement<GetSortedThemeTreeResponse>(_GetSortedThemeTreeResponse_QNAME, GetSortedThemeTreeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDetailedSortedThemeTree }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://brandmaker.com/webservices/Theme/", name = "getDetailedSortedThemeTree")
    public JAXBElement<GetDetailedSortedThemeTree> createGetDetailedSortedThemeTree(GetDetailedSortedThemeTree value) {
        return new JAXBElement<GetDetailedSortedThemeTree>(_GetDetailedSortedThemeTree_QNAME, GetDetailedSortedThemeTree.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateTheme }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://brandmaker.com/webservices/Theme/", name = "createTheme")
    public JAXBElement<CreateTheme> createCreateTheme(CreateTheme value) {
        return new JAXBElement<CreateTheme>(_CreateTheme_QNAME, CreateTheme.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReceiveFullThemeTreeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://brandmaker.com/webservices/Theme/", name = "receiveFullThemeTreeResponse")
    public JAXBElement<ReceiveFullThemeTreeResponse> createReceiveFullThemeTreeResponse(ReceiveFullThemeTreeResponse value) {
        return new JAXBElement<ReceiveFullThemeTreeResponse>(_ReceiveFullThemeTreeResponse_QNAME, ReceiveFullThemeTreeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReceiveFullThemeTree }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://brandmaker.com/webservices/Theme/", name = "receiveFullThemeTree")
    public JAXBElement<ReceiveFullThemeTree> createReceiveFullThemeTree(ReceiveFullThemeTree value) {
        return new JAXBElement<ReceiveFullThemeTree>(_ReceiveFullThemeTree_QNAME, ReceiveFullThemeTree.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateSortedTheme }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://brandmaker.com/webservices/Theme/", name = "updateSortedTheme")
    public JAXBElement<UpdateSortedTheme> createUpdateSortedTheme(UpdateSortedTheme value) {
        return new JAXBElement<UpdateSortedTheme>(_UpdateSortedTheme_QNAME, UpdateSortedTheme.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteThemeById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://brandmaker.com/webservices/Theme/", name = "deleteThemeById")
    public JAXBElement<DeleteThemeById> createDeleteThemeById(DeleteThemeById value) {
        return new JAXBElement<DeleteThemeById>(_DeleteThemeById_QNAME, DeleteThemeById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReceiveThemeInformationById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://brandmaker.com/webservices/Theme/", name = "receiveThemeInformationById")
    public JAXBElement<ReceiveThemeInformationById> createReceiveThemeInformationById(ReceiveThemeInformationById value) {
        return new JAXBElement<ReceiveThemeInformationById>(_ReceiveThemeInformationById_QNAME, ReceiveThemeInformationById.class, null, value);
    }

}
