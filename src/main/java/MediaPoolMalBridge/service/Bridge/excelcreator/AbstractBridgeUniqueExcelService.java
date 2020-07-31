package MediaPoolMalBridge.service.Bridge.excelcreator;

import MediaPoolMalBridge.config.AppConfig;
import MediaPoolMalBridge.persistence.entity.Bridge.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.persistence.repository.Bridge.AssetJsonedValuesRepository;
import MediaPoolMalBridge.persistence.repository.Bridge.AssetRepository;
import MediaPoolMalBridge.persistence.repository.MAL.MALPropertyRepository;
import MediaPoolMalBridge.service.Bridge.AbstractBridgeUniqueThreadService;
import jxl.Workbook;
import jxl.write.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.List;

/**
 * Class which
 */
public abstract class AbstractBridgeUniqueExcelService extends AbstractBridgeUniqueThreadService {

    @Autowired
    protected MALPropertyRepository malPropertyRepository;

    @Autowired
    protected AssetRepository assetRepository;


    @Autowired
    protected AssetJsonedValuesRepository assetJsonedValuesRepository;

    @Autowired
    protected AppConfig appConfig;

    protected static final String[] AFFILIATE_CODE_COLUMN_NAMES = {"CUSTOM_STRUCTURE_ID",
            "NAME", "LABEL", "UPPER_STRUCTURE_VALUE_ID", "STATE", "VALUE_TEXT_01", "VALUE_TEXT_02",
            "VALUE_TEXT_03", "VALUE_TEXT_04", "VALUE_TEXT_05", "VALUE_TEXT_06", "VALUE_TEXT_07",
            "VALUE_TEXT_08", "VALUE_TEXT_09", "VALUE_TEXT_10", "VALUE_TEXT_11", "VALUE_TEXT_12",
            "VALUE_TEXT_13", "VALUE_TEXT_14", "VALUE_TEXT_15", "VALUE_INT_16", "VALUE_INT_17",
            "VALUE_INT_18", "VALUE_INT_19", "VALUE_DATETIME_20", "VALUE_DATETIME_21", "VALUE_FLOAT_22",
            "VALUE_FLOAT_23", "VALUE_MEDIA_24", "VALUE_MEDIA_25", "VALUE_MEDIA_26", "VALUE_MEDIA_27",
            "VALUE_MEDIA_28", "VALUE_MEDIA_29", "VALUE_MEDIA_30", "VALUE_MEDIA_31", "VALUE_MEDIA_32",
            "VALUE_MEDIA_33", "VALUE_RICHTEXT_34", "VALUE_RICHTEXT_35", "VALUE_RICHTEXT_36",
            "VALUE_RICHTEXT_37", "VALUE_RICHTEXT_38", "AFFILIATE"};


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
            final String message = String.format("Can not save excel file [%s]", fileName);
            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), null, message, ReportTo.BM, null, null, null);
            reportsRepository.save( reportsEntity );
            logger.error(message, e);
        }
    }
}
