package com.yamaniha.cep.archtest;


import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.lang.ArchRule;
import com.yamaniha.cep.archtest.annotation.ArchTest;

import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@ArchTest
class ArchServiceTest {

    @Test
    void shouldHaveSimpleNameEndingWithService(final JavaClasses classes) {
        classes()
                .that().resideInAPackage(".service..")
                .and().resideOutsideOfPackage("..impl")
                .should().haveSimpleNameEndingWith("Service")
                .check(classes);

    }

    @Test
    void shouldBeInterfacesService(final JavaClasses classes) {
        classes()
                .that().resideInAPackage(".service..")
                .and().resideOutsideOfPackage("..impl")
                .should().beInterfaces()
                .check(classes);
    }

    @Test
    void shouldOnlyBeAccessedByListenerAndServicePackage(final JavaClasses classes) {
        classes()
                .that().resideInAPackage(".service..")
                .should().onlyBeAccessed().byAnyPackage("..listener..", "..service..");
    }

}
