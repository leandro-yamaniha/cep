package com.yamaniha.cep.archtest;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.yamaniha.cep.archtest.annotation.ArchTest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@ArchTest
class ArchPropertiesTest {

    @Test
    void shouldHaveSimpleNameEndingWithProperties(final JavaClasses classes) {
        classes()
                .that().resideInAPackage("..properties..")
                .and().areNotMemberClasses()
                .should().haveSimpleNameEndingWith("Properties")
                .check(classes);
    }

    @Test
    void shouldBeAnnotatedWithConstructorBinding(final JavaClasses classes) {
        classes()
                .that().resideInAPackage("..properties..")
                .and().areNotMemberClasses()
                .should().beAnnotatedWith(ConstructorBinding.class)
                .check(classes);
    }

    @Test
    void shouldBeAnnotatedWithConfigurationProperties(final JavaClasses classes) {
        classes()
                .that().resideInAPackage("..properties..")
                .and().areNotMemberClasses()
                .should().beAnnotatedWith(ConfigurationProperties.class)
                .check(classes);
    }
}
