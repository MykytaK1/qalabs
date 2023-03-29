package com.lnu.qa.firstlab.controllers;

import com.lnu.qa.firstlab.exceptions.LicenseException;
import com.lnu.qa.firstlab.models.License;
import com.lnu.qa.firstlab.utils.RandomUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class LicenceController {
    private final List<License> licenses = new ArrayList<>();

    public List<License> retrieveAllLicences() {
        return licenses;
    }

    public License saveLicence(License license) {
        log.info("Attempt to save the Licence: %s".formatted(license));
        if (license.getId() != null) {
            throw new LicenseException("Entity already exists with id: %s".formatted(license.getId()));
        }
        license.setId(RandomUtils.generateUUID());
        licenses.add(license);
        log.info("Licence is saved with id: %s".formatted(license.getId()));
        return license;
    }

    public License getLicenceById(String id) {
        log.info("Attempt to take the Licence by id: %s".formatted(id));
        License resultLicense = licenses.stream().filter(license -> license.getId().equals(id)).findFirst().orElse(null);
        if (resultLicense != null) {
            log.info("Licence was found: %s".formatted(resultLicense));
        } else {
            log.info("Licence wasn't found");
        }
        return resultLicense;
    }

    public License removeLicenceById(String id) {
        log.info("Attempt to remove the Licence by id: %s".formatted(id));
        License resultLicense = licenses.stream().filter(license -> license.getId().equals(id)).findFirst().orElse(null);
        if (resultLicense != null) {
            licenses.remove(resultLicense);
            log.info("Licence was removed: %s".formatted(resultLicense));
        } else {
            log.info("Licence wasn't found");
        }
        return resultLicense;
    }

}
