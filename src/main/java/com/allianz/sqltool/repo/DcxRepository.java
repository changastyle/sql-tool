package com.allianz.sqltool.repo;

import com.allianz.sqltool.model.DCXEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
public interface DcxRepository extends JpaRepository<DCXEntity, Long>
{
    List<DCXEntity> findByPartnerAndMarketAndSalesChannelAndLobAndFlowType(@NotBlank @NotNull String partner,
                                                                           @NotBlank @NotNull String market,
                                                                           @NotBlank @NotNull String salesChannel,
                                                                           @NotBlank @NotNull String lob,
                                                                           @NotBlank @NotNull String flowType);
}
