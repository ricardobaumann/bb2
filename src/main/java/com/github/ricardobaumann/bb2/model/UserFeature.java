package com.github.ricardobaumann.bb2.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString(exclude = "userSettings")
public class UserFeature extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long adId;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private UserSettings userSettings;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Feature feature;

    public enum Feature {
        eyeCatcher, topOfPage, pageOneAd
    }

}
