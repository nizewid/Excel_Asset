package es.mindfm;

import es.mindfm.dto.AssetDTO;
import es.mindfm.dto.enumeration.AssetStatus;
import es.mindfm.dto.enumeration.AssetType;
import es.mindfm.excel.AssetCell;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.*;


@Slf4j public class MindExcel {

private static final String FILE_NAME = "src/main/resources/Assets.xlsx";
private static final CellType CELL_TYPE_STRING = CellType.STRING;
private static final CellType CELL_TYPE_NUMERIC = CellType.NUMERIC;

public static void main(String[] args) {

	List<AssetDTO> assetDTOList = new ArrayList<>();
	List<String> headers = null;
	try {
		assetDTOList = new ArrayList<AssetDTO>();
		FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
		Workbook workbook = new XSSFWorkbook(excelFile);
		Sheet sheet = workbook.getSheetAt(0);
		workbook.setMissingCellPolicy(Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
		headers = new ArrayList<>();


		for (Row row : sheet) {
			if (row.getRowNum() == 0) {
				for (Cell cell : row) {
					headers.add(String.valueOf(cell));
				}

				//Reading headers
				if (!headers.get(0).equals(AssetCell.ASSET_ID.name()) ||
						!headers.get(1).equals(AssetCell.SITE_SPACE_ID.name()) ||
						!headers.get(2).equals(AssetCell.STATUS.name()) ||
						!headers.get(3).equals(AssetCell.CODE.name()) ||
						!headers.get(4).equals(AssetCell.DESCRIPTION.name()) ||
						!headers.get(5).equals(AssetCell.NFC.name()) ||
						!headers.get(6).equals(AssetCell.ELEMENTS.name()) ||
						!headers.get(7).equals(AssetCell.LATITUDE.name()) ||
						!headers.get(8).equals(AssetCell.LONGITUDE.name()) ||
						!headers.get(9).equals(AssetCell.ADITIONAL_CODE.name()) ||
						!headers.get(10).equals(AssetCell.TYPE.name()) ||
						!headers.get(11).equals(AssetCell.SERVICE_TYPE.name()) ||
						!headers.get(12).equals(AssetCell.BRAND.name()) ||
						!headers.get(13).equals(AssetCell.MODEL.name()) ||
						!headers.get(14).equals(AssetCell.SERIAL_NUMBER.name()) ||
						!headers.get(15).equals(AssetCell.COMMISSIONING_DATE.name()) ||
						!headers.get(16).equals(AssetCell.HIERARCHY_FUNCTIONAL_ID.name())) {
					log.error("Header format file not supported for file " + headers.listIterator().hasNext());
					break;
				}

			}
			if (row.getRowNum() > 0) {

				// if there are no mandatory values we cannot get in the in
				if (!(row.getCell(AssetCell.SITE_SPACE_ID.getPosition()) == null ||
						row.getCell(AssetCell.SITE_SPACE_ID.getPosition()).getNumericCellValue() == Double.valueOf(0).doubleValue())
						&& !(row.getCell(AssetCell.DESCRIPTION.getPosition()) == null || row.getCell(AssetCell.DESCRIPTION.getPosition()).getStringCellValue().isEmpty())
						&& !(row.getCell(AssetCell.TYPE.getPosition()) == null || row.getCell(AssetCell.TYPE.getPosition()).getStringCellValue().isEmpty())) {


					// if there are values we place them in to variables

					if (row.getCell(AssetCell.ASSET_ID.getPosition()) != null ||
							row.getCell(AssetCell.STATUS.getPosition()) != null ||
							row.getCell(AssetCell.CODE.getPosition()) != null ||
							row.getCell(AssetCell.NFC.getPosition()) != null ||
							row.getCell(AssetCell.ELEMENTS.getPosition()) != null ||
							row.getCell(AssetCell.LATITUDE.getPosition()) != null ||
							row.getCell(AssetCell.LONGITUDE.getPosition()) != null ||
							row.getCell(AssetCell.ADITIONAL_CODE.getPosition()) != null ||
							row.getCell(AssetCell.TYPE.getPosition()) != null ||
							row.getCell(AssetCell.SERVICE_TYPE.getPosition()) != null ||
							row.getCell(AssetCell.BRAND.getPosition()) != null ||
							row.getCell(AssetCell.MODEL.getPosition()) != null ||
							row.getCell(AssetCell.SERIAL_NUMBER.getPosition()) != null ||
							row.getCell(AssetCell.HIERARCHY_FUNCTIONAL_ID.getPosition()) != null

					) {
						//Casting the strings fields.

						Long assetId = Double.valueOf(row.getCell(AssetCell.ASSET_ID.getPosition()).getNumericCellValue()).longValue();
						AssetStatus assetStatus = AssetStatus.valueOf(row.getCell(AssetCell.STATUS.getPosition()).getStringCellValue());
						String code = convertCellToString(row.getCell(AssetCell.CODE.getPosition()));
						String description = convertCellToString(row.getCell(AssetCell.DESCRIPTION.getPosition()));
						String nfc = convertCellToString(row.getCell(AssetCell.NFC.getPosition()));
						Long elements = Double.valueOf(row.getCell(AssetCell.ELEMENTS.getPosition()).getNumericCellValue()).longValue();
						Double latitude = row.getCell(AssetCell.LATITUDE.getPosition()).getNumericCellValue();
						Double longitude = row.getCell(AssetCell.LONGITUDE.getPosition()).getNumericCellValue();
						String adCode = convertCellToString(row.getCell(AssetCell.ADITIONAL_CODE.getPosition()));
						String serviceType = convertCellToString(row.getCell(AssetCell.SERVICE_TYPE.getPosition()));
						String brand = convertCellToString(row.getCell(AssetCell.BRAND.getPosition()));
						String model = convertCellToString(row.getCell(AssetCell.MODEL.getPosition()));
						String serialNumber = convertCellToString(row.getCell(AssetCell.SERIAL_NUMBER.getPosition()));
						Long hierarchyFunctionalId = Double.valueOf(row.getCell(AssetCell.HIERARCHY_FUNCTIONAL_ID.getPosition()).getNumericCellValue()).longValue();

						// only will be able to make an instance of ObjASSETtype

						if (row.getCell(AssetCell.TYPE.getPosition()).getStringCellValue().equals("ASSET")) {
							//To obtain some Date Format
							if (row.getCell(AssetCell.COMMISSIONING_DATE.getPosition()) != null) {
								Cell commissioningCellDate = row.getCell(AssetCell.COMMISSIONING_DATE.getPosition());
								Instant instantDate = getInstant(commissioningCellDate);

								try {
									AssetDTO dto = AssetDTO.builder()
											.assetId(assetId)
											.siteSpaceId(Double.valueOf(row.getCell(AssetCell.SITE_SPACE_ID.getPosition()).getNumericCellValue()).longValue()) //Required
											.statusType(assetStatus)
											.code(code)
											.description(description)
											.nfc(nfc)
											.elementsNumber(elements)
											.latitude(latitude)
											.longitude(longitude)
											.additionalCode(adCode)
											.type(AssetType.valueOf(row.getCell(AssetCell.TYPE.getPosition()).getStringCellValue())) // Required
											.serviceType(serviceType)
											.brand(brand)
											.model(model)
											.serialNumber(serialNumber)
											.hierarchyFunctionalId(hierarchyFunctionalId)
											.commissioningDate(instantDate)
											.build();

									assetDTOList.add(dto);

								} catch (NumberFormatException nfe) {
									System.out.println("NumberFormatException: " + nfe.getMessage());
								}

							} else {
								log.error("Possible miss match in Date values");
							}
						}

					}

				} else {
					log.info("Row  {} not processed, because missed data in not null Cells", row.getRowNum());
					break;
				}
			}

		}
		log.info(" ASSETS: " + assetDTOList.size());


	} catch (Exception e) {
		log.error("Error reading and decorating data from Assets ", e.getMessage());
		System.out.println(e.getMessage());

	}

	Collections.sort(assetDTOList);
	System.out.println(arrayNavigate(assetDTOList));


}

public static String arrayNavigate(List<AssetDTO> assetDTOList) {

	StringBuilder cadena = new StringBuilder();

	for (int i = 0; i <= assetDTOList.size() - 1; i++) {

		cadena.append("-------------------------------");
		cadena.append("|AssetID: ").append(assetDTOList.get(i).getAssetId()).append("\n");
		cadena.append("|Space ID: ").append(assetDTOList.get(i).getSiteSpaceId()).append("\n");
		cadena.append("|StatusType: ").append(assetDTOList.get(i).getStatusType()).append("\n");
		cadena.append("|Code:").append(assetDTOList.get(i).getCode()).append("\n");
		cadena.append("|description: ").append(assetDTOList.get(i).getDescription()).append("\n");
		cadena.append("|NFC: ").append(assetDTOList.get(i).getNfc()).append("\n");
		cadena.append("|Elements: ").append(assetDTOList.get(i).getElementsNumber()).append("\n");
		cadena.append("|Latitude: ").append(assetDTOList.get(i).getLatitude()).append("\n");
		cadena.append("|Longitude: ").append(assetDTOList.get(i).getLongitude()).append("\n");
		cadena.append("|Aditional Code: ").append(assetDTOList.get(i).getAdditionalCode()).append("\n");
		cadena.append("|Type: ").append(assetDTOList.get(i).getType()).append("\n");
		cadena.append("|ServiceType: ").append(assetDTOList.get(i).getServiceType()).append("\n");
		cadena.append("|Brand: ").append(assetDTOList.get(i).getBrand()).append("\n");
		cadena.append("|Model: ").append(assetDTOList.get(i).getModel()).append("\n");
		cadena.append("|SerialNumber: ").append(assetDTOList.get(i).getSerialNumber()).append("\n");
		cadena.append("|Hierarchy: ").append(assetDTOList.get(i).getHierarchyFunctionalId()).append("\n");
		cadena.append("|Commissioning Date: ").append(assetDTOList.get(i).getCommissioningDate()).append("\n");
		cadena.append("HASHCODE").append(assetDTOList.get(i).getSiteSpaceId().hashCode()).append("\n");

	}
	return cadena.toString();
}

private static Instant getInstant(Cell cell) {
	Instant result = null;
	if (cell.getCellType() == CELL_TYPE_STRING) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		ZonedDateTime zonedDateTime = null;
		String date = cell.getStringCellValue().substring(0, 19);
		TemporalAccessor temporalAccessor = formatter.parse(date);
		LocalDateTime localDateTime = LocalDateTime.from(temporalAccessor);
		zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
		result = Instant.from(zonedDateTime);
	} else {
		convertCellToString(cell);
	}
	return result;


}

public static String convertCellToString(Cell cell) {
	if (cell.getCellType() == CELL_TYPE_STRING) {
		// tratamos la celda en consecuencia
		return cell.getStringCellValue();
	} else if (cell.getCellType() == CELL_TYPE_NUMERIC) {
		return String.valueOf(cell.getNumericCellValue());
	}
	return "";
}

}


