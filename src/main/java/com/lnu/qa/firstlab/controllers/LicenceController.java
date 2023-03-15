package com.lnu.qa.firstlab.controllers;

import com.lnu.qa.firstlab.models.Licence;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Log4j
@AllArgsConstructor
public class LicenceController {
    private final List<Licence> licences = new ArrayList<>();

    public List<Licence> retrieveAllLicences() {
        return licences;
    }

    public Licence saveLicence(Licence licence) {
        log.info("Attempt to save the Licence: %s".formatted(licence));
        if (licence.getId() != null) {
            throw new RuntimeException("Entity already exists with id: %s".formatted(licence.getId()));
        }
        licence.setId(UUID.randomUUID().toString());
        licences.add(licence);
        log.info("Licence is saved with id: %s".formatted(licence.getId()));
        return licence;
    }

    public Licence getLicenceById(String id) {
        log.info("Attempt to take the Licence by id: %s".formatted(id));
        Licence resultLicence = licences.stream().filter(licence -> licence.getId().equals(id)).findFirst().orElse(null);
        if (resultLicence != null) {
            log.info("Licence was found: %s".formatted(resultLicence));
        } else {
            log.info("Licence wasn't found");
        }
        return resultLicence;
    }

    public Licence removeLicenceById(String id) {
        log.info("Attempt to remove the Licence by id: %s".formatted(id));
        Licence resultLicence = licences.stream().filter(licence -> licence.getId().equals(id)).findFirst().orElse(null);
        if (resultLicence != null) {
            licences.remove(resultLicence);
            log.info("Licence was removed: %s".formatted(resultLicence));
        } else {
            log.info("Licence wasn't found");
        }
        return resultLicence;
    }

}
