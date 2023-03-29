package com.lnu.qa.firstlab.controllers;

import com.lnu.qa.firstlab.models.License;
import com.lnu.qa.firstlab.utils.RandomUtils;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.Set;

@Slf4j
public class LicenseControllerTest {

    private LicenceController licenceController;

    @BeforeClass
    public void setUp() {
        log.info("Current thread: %s".formatted(Thread.currentThread().getName()));
        licenceController = new LicenceController();
    }

    @AfterClass
    public void tearDown() {
        licenceController.retrieveAllLicences().clear();
    }

    @BeforeMethod
    public void afterTest() {
        licenceController.retrieveAllLicences().clear();
    }

    @Test
    public void shouldSaveLicence() {
        //Given
        var licence = new License();
        //When
        License savedLicense = licenceController.saveLicence(licence);
        //Then
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(savedLicense.getId().length() > 0);
        var foundLicencesById = licenceController.retrieveAllLicences().stream().filter(fr -> fr.getId().equals(savedLicense.getId())).toList();
        softAssert.assertEquals(foundLicencesById.size(), 1);
        softAssert.assertEquals(foundLicencesById.get(0), savedLicense);
        softAssert.assertAll();
    }

    @Test
    public void shouldThrowExceptionIfTheLicenseAlreadyHasAnId() {
        //Given
        var licence = new License();
        licence.setId("some_id");
        //Then
        Assert.expectThrows(RuntimeException.class, () -> licenceController.saveLicence(licence));
    }


    @Test
    public void shouldRemoveEmptyListWhenNoLicencesAvailable() {
        Assert.assertEquals(licenceController.retrieveAllLicences().size(), 0);
    }

    @Test
    public void shouldRetrieveAllLicences() {
        //Given
        String name_1 = RandomUtils.generateUUID();
        String name_2 = RandomUtils.generateUUID();
        //When
        License savedLicense_1 = licenceController.saveLicence(new License());
        License savedLicense_2 = licenceController.saveLicence(new License());
        //Then
        List<License> allLicenses = licenceController.retrieveAllLicences();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(allLicenses.size(), 2);
        softAssert.assertEquals(Set.copyOf(allLicenses), Set.of(savedLicense_1, savedLicense_2));
        softAssert.assertAll();
    }

    @Test
    public void shouldGetLicenceById() {
        //Given
        var licence = new License();
        License savedLicense = licenceController.saveLicence(licence);
        //When
        License licenseById = licenceController.getLicenceById(savedLicense.getId());
        //Then
        Assert.assertEquals(licenseById, savedLicense);
    }

    @Test
    public void shouldReturnNullIfLicenceNotFoundById() {
        //When
        License resultLicense = licenceController.getLicenceById("not_valid_id");
        //Then
        Assert.assertNull(resultLicense);
    }

    @Test
    public void shouldRemoveLicenceById() {
        //Given
        String name = RandomUtils.generateUUID();
        var licence = new License();
        License savedLicense = licenceController.saveLicence(licence);
        //When
        License licenseById = licenceController.removeLicenceById(savedLicense.getId());
        //Then
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(licenseById, savedLicense);
        var foundLicencesByName = licenceController.retrieveAllLicences().stream().filter(fr -> fr.getId().equals(name)).findFirst();
        softAssert.assertTrue(foundLicencesByName.isEmpty());
        softAssert.assertAll();
    }

    @Test
    public void shouldReturnNullIfLicenceNotFoundToRemoveById() {
        //When
        License resultLicense = licenceController.removeLicenceById("not_valid_id");
        //Then
        Assert.assertNull(resultLicense);
    }
}