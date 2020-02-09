package MediaPoolMalBridge.service.Bridge.excelcreator.excelfilesserver6_2.affiliation;

import MediaPoolMalBridge.persistence.entity.Bridge.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.MAL.MALPropertyEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.persistence.entity.enums.property.MALPropertyStatus;
import MediaPoolMalBridge.service.Bridge.excelcreator.AbstractBridgeUniqueExcelService;
import jxl.Workbook;
import jxl.write.*;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * Service which creates Affiliate Excel files
 */
@Service
public class BridgeCreateAffiliateExcelUniqueThreadService extends AbstractBridgeUniqueExcelService {

    @Override
    protected void run()
    {
        writeToFile( "AFFILIATES_CODE.xls" );
    }

    private void writeToFile(final String fileName) {
        try {
            WritableWorkbook workbook = Workbook.createWorkbook(new File(appConfig.getExcelDir() + fileName));
            WritableSheet sheet = workbook.createSheet("Worksheet", 0);

            WritableCellFormat headerFormat = new WritableCellFormat();
            WritableFont font = new WritableFont(WritableFont.ARIAL, 16, WritableFont.NO_BOLD);
            headerFormat.setFont(font);

            int colIndex = 0;
            int rowIndex = 0;
            for (final String column : AFFILIATE_CODE_COLUMN_NAMES) {
                Label headerLabel = new Label(colIndex++, rowIndex, column, headerFormat);
                sheet.addCell(headerLabel);
            }

            for (final MALPropertyEntity malPropertyEntity : malPropertyRepository.findAllByUpdatedIsAfter( getMidnightBridgeLookInThePast() ) ) {
                ++rowIndex;
                colIndex = 0;
                Label headerLabel = new Label(colIndex++, rowIndex, "AFFILIATES_CODE", headerFormat);
                sheet.addCell(headerLabel);
                headerLabel = new Label(colIndex++, rowIndex, malPropertyEntity.getPropertyId(), headerFormat);
                sheet.addCell(headerLabel);
                headerLabel = new Label(colIndex++, rowIndex, malPropertyEntity.getPropertyId() + " - " + malPropertyEntity.getName(), headerFormat);
                sheet.addCell(headerLabel);
                headerLabel = new Label(colIndex++, rowIndex, "", headerFormat);
                sheet.addCell(headerLabel);
                headerLabel = new Label(colIndex++, rowIndex, MALPropertyStatus.OBSERVED.equals( malPropertyEntity.getMalPropertyStatus() ) ? "1" : "0", headerFormat);
                sheet.addCell(headerLabel);
                headerLabel = new Label(colIndex++, rowIndex, malPropertyEntity.getName(), headerFormat);
                sheet.addCell(headerLabel);
                headerLabel = new Label(colIndex++, rowIndex, malPropertyEntity.getPropertyId(), headerFormat);
                sheet.addCell(headerLabel);
                headerLabel = new Label(colIndex++, rowIndex, malPropertyEntity.getState(), headerFormat);
                sheet.addCell(headerLabel);
                headerLabel = new Label(colIndex++, rowIndex, malPropertyEntity.getAddress2(), headerFormat);
                sheet.addCell(headerLabel);
                headerLabel = new Label(colIndex++, rowIndex, malPropertyEntity.getAddress(), headerFormat);
                sheet.addCell(headerLabel);
                headerLabel = new Label(colIndex++, rowIndex, malPropertyEntity.getZip(), headerFormat);
                sheet.addCell(headerLabel);
                headerLabel = new Label(colIndex++, rowIndex, malPropertyEntity.getCity(), headerFormat);
                sheet.addCell(headerLabel);
                headerLabel = new Label(colIndex++, rowIndex, malPropertyEntity.getCountry(), headerFormat);
                sheet.addCell(headerLabel);
                headerLabel = new Label(colIndex++, rowIndex, malPropertyEntity.getUrl(), headerFormat);
                sheet.addCell(headerLabel);
                headerLabel = new Label(colIndex++, rowIndex, malPropertyEntity.getTelephone(), headerFormat);
                sheet.addCell(headerLabel);
                headerLabel = new Label(colIndex++, rowIndex, malPropertyEntity.getBrand(), headerFormat);
                sheet.addCell(headerLabel);
                headerLabel = new Label(colIndex++, rowIndex, malPropertyEntity.getPropertyId(), headerFormat);
                sheet.addCell(headerLabel);
                //1
                headerLabel = new Label(colIndex++, rowIndex, "", headerFormat);
                sheet.addCell(headerLabel);
                //2
                headerLabel = new Label(colIndex++, rowIndex, "", headerFormat);
                sheet.addCell(headerLabel);
                //3
                headerLabel = new Label(colIndex++, rowIndex, "", headerFormat);
                sheet.addCell(headerLabel);
                //4
                headerLabel = new Label(colIndex++, rowIndex, "", headerFormat);
                sheet.addCell(headerLabel);
                //5
                headerLabel = new Label(colIndex++, rowIndex, "", headerFormat);
                sheet.addCell(headerLabel);
                //6
                headerLabel = new Label(colIndex++, rowIndex, "", headerFormat);
                sheet.addCell(headerLabel);
                //7
                headerLabel = new Label(colIndex++, rowIndex, "", headerFormat);
                sheet.addCell(headerLabel);
                //8
                headerLabel = new Label(colIndex++, rowIndex, "", headerFormat);
                sheet.addCell(headerLabel);
                //9
                headerLabel = new Label(colIndex++, rowIndex, "", headerFormat);
                sheet.addCell(headerLabel);
                //10
                headerLabel = new Label(colIndex++, rowIndex, "", headerFormat);
                sheet.addCell(headerLabel);
                //11
                headerLabel = new Label(colIndex++, rowIndex, "", headerFormat);
                sheet.addCell(headerLabel);
                //12
                headerLabel = new Label(colIndex++, rowIndex, "", headerFormat);
                sheet.addCell(headerLabel);
                //13
                headerLabel = new Label(colIndex++, rowIndex, "", headerFormat);
                sheet.addCell(headerLabel);
                //14
                headerLabel = new Label(colIndex++, rowIndex, "", headerFormat);
                sheet.addCell(headerLabel);
                //15
                headerLabel = new Label(colIndex++, rowIndex, "", headerFormat);
                sheet.addCell(headerLabel);
                //16
                headerLabel = new Label(colIndex++, rowIndex, "", headerFormat);
                sheet.addCell(headerLabel);
                //17
                headerLabel = new Label(colIndex++, rowIndex, "", headerFormat);
                sheet.addCell(headerLabel);
                //18
                headerLabel = new Label(colIndex++, rowIndex, "", headerFormat);
                sheet.addCell(headerLabel);
                //19
                headerLabel = new Label(colIndex++, rowIndex, "", headerFormat);
                sheet.addCell(headerLabel);
                //20
                headerLabel = new Label(colIndex++, rowIndex, "", headerFormat);
                sheet.addCell(headerLabel);
                //21
                headerLabel = new Label(colIndex++, rowIndex, "", headerFormat);
                sheet.addCell(headerLabel);
                //22
                headerLabel = new Label(colIndex++, rowIndex, "", headerFormat);
                sheet.addCell(headerLabel);
                //23
                headerLabel = new Label(colIndex++, rowIndex, "", headerFormat);
                sheet.addCell(headerLabel);
                //24
                headerLabel = new Label(colIndex++, rowIndex, "", headerFormat);
                sheet.addCell(headerLabel);
                //25
                headerLabel = new Label(colIndex++, rowIndex, "", headerFormat);
                sheet.addCell(headerLabel);
                //26
                headerLabel = new Label(colIndex++, rowIndex, "", headerFormat);
                sheet.addCell(headerLabel);
                //27
                headerLabel = new Label(colIndex++, rowIndex, "", headerFormat);
                sheet.addCell(headerLabel);
                //28
                headerLabel = new Label(colIndex, rowIndex, malPropertyEntity.getPropertyId(), headerFormat);
                sheet.addCell(headerLabel);
            }

            workbook.write();
            workbook.close();
        } catch (final Exception e) {
            final String message = String.format("Can not create file [%s]", fileName);
            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), null, message, ReportTo.BM, null, null, null);
            reportsRepository.save( reportsEntity );
            logger.error(message, e);
        }
    }

}
