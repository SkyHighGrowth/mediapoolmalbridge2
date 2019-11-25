package MediaPoolMalBridge.service.Bridge.excelcreator;

import MediaPoolMalBridge.AppConfig;
import MediaPoolMalBridge.persistence.entity.MAL.MALPropertyEntity;
import MediaPoolMalBridge.persistence.repository.BM.BMAssetRepository;
import MediaPoolMalBridge.persistence.repository.MAL.MALAssetRepository;
import MediaPoolMalBridge.persistence.repository.MAL.MALPropertyRepository;
import MediaPoolMalBridge.service.AbstractUniqueService;
import jxl.Workbook;
import jxl.write.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.List;

public abstract class AbstractBridgeUniqueExcelService extends AbstractUniqueService {

    @Autowired
    protected MALPropertyRepository malPropertyRepository;

    @Autowired
    protected MALAssetRepository malAssetRepository;

    @Autowired
    protected BMAssetRepository bmAssetRepository;

    @Autowired
    private AppConfig appConfig;

    private static final String[] AFFILIATE_CODE_COLUMN_NAMES = {"CUSTOM_STRUCTURE_ID",
            "NAME", "LABEL", "UPPER_STRUCTURE_VALUE_ID", "STATE", "VALUE_TEXT_01", "VALUE_TEXT_02",
            "VALUE_TEXT_03", "VALUE_TEXT_04", "VALUE_TEXT_05", "VALUE_TEXT_06", "VALUE_TEXT_07",
            "VALUE_TEXT_08", "VALUE_TEXT_09", "VALUE_TEXT_10", "VALUE_TEXT_11", "VALUE_TEXT_12",
            "VALUE_TEXT_13", "VALUE_TEXT_14", "VALUE_TEXT_15", "VALUE_INT_16", "VALUE_INT_17",
            "VALUE_INT_18", "VALUE_INT_19", "VALUE_DATETIME_20", "VALUE_DATETIME_21", "VALUE_FLOAT_22",
            "VALUE_FLOAT_23", "VALUE_MEDIA_24", "VALUE_MEDIA_25", "VALUE_MEDIA_26", "VALUE_MEDIA_27",
            "VALUE_MEDIA_28", "VALUE_MEDIA_29", "VALUE_MEDIA_30", "VALUE_MEDIA_31", "VALUE_MEDIA_32",
            "VALUE_MEDIA_33", "VALUE_RICHTEXT_34", "VALUE_RICHTEXT_35", "VALUE_RICHTEXT_36",
            "VALUE_RICHTEXT_37", "VALUE_RICHTEXT_38", "AFFILIATE"};

    protected void writeToFile(final String fileName) {
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

            for (final MALPropertyEntity malPropertyEntity : malPropertyRepository.findAll()) {
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
                headerLabel = new Label(colIndex++, rowIndex, String.valueOf( malPropertyEntity.getStatus() ), headerFormat);
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
                headerLabel = new Label(colIndex, rowIndex, malPropertyEntity.getPropertyId(), headerFormat);
                sheet.addCell(headerLabel);
            }

            workbook.write();
            workbook.close();
        } catch (final Exception e) {
            final String message = String.format("Can not create file [%s]", fileName);
            logger.error(message, e);
        }
    }

    protected void writeToFile( final String fileName, final List<String[]> rows) {
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
            for (final String[] row : rows) {
                ++rowIndex;
                colIndex = 0;
                for( final String cell : row ) {
                    Label headerLabel = new Label(colIndex++, rowIndex, cell, headerFormat);
                    sheet.addCell(headerLabel);
                }
            }
            workbook.write();
            workbook.close();
        } catch (final Exception e) {
            final String message = String.format("Can not create excel file [%s]", fileName);
            logger.error(message, e);
        }
    }
}
