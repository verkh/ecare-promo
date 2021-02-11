package com.ecare.plans;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder=true)
@JsonDeserialize(using = PlanDeserializer.class)
public class Plan {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder(toBuilder=true)
    public static class Option {
        private Long id;
        private String name;
        private Double price;
        private String description;
        private Double turnOnPrice;
        private boolean undisablable;
    }

    private Long id;
    private String name;
    private Double price;
    @Singular private List<Option> options;
}
