package com.yamaniha.cep.archtest;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Configuration;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.yamaniha.cep.archtest.annotation.ArchTest;

@ArchTest
@DisplayName("padrão de código fonte, config")
class ArchConfigTest {

    @Test
    @DisplayName("deve terminar com Configuration")
    void shouldHaveSimpleNameEndingWithConfiguration(final JavaClasses classes) {
        classes()
                .that().resideInAPackage(".config")
                .should().haveSimpleNameEndingWith("Configuration")
                .check(classes);
    }

    @Test
    @DisplayName("deve ter annotation Configuration")
    void shouldBeAnnotatedWithConfiguration(final JavaClasses classes) {
        classes()
                .that().resideInAPackage(".config")
                .should().beAnnotatedWith(Configuration.class)
                .check(classes);
    }
}
