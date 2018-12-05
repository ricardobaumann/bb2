package com.github.ricardobaumann.bb2.model;

import com.github.ricardobaumann.bb2.traits.Mappable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSettings extends Auditable implements Mappable<UserSettings> {

    @Id
    @NotNull
    private Long customerId;

    @OneToMany(
            mappedBy = "userSettings",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private List<UserFeature> userFeatures;

    private Date processedAt;

}
