
package com.brandmaker.webservices.mediapool;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.brandmaker.webservices.mediapool package. 
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

    private final static QName _UploadMediaVersionResponse_QNAME = new QName("http://brandmaker.com/webservices/MediaPool/", "uploadMediaVersionResponse");
    private final static QName _UpdateExternalDownloadLinkResponse_QNAME = new QName("http://brandmaker.com/webservices/MediaPool/", "updateExternalDownloadLinkResponse");
    private final static QName _WebPublishedMediaResponse_QNAME = new QName("http://brandmaker.com/webservices/MediaPool/", "webPublishedMediaResponse");
    private final static QName _FindExternalDownloadLinkByMediaGuidResponse_QNAME = new QName("http://brandmaker.com/webservices/MediaPool/", "findExternalDownloadLinkByMediaGuidResponse");
    private final static QName _UploadMediaAsStream_QNAME = new QName("http://brandmaker.com/webservices/MediaPool/", "uploadMediaAsStream");
    private final static QName _GetMediaDetails_QNAME = new QName("http://brandmaker.com/webservices/MediaPool/", "getMediaDetails");
    private final static QName _UploadMediaVersionAsStreamResponse_QNAME = new QName("http://brandmaker.com/webservices/MediaPool/", "uploadMediaVersionAsStreamResponse");
    private final static QName _AddExternalDownloadLink_QNAME = new QName("http://brandmaker.com/webservices/MediaPool/", "addExternalDownloadLink");
    private final static QName _SetOfficial_QNAME = new QName("http://brandmaker.com/webservices/MediaPool/", "setOfficial");
    private final static QName _FindExternalDownloadLinkById_QNAME = new QName("http://brandmaker.com/webservices/MediaPool/", "findExternalDownloadLinkById");
    private final static QName _UploadMedia_QNAME = new QName("http://brandmaker.com/webservices/MediaPool/", "uploadMedia");
    private final static QName _DeleteExternalDownloadLinkByMediaGuid_QNAME = new QName("http://brandmaker.com/webservices/MediaPool/", "deleteExternalDownloadLinkByMediaGuid");
    private final static QName _GetMediaGuidByHashResponse_QNAME = new QName("http://brandmaker.com/webservices/MediaPool/", "getMediaGuidByHashResponse");
    private final static QName _UploadMetaDataResponse_QNAME = new QName("http://brandmaker.com/webservices/MediaPool/", "uploadMetaDataResponse");
    private final static QName _UploadMediaResponse_QNAME = new QName("http://brandmaker.com/webservices/MediaPool/", "uploadMediaResponse");
    private final static QName _SetOfficialResponse_QNAME = new QName("http://brandmaker.com/webservices/MediaPool/", "setOfficialResponse");
    private final static QName _AddExternalDownloadLinkResponse_QNAME = new QName("http://brandmaker.com/webservices/MediaPool/", "addExternalDownloadLinkResponse");
    private final static QName _DeleteMediaResponse_QNAME = new QName("http://brandmaker.com/webservices/MediaPool/", "deleteMediaResponse");
    private final static QName _WebPublishedMedia_QNAME = new QName("http://brandmaker.com/webservices/MediaPool/", "webPublishedMedia");
    private final static QName _FindExternalDownloadLinkByIdResponse_QNAME = new QName("http://brandmaker.com/webservices/MediaPool/", "findExternalDownloadLinkByIdResponse");
    private final static QName _FindExternalDownloadLinkByMediaGuid_QNAME = new QName("http://brandmaker.com/webservices/MediaPool/", "findExternalDownloadLinkByMediaGuid");
    private final static QName _DeleteExternalDownloadLinkByMediaGuidResponse_QNAME = new QName("http://brandmaker.com/webservices/MediaPool/", "deleteExternalDownloadLinkByMediaGuidResponse");
    private final static QName _UploadMediaAsStreamResponse_QNAME = new QName("http://brandmaker.com/webservices/MediaPool/", "uploadMediaAsStreamResponse");
    private final static QName _DeleteMedia_QNAME = new QName("http://brandmaker.com/webservices/MediaPool/", "deleteMedia");
    private final static QName _UploadMetaData_QNAME = new QName("http://brandmaker.com/webservices/MediaPool/", "uploadMetaData");
    private final static QName _DeleteExternalDownloadLinkByIdResponse_QNAME = new QName("http://brandmaker.com/webservices/MediaPool/", "deleteExternalDownloadLinkByIdResponse");
    private final static QName _UpdateExternalDownloadLink_QNAME = new QName("http://brandmaker.com/webservices/MediaPool/", "updateExternalDownloadLink");
    private final static QName _GetMediaGuidByHash_QNAME = new QName("http://brandmaker.com/webservices/MediaPool/", "getMediaGuidByHash");
    private final static QName _UploadMediaVersionAsStream_QNAME = new QName("http://brandmaker.com/webservices/MediaPool/", "uploadMediaVersionAsStream");
    private final static QName _GetMediaDetailsResponse_QNAME = new QName("http://brandmaker.com/webservices/MediaPool/", "getMediaDetailsResponse");
    private final static QName _DeleteExternalDownloadLinkById_QNAME = new QName("http://brandmaker.com/webservices/MediaPool/", "deleteExternalDownloadLinkById");
    private final static QName _UploadMediaVersion_QNAME = new QName("http://brandmaker.com/webservices/MediaPool/", "uploadMediaVersion");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.brandmaker.webservices.mediapool
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DeleteExternalDownloadLinkByMediaGuid }
     * 
     */
    public DeleteExternalDownloadLinkByMediaGuid createDeleteExternalDownloadLinkByMediaGuid() {
        return new DeleteExternalDownloadLinkByMediaGuid();
    }

    /**
     * Create an instance of {@link WebPublishedMedia }
     * 
     */
    public WebPublishedMedia createWebPublishedMedia() {
        return new WebPublishedMedia();
    }

    /**
     * Create an instance of {@link DeleteMediaResponse }
     * 
     */
    public DeleteMediaResponse createDeleteMediaResponse() {
        return new DeleteMediaResponse();
    }

    /**
     * Create an instance of {@link GetMediaDetailsResponse }
     * 
     */
    public GetMediaDetailsResponse createGetMediaDetailsResponse() {
        return new GetMediaDetailsResponse();
    }

    /**
     * Create an instance of {@link ThemeDto }
     * 
     */
    public ThemeDto createThemeDto() {
        return new ThemeDto();
    }

    /**
     * Create an instance of {@link DeleteMedia }
     * 
     */
    public DeleteMedia createDeleteMedia() {
        return new DeleteMedia();
    }

    /**
     * Create an instance of {@link FindExternalDownloadLinkByMediaGuid }
     * 
     */
    public FindExternalDownloadLinkByMediaGuid createFindExternalDownloadLinkByMediaGuid() {
        return new FindExternalDownloadLinkByMediaGuid();
    }

    /**
     * Create an instance of {@link UploadMediaVersionAsStream }
     * 
     */
    public UploadMediaVersionAsStream createUploadMediaVersionAsStream() {
        return new UploadMediaVersionAsStream();
    }

    /**
     * Create an instance of {@link ExternalDownloadLink }
     * 
     */
    public ExternalDownloadLink createExternalDownloadLink() {
        return new ExternalDownloadLink();
    }

    /**
     * Create an instance of {@link UploadMetaDataResponse }
     * 
     */
    public UploadMetaDataResponse createUploadMetaDataResponse() {
        return new UploadMetaDataResponse();
    }

    /**
     * Create an instance of {@link GetMediaDetailsResult }
     * 
     */
    public GetMediaDetailsResult createGetMediaDetailsResult() {
        return new GetMediaDetailsResult();
    }

    /**
     * Create an instance of {@link LanguageItem }
     * 
     */
    public LanguageItem createLanguageItem() {
        return new LanguageItem();
    }

    /**
     * Create an instance of {@link UploadMediaAsStreamResponse }
     * 
     */
    public UploadMediaAsStreamResponse createUploadMediaAsStreamResponse() {
        return new UploadMediaAsStreamResponse();
    }

    /**
     * Create an instance of {@link UploadMediaVersion }
     * 
     */
    public UploadMediaVersion createUploadMediaVersion() {
        return new UploadMediaVersion();
    }

    /**
     * Create an instance of {@link SetOfficialArgument }
     * 
     */
    public SetOfficialArgument createSetOfficialArgument() {
        return new SetOfficialArgument();
    }

    /**
     * Create an instance of {@link UploadMediaArgument }
     * 
     */
    public UploadMediaArgument createUploadMediaArgument() {
        return new UploadMediaArgument();
    }

    /**
     * Create an instance of {@link UploadMediaVersionArgument }
     * 
     */
    public UploadMediaVersionArgument createUploadMediaVersionArgument() {
        return new UploadMediaVersionArgument();
    }

    /**
     * Create an instance of {@link Code }
     * 
     */
    public Code createCode() {
        return new Code();
    }

    /**
     * Create an instance of {@link UploadMediaVersionResponse }
     * 
     */
    public UploadMediaVersionResponse createUploadMediaVersionResponse() {
        return new UploadMediaVersionResponse();
    }

    /**
     * Create an instance of {@link UploadMediaVersionResult }
     * 
     */
    public UploadMediaVersionResult createUploadMediaVersionResult() {
        return new UploadMediaVersionResult();
    }

    /**
     * Create an instance of {@link DeleteExternalDownloadLinkById }
     * 
     */
    public DeleteExternalDownloadLinkById createDeleteExternalDownloadLinkById() {
        return new DeleteExternalDownloadLinkById();
    }

    /**
     * Create an instance of {@link DeleteMediaResult }
     * 
     */
    public DeleteMediaResult createDeleteMediaResult() {
        return new DeleteMediaResult();
    }

    /**
     * Create an instance of {@link AddExternalDownloadLinkResponse }
     * 
     */
    public AddExternalDownloadLinkResponse createAddExternalDownloadLinkResponse() {
        return new AddExternalDownloadLinkResponse();
    }

    /**
     * Create an instance of {@link StructuredKeywords }
     * 
     */
    public StructuredKeywords createStructuredKeywords() {
        return new StructuredKeywords();
    }

    /**
     * Create an instance of {@link UploadMediaResponse }
     * 
     */
    public UploadMediaResponse createUploadMediaResponse() {
        return new UploadMediaResponse();
    }

    /**
     * Create an instance of {@link UploadMetaData }
     * 
     */
    public UploadMetaData createUploadMetaData() {
        return new UploadMetaData();
    }

    /**
     * Create an instance of {@link Error }
     * 
     */
    public Error createError() {
        return new Error();
    }

    /**
     * Create an instance of {@link SetOfficialResponse }
     * 
     */
    public SetOfficialResponse createSetOfficialResponse() {
        return new SetOfficialResponse();
    }

    /**
     * Create an instance of {@link WebPublishedMediaArgument }
     * 
     */
    public WebPublishedMediaArgument createWebPublishedMediaArgument() {
        return new WebPublishedMediaArgument();
    }

    /**
     * Create an instance of {@link SetOfficialResult }
     * 
     */
    public SetOfficialResult createSetOfficialResult() {
        return new SetOfficialResult();
    }

    /**
     * Create an instance of {@link WebPublishedMediaResult }
     * 
     */
    public WebPublishedMediaResult createWebPublishedMediaResult() {
        return new WebPublishedMediaResult();
    }

    /**
     * Create an instance of {@link FindExternalDownloadLinkByMediaGuidResponse }
     * 
     */
    public FindExternalDownloadLinkByMediaGuidResponse createFindExternalDownloadLinkByMediaGuidResponse() {
        return new FindExternalDownloadLinkByMediaGuidResponse();
    }

    /**
     * Create an instance of {@link FindExternalDownloadLinkByIdResponse }
     * 
     */
    public FindExternalDownloadLinkByIdResponse createFindExternalDownloadLinkByIdResponse() {
        return new FindExternalDownloadLinkByIdResponse();
    }

    /**
     * Create an instance of {@link DeleteExternalDownloadLinkByIdResponse }
     * 
     */
    public DeleteExternalDownloadLinkByIdResponse createDeleteExternalDownloadLinkByIdResponse() {
        return new DeleteExternalDownloadLinkByIdResponse();
    }

    /**
     * Create an instance of {@link DeleteMediaArgument }
     * 
     */
    public DeleteMediaArgument createDeleteMediaArgument() {
        return new DeleteMediaArgument();
    }

    /**
     * Create an instance of {@link AddExternalDownloadLink }
     * 
     */
    public AddExternalDownloadLink createAddExternalDownloadLink() {
        return new AddExternalDownloadLink();
    }

    /**
     * Create an instance of {@link UploadMetadataArgument }
     * 
     */
    public UploadMetadataArgument createUploadMetadataArgument() {
        return new UploadMetadataArgument();
    }

    /**
     * Create an instance of {@link WebPublishedMediaResponse }
     * 
     */
    public WebPublishedMediaResponse createWebPublishedMediaResponse() {
        return new WebPublishedMediaResponse();
    }

    /**
     * Create an instance of {@link GetMediaDetailsArgument }
     * 
     */
    public GetMediaDetailsArgument createGetMediaDetailsArgument() {
        return new GetMediaDetailsArgument();
    }

    /**
     * Create an instance of {@link Warning }
     * 
     */
    public Warning createWarning() {
        return new Warning();
    }

    /**
     * Create an instance of {@link UploadMediaVersionAsStreamResponse }
     * 
     */
    public UploadMediaVersionAsStreamResponse createUploadMediaVersionAsStreamResponse() {
        return new UploadMediaVersionAsStreamResponse();
    }

    /**
     * Create an instance of {@link Isin }
     * 
     */
    public Isin createIsin() {
        return new Isin();
    }

    /**
     * Create an instance of {@link NameAndIdItem }
     * 
     */
    public NameAndIdItem createNameAndIdItem() {
        return new NameAndIdItem();
    }

    /**
     * Create an instance of {@link UploadMediaResult }
     * 
     */
    public UploadMediaResult createUploadMediaResult() {
        return new UploadMediaResult();
    }

    /**
     * Create an instance of {@link UpdateExternalDownloadLinkResponse }
     * 
     */
    public UpdateExternalDownloadLinkResponse createUpdateExternalDownloadLinkResponse() {
        return new UpdateExternalDownloadLinkResponse();
    }

    /**
     * Create an instance of {@link UpdateExternalDownloadLink }
     * 
     */
    public UpdateExternalDownloadLink createUpdateExternalDownloadLink() {
        return new UpdateExternalDownloadLink();
    }

    /**
     * Create an instance of {@link UploadMediaAsStream }
     * 
     */
    public UploadMediaAsStream createUploadMediaAsStream() {
        return new UploadMediaAsStream();
    }

    /**
     * Create an instance of {@link UploadMetadataResult }
     * 
     */
    public UploadMetadataResult createUploadMetadataResult() {
        return new UploadMetadataResult();
    }

    /**
     * Create an instance of {@link GetMediaGuidByHash }
     * 
     */
    public GetMediaGuidByHash createGetMediaGuidByHash() {
        return new GetMediaGuidByHash();
    }

    /**
     * Create an instance of {@link GetMediaGuidByHashResponse }
     * 
     */
    public GetMediaGuidByHashResponse createGetMediaGuidByHashResponse() {
        return new GetMediaGuidByHashResponse();
    }

    /**
     * Create an instance of {@link UploadMedia }
     * 
     */
    public UploadMedia createUploadMedia() {
        return new UploadMedia();
    }

    /**
     * Create an instance of {@link SetOfficial }
     * 
     */
    public SetOfficial createSetOfficial() {
        return new SetOfficial();
    }

    /**
     * Create an instance of {@link FindExternalDownloadLinkById }
     * 
     */
    public FindExternalDownloadLinkById createFindExternalDownloadLinkById() {
        return new FindExternalDownloadLinkById();
    }

    /**
     * Create an instance of {@link DeleteExternalDownloadLinkByMediaGuidResponse }
     * 
     */
    public DeleteExternalDownloadLinkByMediaGuidResponse createDeleteExternalDownloadLinkByMediaGuidResponse() {
        return new DeleteExternalDownloadLinkByMediaGuidResponse();
    }

    /**
     * Create an instance of {@link GetMediaDetails }
     * 
     */
    public GetMediaDetails createGetMediaDetails() {
        return new GetMediaDetails();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UploadMediaVersionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://brandmaker.com/webservices/MediaPool/", name = "uploadMediaVersionResponse")
    public JAXBElement<UploadMediaVersionResponse> createUploadMediaVersionResponse(UploadMediaVersionResponse value) {
        return new JAXBElement<UploadMediaVersionResponse>(_UploadMediaVersionResponse_QNAME, UploadMediaVersionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateExternalDownloadLinkResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://brandmaker.com/webservices/MediaPool/", name = "updateExternalDownloadLinkResponse")
    public JAXBElement<UpdateExternalDownloadLinkResponse> createUpdateExternalDownloadLinkResponse(UpdateExternalDownloadLinkResponse value) {
        return new JAXBElement<UpdateExternalDownloadLinkResponse>(_UpdateExternalDownloadLinkResponse_QNAME, UpdateExternalDownloadLinkResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WebPublishedMediaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://brandmaker.com/webservices/MediaPool/", name = "webPublishedMediaResponse")
    public JAXBElement<WebPublishedMediaResponse> createWebPublishedMediaResponse(WebPublishedMediaResponse value) {
        return new JAXBElement<WebPublishedMediaResponse>(_WebPublishedMediaResponse_QNAME, WebPublishedMediaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindExternalDownloadLinkByMediaGuidResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://brandmaker.com/webservices/MediaPool/", name = "findExternalDownloadLinkByMediaGuidResponse")
    public JAXBElement<FindExternalDownloadLinkByMediaGuidResponse> createFindExternalDownloadLinkByMediaGuidResponse(FindExternalDownloadLinkByMediaGuidResponse value) {
        return new JAXBElement<FindExternalDownloadLinkByMediaGuidResponse>(_FindExternalDownloadLinkByMediaGuidResponse_QNAME, FindExternalDownloadLinkByMediaGuidResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UploadMediaAsStream }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://brandmaker.com/webservices/MediaPool/", name = "uploadMediaAsStream")
    public JAXBElement<UploadMediaAsStream> createUploadMediaAsStream(UploadMediaAsStream value) {
        return new JAXBElement<UploadMediaAsStream>(_UploadMediaAsStream_QNAME, UploadMediaAsStream.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMediaDetails }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://brandmaker.com/webservices/MediaPool/", name = "getMediaDetails")
    public JAXBElement<GetMediaDetails> createGetMediaDetails(GetMediaDetails value) {
        return new JAXBElement<GetMediaDetails>(_GetMediaDetails_QNAME, GetMediaDetails.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UploadMediaVersionAsStreamResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://brandmaker.com/webservices/MediaPool/", name = "uploadMediaVersionAsStreamResponse")
    public JAXBElement<UploadMediaVersionAsStreamResponse> createUploadMediaVersionAsStreamResponse(UploadMediaVersionAsStreamResponse value) {
        return new JAXBElement<UploadMediaVersionAsStreamResponse>(_UploadMediaVersionAsStreamResponse_QNAME, UploadMediaVersionAsStreamResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddExternalDownloadLink }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://brandmaker.com/webservices/MediaPool/", name = "addExternalDownloadLink")
    public JAXBElement<AddExternalDownloadLink> createAddExternalDownloadLink(AddExternalDownloadLink value) {
        return new JAXBElement<AddExternalDownloadLink>(_AddExternalDownloadLink_QNAME, AddExternalDownloadLink.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetOfficial }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://brandmaker.com/webservices/MediaPool/", name = "setOfficial")
    public JAXBElement<SetOfficial> createSetOfficial(SetOfficial value) {
        return new JAXBElement<SetOfficial>(_SetOfficial_QNAME, SetOfficial.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindExternalDownloadLinkById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://brandmaker.com/webservices/MediaPool/", name = "findExternalDownloadLinkById")
    public JAXBElement<FindExternalDownloadLinkById> createFindExternalDownloadLinkById(FindExternalDownloadLinkById value) {
        return new JAXBElement<FindExternalDownloadLinkById>(_FindExternalDownloadLinkById_QNAME, FindExternalDownloadLinkById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UploadMedia }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://brandmaker.com/webservices/MediaPool/", name = "uploadMedia")
    public JAXBElement<UploadMedia> createUploadMedia(UploadMedia value) {
        return new JAXBElement<UploadMedia>(_UploadMedia_QNAME, UploadMedia.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteExternalDownloadLinkByMediaGuid }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://brandmaker.com/webservices/MediaPool/", name = "deleteExternalDownloadLinkByMediaGuid")
    public JAXBElement<DeleteExternalDownloadLinkByMediaGuid> createDeleteExternalDownloadLinkByMediaGuid(DeleteExternalDownloadLinkByMediaGuid value) {
        return new JAXBElement<DeleteExternalDownloadLinkByMediaGuid>(_DeleteExternalDownloadLinkByMediaGuid_QNAME, DeleteExternalDownloadLinkByMediaGuid.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMediaGuidByHashResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://brandmaker.com/webservices/MediaPool/", name = "getMediaGuidByHashResponse")
    public JAXBElement<GetMediaGuidByHashResponse> createGetMediaGuidByHashResponse(GetMediaGuidByHashResponse value) {
        return new JAXBElement<GetMediaGuidByHashResponse>(_GetMediaGuidByHashResponse_QNAME, GetMediaGuidByHashResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UploadMetaDataResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://brandmaker.com/webservices/MediaPool/", name = "uploadMetaDataResponse")
    public JAXBElement<UploadMetaDataResponse> createUploadMetaDataResponse(UploadMetaDataResponse value) {
        return new JAXBElement<UploadMetaDataResponse>(_UploadMetaDataResponse_QNAME, UploadMetaDataResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UploadMediaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://brandmaker.com/webservices/MediaPool/", name = "uploadMediaResponse")
    public JAXBElement<UploadMediaResponse> createUploadMediaResponse(UploadMediaResponse value) {
        return new JAXBElement<UploadMediaResponse>(_UploadMediaResponse_QNAME, UploadMediaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetOfficialResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://brandmaker.com/webservices/MediaPool/", name = "setOfficialResponse")
    public JAXBElement<SetOfficialResponse> createSetOfficialResponse(SetOfficialResponse value) {
        return new JAXBElement<SetOfficialResponse>(_SetOfficialResponse_QNAME, SetOfficialResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddExternalDownloadLinkResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://brandmaker.com/webservices/MediaPool/", name = "addExternalDownloadLinkResponse")
    public JAXBElement<AddExternalDownloadLinkResponse> createAddExternalDownloadLinkResponse(AddExternalDownloadLinkResponse value) {
        return new JAXBElement<AddExternalDownloadLinkResponse>(_AddExternalDownloadLinkResponse_QNAME, AddExternalDownloadLinkResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteMediaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://brandmaker.com/webservices/MediaPool/", name = "deleteMediaResponse")
    public JAXBElement<DeleteMediaResponse> createDeleteMediaResponse(DeleteMediaResponse value) {
        return new JAXBElement<DeleteMediaResponse>(_DeleteMediaResponse_QNAME, DeleteMediaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WebPublishedMedia }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://brandmaker.com/webservices/MediaPool/", name = "webPublishedMedia")
    public JAXBElement<WebPublishedMedia> createWebPublishedMedia(WebPublishedMedia value) {
        return new JAXBElement<WebPublishedMedia>(_WebPublishedMedia_QNAME, WebPublishedMedia.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindExternalDownloadLinkByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://brandmaker.com/webservices/MediaPool/", name = "findExternalDownloadLinkByIdResponse")
    public JAXBElement<FindExternalDownloadLinkByIdResponse> createFindExternalDownloadLinkByIdResponse(FindExternalDownloadLinkByIdResponse value) {
        return new JAXBElement<FindExternalDownloadLinkByIdResponse>(_FindExternalDownloadLinkByIdResponse_QNAME, FindExternalDownloadLinkByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindExternalDownloadLinkByMediaGuid }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://brandmaker.com/webservices/MediaPool/", name = "findExternalDownloadLinkByMediaGuid")
    public JAXBElement<FindExternalDownloadLinkByMediaGuid> createFindExternalDownloadLinkByMediaGuid(FindExternalDownloadLinkByMediaGuid value) {
        return new JAXBElement<FindExternalDownloadLinkByMediaGuid>(_FindExternalDownloadLinkByMediaGuid_QNAME, FindExternalDownloadLinkByMediaGuid.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteExternalDownloadLinkByMediaGuidResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://brandmaker.com/webservices/MediaPool/", name = "deleteExternalDownloadLinkByMediaGuidResponse")
    public JAXBElement<DeleteExternalDownloadLinkByMediaGuidResponse> createDeleteExternalDownloadLinkByMediaGuidResponse(DeleteExternalDownloadLinkByMediaGuidResponse value) {
        return new JAXBElement<DeleteExternalDownloadLinkByMediaGuidResponse>(_DeleteExternalDownloadLinkByMediaGuidResponse_QNAME, DeleteExternalDownloadLinkByMediaGuidResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UploadMediaAsStreamResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://brandmaker.com/webservices/MediaPool/", name = "uploadMediaAsStreamResponse")
    public JAXBElement<UploadMediaAsStreamResponse> createUploadMediaAsStreamResponse(UploadMediaAsStreamResponse value) {
        return new JAXBElement<UploadMediaAsStreamResponse>(_UploadMediaAsStreamResponse_QNAME, UploadMediaAsStreamResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteMedia }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://brandmaker.com/webservices/MediaPool/", name = "deleteMedia")
    public JAXBElement<DeleteMedia> createDeleteMedia(DeleteMedia value) {
        return new JAXBElement<DeleteMedia>(_DeleteMedia_QNAME, DeleteMedia.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UploadMetaData }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://brandmaker.com/webservices/MediaPool/", name = "uploadMetaData")
    public JAXBElement<UploadMetaData> createUploadMetaData(UploadMetaData value) {
        return new JAXBElement<UploadMetaData>(_UploadMetaData_QNAME, UploadMetaData.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteExternalDownloadLinkByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://brandmaker.com/webservices/MediaPool/", name = "deleteExternalDownloadLinkByIdResponse")
    public JAXBElement<DeleteExternalDownloadLinkByIdResponse> createDeleteExternalDownloadLinkByIdResponse(DeleteExternalDownloadLinkByIdResponse value) {
        return new JAXBElement<DeleteExternalDownloadLinkByIdResponse>(_DeleteExternalDownloadLinkByIdResponse_QNAME, DeleteExternalDownloadLinkByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateExternalDownloadLink }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://brandmaker.com/webservices/MediaPool/", name = "updateExternalDownloadLink")
    public JAXBElement<UpdateExternalDownloadLink> createUpdateExternalDownloadLink(UpdateExternalDownloadLink value) {
        return new JAXBElement<UpdateExternalDownloadLink>(_UpdateExternalDownloadLink_QNAME, UpdateExternalDownloadLink.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMediaGuidByHash }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://brandmaker.com/webservices/MediaPool/", name = "getMediaGuidByHash")
    public JAXBElement<GetMediaGuidByHash> createGetMediaGuidByHash(GetMediaGuidByHash value) {
        return new JAXBElement<GetMediaGuidByHash>(_GetMediaGuidByHash_QNAME, GetMediaGuidByHash.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UploadMediaVersionAsStream }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://brandmaker.com/webservices/MediaPool/", name = "uploadMediaVersionAsStream")
    public JAXBElement<UploadMediaVersionAsStream> createUploadMediaVersionAsStream(UploadMediaVersionAsStream value) {
        return new JAXBElement<UploadMediaVersionAsStream>(_UploadMediaVersionAsStream_QNAME, UploadMediaVersionAsStream.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMediaDetailsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://brandmaker.com/webservices/MediaPool/", name = "getMediaDetailsResponse")
    public JAXBElement<GetMediaDetailsResponse> createGetMediaDetailsResponse(GetMediaDetailsResponse value) {
        return new JAXBElement<GetMediaDetailsResponse>(_GetMediaDetailsResponse_QNAME, GetMediaDetailsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteExternalDownloadLinkById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://brandmaker.com/webservices/MediaPool/", name = "deleteExternalDownloadLinkById")
    public JAXBElement<DeleteExternalDownloadLinkById> createDeleteExternalDownloadLinkById(DeleteExternalDownloadLinkById value) {
        return new JAXBElement<DeleteExternalDownloadLinkById>(_DeleteExternalDownloadLinkById_QNAME, DeleteExternalDownloadLinkById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UploadMediaVersion }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://brandmaker.com/webservices/MediaPool/", name = "uploadMediaVersion")
    public JAXBElement<UploadMediaVersion> createUploadMediaVersion(UploadMediaVersion value) {
        return new JAXBElement<UploadMediaVersion>(_UploadMediaVersion_QNAME, UploadMediaVersion.class, null, value);
    }

}
