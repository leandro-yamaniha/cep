package com.yamaniha.cep.archtest;


import com.tngtech.archunit.core.domain.JavaClasses;
import com.yamaniha.cep.archtest.annotation.ArchTest;

import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@ArchTest
class ArchUtilsTest {

    @Test
    void shouldhaveOnlyFinalFields(final JavaClasses classes) {
        classes()
                .that().resideInAPackage(".utils..")
                .should().haveOnlyFinalFields()
                .check(classes);
    }

    @Test
    void shouldhaveOnlyPrivateConstructors(final JavaClasses classes) {
        classes()
                .that().resideInAPackage(".utils..")
                .should().haveOnlyPrivateConstructors()
                .check(classes);
    }

    @Test
    void shouldHaveSimpleNameEndingWithUtils(final JavaClasses classes) {
        classes()
                .that().resideInAPackage(".utils..")
                .should().haveSimpleNameEndingWith("Utils")
                .check(classes);
    }
}
