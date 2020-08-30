package com.yamaniha.cep.archtest;


import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.yamaniha.cep.archtest.annotation.ArchTest;

@ArchTest
@DisplayName("padrão de código fonte, interface service")
class ArchServiceTest {

    @Test
    @DisplayName("no pacote service deve ser codificado com o nome terminado com service")
    void shouldHaveSimpleNameEndingWithService(final JavaClasses classes) {
        classes()
                .that().resideInAPackage(".service..")
                .and().resideOutsideOfPackage("..impl")
                .should().haveSimpleNameEndingWith("Service")
                .check(classes);

    }

    @Test
    @DisplayName("no pacote service deve ser codificado somente interface")
    void shouldBeInterfacesService(final JavaClasses classes) {
        classes()
                .that().resideInAPackage(".service..")
                .and().resideOutsideOfPackage("..impl")
                .should().beInterfaces()
                .check(classes);
    }

    @Test
    @DisplayName("no pacote service deve ser acessado somente por listener ou service")
    void shouldOnlyBeAccessedByListenerAndServicePackage(final JavaClasses classes) {
        classes()
                .that().resideInAPackage(".service..")
                .should().onlyBeAccessed().byAnyPackage("..listener..", "..service..");
    }

}
