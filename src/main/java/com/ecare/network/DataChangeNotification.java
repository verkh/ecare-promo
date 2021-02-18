package com.ecare.network;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Communication message which notifies about changes in database
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DataChangeNotification implements Serializable {
    final private String keyDefault = "ecare.changed";
    private String key;
    private String sender;

    @Override
    public boolean equals(Object other) {
        if(other == this) return true;

        if(!(other instanceof DataChangeNotification)) return false;

        DataChangeNotification toCompare = (DataChangeNotification) other;
        return this.key.equals(toCompare.getKey()) &&
                this.sender.equals(toCompare.getSender());
    }
}
