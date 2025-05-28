package com.example.maxstorage.Almacen;

public class Almacen {
    private int    packageId;
    private double weight;
    private int    registeredBy;
    private String arrivalDate;
    private String estimatedDepartureDate;
    private String qrCode;
    private String status;

    public Almacen() { }

    /** Constructor para leer de la BD (incluye packageId) */
    public Almacen(int packageId,
                    double weight,
                    int registeredBy,
                    String arrivalDate,
                    String estimatedDepartureDate,
                    String qrCode,
                    String status) {
        this.packageId             = packageId;
        this.weight                = weight;
        this.registeredBy          = registeredBy;
        this.arrivalDate           = arrivalDate;
        this.estimatedDepartureDate= estimatedDepartureDate;
        this.qrCode                = qrCode;
        this.status                = status;
    }

    /** Constructor para inserción (no lleva packageId) */
    public Almacen(double weight,
                    int registeredBy,
                    String arrivalDate,
                    String estimatedDepartureDate,
                    String qrCode,
                    String status) {
        this.weight                = weight;
        this.registeredBy          = registeredBy;
        this.arrivalDate           = arrivalDate;
        this.estimatedDepartureDate= estimatedDepartureDate;
        this.qrCode                = qrCode;
        this.status                = status;
    }

    public int getPackageId() {
        return packageId;
    }
    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public double getWeight() {
        return weight;
    }
    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getRegisteredBy() {
        return registeredBy;
    }
    public void setRegisteredBy(int registeredBy) {
        this.registeredBy = registeredBy;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }
    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getEstimatedDepartureDate() {
        return estimatedDepartureDate;
    }
    public void setEstimatedDepartureDate(String estimatedDepartureDate) {
        this.estimatedDepartureDate = estimatedDepartureDate;
    }

    public String getQrCode() {
        return qrCode;
    }
    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
