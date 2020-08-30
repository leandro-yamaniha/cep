package com.yamaniha.cep.archtest;


import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Repository;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.yamaniha.cep.archtest.annotation.ArchTest;

@ArchTest
@DisplayName("padrão de código fonte, repository")
class ArchRepositoryTest {

    @Test
    @DisplayName("deve terminar com Repository")
    void shouldHaveSimpleNameEndingWithServiceImpl(final JavaClasses classes) {
        classes()
                .that().resideInAPackage("..repository..")
                .should().haveSimpleNameEndingWith("Repository")
                .check(classes);

    }

    @Test
    @DisplayName("deve ter a annotation Repository")
    void shouldBeAnnotatedWithService(final JavaClasses classes) {
        classes()
                .that().resideInAPackage("..repository..")
                .should().beAnnotatedWith(Repository.class)
                .check(classes);

    }


}
