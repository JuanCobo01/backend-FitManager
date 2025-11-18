package com.uceva.fitmanager.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppInfoDTO {
    private String version;
    private String buildNumber;
    private String privacyPolicyUrl;
    private String termsUrl;
    private String licenseUrl;
    private String appName;
    private String supportEmail;
}
