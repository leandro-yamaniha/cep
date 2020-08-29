package com.yamaniha.cep.archtest;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.fields;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.yamaniha.cep.archtest.annotation.ArchTest;

@ArchTest
class ArchCommonTest {

    @Test
    @DisplayName("verifing classes using autowired")
    void shouldFieldsNotBeAnnotatedWithAutowired(final JavaClasses classes) {
        fields().should()
                .notBeAnnotatedWith(Autowired.class)
                .check(classes);
    }
}
