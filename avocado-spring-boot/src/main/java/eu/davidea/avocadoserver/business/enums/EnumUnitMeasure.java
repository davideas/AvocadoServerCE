package eu.davidea.avocadoserver.business.enums;

public enum  EnumUnitMeasure {
    KM((short) 6371),
    MILES((short) 3959);

    private short unit;

    EnumUnitMeasure(short unit) {
        this.unit = unit;
    }

    public short getUnit() {
        return unit;
    }

}