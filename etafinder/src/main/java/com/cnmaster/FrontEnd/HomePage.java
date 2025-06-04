package com.cnmaster.FrontEnd;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.cnmaster.BackEnd.EstimatedTime;
import com.cnmaster.BackEnd.EtaToMap;
import com.cnmaster.BackEnd.OAuthTokenFetcher;

public class HomePage {

    public static void Mainpage() {
        JFrame frame = new JFrame("输入验证");
        frame.setSize(600, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel inputLabel = new JLabel("请输入 Container #");

        JTextArea inputArea = new JTextArea();
        inputArea.setLineWrap(true);
        inputArea.setWrapStyleWord(true);
        inputArea.setPreferredSize(new Dimension(300, 900));
        
        JButton startButton = new JButton("元神启动");
        startButton.setPreferredSize(new Dimension(120, 30));

        startButton.addActionListener((ActionEvent e) -> {
            String rawInput = inputArea.getText().trim();

            List<String> containers = Arrays.stream(rawInput.split("\\R+"))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toList());

            if (containers.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "请输入至少一个 Container 编号", "输入错误", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                String token = OAuthTokenFetcher.getAccessToken();

                List<ContainerInfo> resultList = new ArrayList<>();

                for (String container : containers) {
                    String etaJson = EstimatedTime.getETA(token, container);

                    Map<String, Object> data = EtaToMap.parseJsonToMap(etaJson);

                    // 先取出 "ThirdPartyIntermodalShipment" 对应的 Map
                    Map<String, Object> thirdPartyShipment = (Map<String, Object>) data
                            .get("ThirdPartyIntermodalShipment");
                    if (thirdPartyShipment == null) {
                        System.out.println("No ThirdPartyIntermodalShipment data for container " + container);
                        continue;
                    }

                    // 取 Equipment 列表
                    List<Map<String, Object>> equipmentList = (List<Map<String, Object>>) thirdPartyShipment
                            .get("Equipment");
                    if (equipmentList == null || equipmentList.isEmpty()) {
                        System.out.println("No Equipment data for container " + container);
                        continue;
                    }

                    // 取第一个设备的详细信息
                    Map<String, Object> equipment = equipmentList.get(0);

                    String containerId = (String) equipment.getOrDefault("EquipmentId", container);
                    String carkind = (String) equipment.getOrDefault("CarKind", "");
                    Map<String, String> carKindMap = new HashMap<>();
                    carKindMap.put("KC4", "40HQ");
                    carKindMap.put("KC1", "20GP");
                    carKindMap.put("KC2", "40GP");
                    carKindMap.put("KR4", "40RH");
                    String carKind = carKindMap.getOrDefault(carkind, carkind);

                    // Destination 是个 Map，取 Station 字段
                    Map<String, Object> destinationMap = (Map<String, Object>) equipment.get("Destination");
                    String destination = destinationMap != null ? (String) destinationMap.getOrDefault("Station", "")
                            : "";

                    // ETA 是个 Map，取 Time 字段
                    Map<String, Object> etaMap = (Map<String, Object>) equipment.get("ETA");
                    String eta = etaMap != null ? (String) etaMap.getOrDefault("Time", "") : "";

                    // 你代码里 lot,row,spot 等字段，JSON 里没有找到，如果你确定有，可以继续按类似方式取
                    // 这里用空字符串代替
                    String lot = "";
                    String row = "";
                    String spot = "";

                    // CustomsHold 是个 Map，取 Description 字段
                    Map<String, Object> customsHoldMap = (Map<String, Object>) equipment.get("CustomsHold");
                    String customsHold = customsHoldMap != null
                            ? (String) customsHoldMap.getOrDefault("Description", "")
                            : "";

                    // StorageCharge 里可能有 LastFreeDay
                    Map<String, Object> storageChargeMap = (Map<String, Object>) equipment.get("StorageCharge");
                    String freeDay = storageChargeMap != null
                            ? String.valueOf(storageChargeMap.getOrDefault("LastFreeDay", ""))
                            : "";

                    resultList.add(new ContainerInfo(containerId, carKind, destination, eta, lot, row, spot,
                            customsHold, freeDay));
                }
                ShowResultTable.showResultTable(resultList);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "出错了，请稍后再试", "错误", JOptionPane.ERROR_MESSAGE);
            }
        });

        frame.add(inputLabel);
        frame.add(new JScrollPane(inputArea)); // 加滚动条，防止长输入框溢出
        frame.add(new JLabel()); // 占位
        frame.add(startButton);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
