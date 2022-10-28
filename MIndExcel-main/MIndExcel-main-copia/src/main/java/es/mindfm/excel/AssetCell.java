package es.mindfm.excel;

public enum AssetCell {
    ASSET_ID(0),
    SITE_SPACE_ID(1),
    STATUS(2),
    CODE(3),
    DESCRIPTION(4),
    NFC(5),
    ELEMENTS(6),
    LATITUDE(7),
    LONGITUDE(8),
    ADITIONAL_CODE(9),
    TYPE(10),
    SERVICE_TYPE(11),
    BRAND(12),
    MODEL(13),
    SERIAL_NUMBER(14),
    COMMISSIONING_DATE(15),
    HIERARCHY_FUNCTIONAL_ID(16);


    private final Integer position;


    public Integer getPosition() {
        return this.position;
    }


    AssetCell(Integer position) {
        this.position = position;
    }


}
