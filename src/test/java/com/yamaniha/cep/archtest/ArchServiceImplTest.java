package com.yamaniha.cep.archtest;


import com.tngtech.archunit.core.domain.JavaClasses;
import com.yamaniha.cep.archtest.annotation.ArchTest;

import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@ArchTest
class ArchServiceImplTest {

    @Test
    void shouldHaveSimpleNameEndingWithServiceImpl(final JavaClasses classes) {
        classes()
                .that().resideInAPackage("..service.impl..")
                .should().haveSimpleNameEndingWith("ServiceImpl")
                .check(classes);

    }

    @Test
    void shouldBeAnnotatedWithService(final JavaClasses classes) {
        classes()
                .that().resideInAPackage("..service.impl..")
                .should().beAnnotatedWith(Service.class)
                .check(classes);

    }

    @Test
    void shouldNotBeAnnotatedWithComponent(final JavaClasses classes) {
        classes()
                .that().resideInAPackage("..service.impl..")
                .should().notBeAnnotatedWith(Component.class)
                .check(classes);

    }
}
