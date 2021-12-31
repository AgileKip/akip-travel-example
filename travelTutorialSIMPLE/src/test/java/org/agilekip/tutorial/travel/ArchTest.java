package org.agilekip.tutorial.travel;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("org.agilekip.tutorial.travel");

        noClasses()
            .that()
            .resideInAnyPackage("org.agilekip.tutorial.travel.service..")
            .or()
            .resideInAnyPackage("org.agilekip.tutorial.travel.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..org.agilekip.tutorial.travel.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
