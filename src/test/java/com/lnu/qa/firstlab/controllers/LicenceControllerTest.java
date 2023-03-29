package com.lnu.qa.firstlab.controllers;

import com.lnu.qa.firstlab.models.Licence;
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
public class LicenceControllerTest {

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
        var licence = new Licence();
        //When
        Licence savedLicence = licenceController.saveLicence(licence);
        //Then
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(savedLicence.getId().length() > 0);
        var foundLicencesById = licenceController.retrieveAllLicences().stream().filter(fr -> fr.getId().equals(savedLicence.getId())).toList();
        softAssert.assertEquals(foundLicencesById.size(), 1);
        softAssert.assertEquals(foundLicencesById.get(0), savedLicence);
        softAssert.assertAll();
    }

    @Test
    public void shouldThrowExceptionIfTheLicenseAlreadyHasAnId() {
        //Given
        var licence = new Licence();
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
        Licence savedLicence_1 = licenceController.saveLicence(new Licence());
        Licence savedLicence_2 = licenceController.saveLicence(new Licence());
        //Then
        List<Licence> allLicences = licenceController.retrieveAllLicences();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(allLicences.size(), 2);
        softAssert.assertEquals(Set.copyOf(allLicences), Set.of(savedLicence_1, savedLicence_2));
        softAssert.assertAll();
    }

    @Test
    public void shouldGetLicenceById() {
        //Given
        var licence = new Licence();
        Licence savedLicence = licenceController.saveLicence(licence);
        //When
        Licence licenceById = licenceController.getLicenceById(savedLicence.getId());
        //Then
        Assert.assertEquals(licenceById, savedLicence);
    }

    @Test
    public void shouldReturnNullIfLicenceNotFoundById() {
        //When
        Licence resultLicence = licenceController.getLicenceById("not_valid_id");
        //Then
        Assert.assertNull(resultLicence);
    }

    @Test
    public void shouldRemoveLicenceById() {
        //Given
        String name = RandomUtils.generateUUID();
        var licence = new Licence();
        Licence savedLicence = licenceController.saveLicence(licence);
        //When
        Licence licenceById = licenceController.removeLicenceById(savedLicence.getId());
        //Then
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(licenceById, savedLicence);
        var foundLicencesByName = licenceController.retrieveAllLicences().stream().filter(fr -> fr.getId().equals(name)).findFirst();
        softAssert.assertTrue(foundLicencesByName.isEmpty());
        softAssert.assertAll();
    }

    @Test
    public void shouldReturnNullIfLicenceNotFoundToRemoveById() {
        //When
        Licence resultLicence = licenceController.removeLicenceById("not_valid_id");
        //Then
        Assert.assertNull(resultLicence);
    }
}