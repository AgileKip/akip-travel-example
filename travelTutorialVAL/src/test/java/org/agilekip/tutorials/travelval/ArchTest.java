package org.agilekip.tutorials.travelval;

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
            .importPackages("org.agilekip.tutorials.travelval");

        noClasses()
            .that()
            .resideInAnyPackage("org.agilekip.tutorials.travelval.service..")
            .or()
            .resideInAnyPackage("org.agilekip.tutorials.travelval.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..org.agilekip.tutorials.travelval.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
