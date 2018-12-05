package com.github.ricardobaumann.bb2.repo;

import com.github.ricardobaumann.bb2.model.UserSettings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Date;
import java.util.List;

@RepositoryRestResource
public interface FeatureSettingRepo extends PagingAndSortingRepository<UserSettings, Long> {

    List<UserSettings> findByCustomerId(@Param("customerId") Long customerId);

    Page<UserSettings> findByProcessedAtBeforeOrderByCustomerId(Date processedAt, Pageable pageable);

}
