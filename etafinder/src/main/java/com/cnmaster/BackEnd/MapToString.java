package com.cnmaster.BackEnd;

import java.util.List;
import java.util.Map;

public class MapToString {
    public static void mapToString(Map<String, Object> resultMap) {
        // 取出 ThirdPartyIntermodalShipment
        Map<String, Object> shipment = (Map<String, Object>) resultMap.get("ThirdPartyIntermodalShipment");
        List<Map<String, Object>> equipmentList = (List<Map<String, Object>>) shipment.get("Equipment");

        // 遍历所有 container 信息
        for (Map<String, Object> container : equipmentList) {
            String id = (String) container.get("EquipmentId");
            String waybillStatus = (String) container.get("WaybillStatus");
            String loadEmpty = (String) container.get("LoadEmpty");
            String carKind = (String) container.get("CarKind");
            String carKindDescription = (String) container.get("CarKindDescription");

            Map<String, Object> destination = (Map<String, Object>) container.get("Destination");
            String destStation = destination != null ? (String) destination.get("Station") : null;
            String destProvState = destination != null ? (String) destination.get("ProvState") : null;
            String destCountryCode = destination != null ? (String) destination.get("CountryCode") : null;

            Map<String, Object> event = (Map<String, Object>) container.get("Event");
            String eventDesc = event != null ? (String) event.get("Description") : null;
            String eventTime = event != null ? (String) event.get("Time") : null;
            Map<String, Object> eventLocation = event != null ? (Map<String, Object>) event.get("Location") : null;
            String eventStation = eventLocation != null ? (String) eventLocation.get("Station") : null;
            String eventProvState = eventLocation != null ? (String) eventLocation.get("ProvState") : null;

            Map<String, Object> lotLocation = (Map<String, Object>) container.get("LotLocation");
            String lot = lotLocation != null ? (String) lotLocation.get("Lot") : null;
            String row = lotLocation != null ? (String) lotLocation.get("Row") : null;
            String spot = lotLocation != null ? (String) lotLocation.get("Spot") : null;

            List<Map<String, Object>> weights = (List<Map<String, Object>>) container.get("Weight");

            Map<String, Object> customsHold = (Map<String, Object>) container.get("CustomsHold");
            String customsDesc = customsHold != null ? (String) customsHold.get("Description") : null;
            String customsTimestamp = customsHold != null ? (String) customsHold.get("Timestamp") : null;

            List<Map<String, Object>> customsHolds = (List<Map<String, Object>>) container.get("CustomsHolds");

            Map<String, Object> storageCharge = (Map<String, Object>) container.get("StorageCharge");
            String lastFreeDay = storageCharge != null ? (String) storageCharge.get("LastFreeDay") : null;

            System.out.println("=== Container: " + id + " ===");
            System.out.println("Waybill Status: " + waybillStatus);
            System.out.println("Load/Empty: " + loadEmpty);
            System.out.println("Car Kind: " + carKind + " (" + carKindDescription + ")");
            System.out.println("Destination: " + destStation + ", " + destProvState + ", " + destCountryCode);

            System.out.println("Event: " + eventDesc + " @ " + eventTime);
            System.out.println("Event Location: " + eventStation + ", " + eventProvState);

            System.out.println("Lot Location: Lot=" + lot + ", Row=" + row + ", Spot=" + spot);

            System.out.println("Weights:");
            if (weights != null) {
                for (Map<String, Object> w : weights) {
                    System.out.println("  " + w.get("Type") + ": " + w.get("Weight") + " " + w.get("Unit"));
                }
            }

            System.out.println("Customs Hold: " + customsDesc + " @ " + customsTimestamp);

            System.out.println("Customs Holds List:");
            if (customsHolds != null) {
                for (Map<String, Object> ch : customsHolds) {
                    System.out.println("  Description: " + ch.get("Description") + ", Timestamp: " + ch.get("Timestamp")
                            + ", Direction: " + ch.get("Direction"));
                }
            }

            System.out.println("Storage Charge Last Free Day: " + lastFreeDay);

            System.out.println();
        }
    }
}
