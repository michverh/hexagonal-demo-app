package com.michel.hexagonaldemoapp.archunit;

import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

public class HexagonalArchitectureTest {

    private static final List<String> ALLOWED_PACKAGES_ALL_LAYERS =
            List.of(
                    "com.michel.hexagonaldemoapp.domain..",
                    "java..",
                    "lombok.."
            );

    private static final List<String> ALLOWED_PACKAGES_APPLICATION_LAYER = List.of(
                    "com.michel.hexagonaldemoapp.domain..",
                    "java..",
                    "lombok..",
                    "com.michel.hexagonaldemoapp.application.."
            );


    @Test
    void domainLayerDoesNotDependOnOtherLayers() {
        noClasses()
                .that()
                .resideInAPackage("com.michel.hexagonaldemoapp.domain..")
                .should()
                .dependOnClassesThat()
                .resideOutsideOfPackages(
                        ALLOWED_PACKAGES_ALL_LAYERS.toArray(new String[0])
                )
                .check(new ClassFileImporter()
                        .importPackages("com.michel.hexagonaldemoapp.."));
    }

    @Test
    void applicationLayerOnlyDependsOnItselfAndDomainLayer() {
        noClasses()
                .that()
                .resideInAPackage("com.michel.hexagonaldemoapp.application..")
                .should()
                .dependOnClassesThat()
                .resideOutsideOfPackages(
                        ALLOWED_PACKAGES_APPLICATION_LAYER.toArray(new String[0])
                )
                .check(new ClassFileImporter()
                        .importPackages("com.michel.hexagonaldemoapp.."));
    }
}
