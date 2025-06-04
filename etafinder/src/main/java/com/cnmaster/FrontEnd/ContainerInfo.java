package com.cnmaster.FrontEnd;

public class ContainerInfo {
    String container;
    String carKind;
    String destination;
    String eta;
    String lot;
    String row;
    String spot;
    String customsHold;
    String freeDay;

    public ContainerInfo(String container, String carKind, String destination, String eta,
            String lot, String row, String spot, String customsHold, String freeDay) {
        this.container = container;
        this.carKind = carKind;
        this.destination = destination;
        this.eta = eta;
        this.lot = lot;
        this.row = row;
        this.spot = spot;
        this.customsHold = customsHold;
        this.freeDay = freeDay;
    }

    public Object[] toTableRow() {
        return new Object[] {
                container, carKind, destination, eta, lot, row, spot, customsHold, freeDay
        };
    }
}
