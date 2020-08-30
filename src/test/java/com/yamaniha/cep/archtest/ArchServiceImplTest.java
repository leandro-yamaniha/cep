package com.yamaniha.cep.archtest;


import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.yamaniha.cep.archtest.annotation.ArchTest;

@ArchTest
@DisplayName("padrão de código fonte, implementação da service")
class ArchServiceImplTest {

    @Test
    @DisplayName("deve terminar com ServiceImpl")
    void shouldHaveSimpleNameEndingWithServiceImpl(final JavaClasses classes) {
        classes()
                .that().resideInAPackage("..service.impl..")
                .should().haveSimpleNameEndingWith("ServiceImpl")
                .check(classes);

    }

    @Test
    @DisplayName("deve ter a annotation Service")
    void shouldBeAnnotatedWithService(final JavaClasses classes) {
        classes()
                .that().resideInAPackage("..service.impl..")
                .should().beAnnotatedWith(Service.class)
                .check(classes);

    }

    @Test
    @DisplayName("não pode ser anotado com Component")
    void shouldNotBeAnnotatedWithComponent(final JavaClasses classes) {
        classes()
                .that().resideInAPackage("..service.impl..")
                .should().notBeAnnotatedWith(Component.class)
                .check(classes);

    }
}
