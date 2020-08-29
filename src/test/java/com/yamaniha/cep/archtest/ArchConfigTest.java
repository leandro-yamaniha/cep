package com.yamaniha.cep.archtest;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.yamaniha.cep.archtest.annotation.ArchTest;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Configuration;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@ArchTest
class ArchConfigTest {

    @Test
    void shouldHaveSimpleNameEndingWithConfiguration(final JavaClasses classes) {
        classes()
                .that().resideInAPackage(".config")
                .should().haveSimpleNameEndingWith("Configuration")
                .check(classes);
    }

    @Test
    void shouldBeAnnotatedWithConfiguration(final JavaClasses classes) {
        classes()
                .that().resideInAPackage(".config")
                .should().beAnnotatedWith(Configuration.class)
                .check(classes);
    }
}
