package com.yamaniha.cep.archtest;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.yamaniha.cep.archtest.annotation.ArchTest;

@ArchTest
@DisplayName("padrão de código fonte quando é exception")
class ArchExceptionTest {
	
    @Test
    @DisplayName("verificando classes no pacote exception terminadas com exception ...")
    void shouldHaveSimpleNameEndingWithException(final JavaClasses classes) {
        classes()
                .that().resideInAPackage("..exception..")
                .should().haveSimpleNameEndingWith("Exception")
                .check(classes);
    }
}
