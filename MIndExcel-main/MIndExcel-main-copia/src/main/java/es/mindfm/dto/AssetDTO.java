package es.mindfm.dto;

import es.mindfm.dto.enumeration.AssetStatus;
import es.mindfm.dto.enumeration.AssetType;
import lombok.*;

import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssetDTO implements Serializable, Comparable<AssetDTO> {

    private Long assetId;

    @NotNull(message = "must not be null")
    private Long siteSpaceId;

    private AssetStatus statusType;
    private String code;

    @NotNull(message = "must not be null")
    private String description;

    private String nfc;

    private Long elementsNumber;
    private Double latitude;

    private Double longitude;
    private String additionalCode;

    @NotNull(message = "must not be null")
    private AssetType type;
    private String serviceType;
    private String brand;
    private String model;
    private String serialNumber;
    private Instant commissioningDate;

    //Hierarchy Functional of agreement
    private Long hierarchyFunctionalId;
    private String functionalName;


@Override public int compareTo(@org.jetbrains.annotations.NotNull AssetDTO o) {
    return this.siteSpaceId.hashCode() - o.siteSpaceId.hashCode();
}
}
