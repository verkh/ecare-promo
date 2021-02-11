package com.ecare.network;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class DataChangeNotification implements Serializable {
    final private String keyDefault = "ecare.changed";
    private String key;
    private String sender;
}
