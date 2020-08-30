package com.yamaniha.cep.archtest;


import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.yamaniha.cep.archtest.annotation.ArchTest;

@ArchTest
@DisplayName("padrão de código fonte, utils")
class ArchUtilsTest {

    @Test
    @DisplayName("deve ter somente atributos com final")
    void shouldhaveOnlyFinalFields(final JavaClasses classes) {
        classes()
                .that().resideInAPackage(".utils..")
                .should().haveOnlyFinalFields()
                .check(classes);
    }

    @Test
    @DisplayName("deve ter somente construtor privado")
    void shouldhaveOnlyPrivateConstructors(final JavaClasses classes) {
        classes()
                .that().resideInAPackage(".utils..")
                .should().haveOnlyPrivateConstructors()
                .check(classes);
    }

    @Test
    @DisplayName("deve terminar com o nome utils")
    void shouldHaveSimpleNameEndingWithUtils(final JavaClasses classes) {
        classes()
                .that().resideInAPackage(".utils..")
                .should().haveSimpleNameEndingWith("Utils")
                .check(classes);
    }
}
