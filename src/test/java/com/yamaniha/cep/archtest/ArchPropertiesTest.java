package com.yamaniha.cep.archtest;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.yamaniha.cep.archtest.annotation.ArchTest;

@ArchTest
@DisplayName("padrão de código fonte, properties")
class ArchPropertiesTest {

    @Test
    @DisplayName("deve ter o nome terminado com properties")
    void shouldHaveSimpleNameEndingWithProperties(final JavaClasses classes) {
        classes()
                .that().resideInAPackage("..properties..")
                .and().areNotMemberClasses()
                .should().haveSimpleNameEndingWith("Properties")
                .check(classes);
    }

    @Test
    @DisplayName("deve ter a annotation ConstructorBinding")
    void shouldBeAnnotatedWithConstructorBinding(final JavaClasses classes) {
        classes()
                .that().resideInAPackage("..properties..")
                .and().areNotMemberClasses()
                .should().beAnnotatedWith(ConstructorBinding.class)
                .check(classes);
    }

    @Test
    @DisplayName("deve ser a annotation ConfigurationProperties")
    void shouldBeAnnotatedWithConfigurationProperties(final JavaClasses classes) {
        classes()
                .that().resideInAPackage("..properties..")
                .and().areNotMemberClasses()
                .should().beAnnotatedWith(ConfigurationProperties.class)
                .check(classes);
    }
}
