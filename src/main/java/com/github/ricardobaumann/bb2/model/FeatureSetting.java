package com.github.ricardobaumann.bb2.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeatureSetting extends Auditable {

    @Id
    private String id;
    @NotNull
    private Long customerId;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Feature feature;
    @NotNull
    private Integer amount;

    private Date processedAt;

    @PrePersist
    void prePersist() {
        id = id == null ? UUID.randomUUID().toString() : id;
    }

    public enum Feature {
        EYE_CATCHER, TOP, TIC
    }
}
