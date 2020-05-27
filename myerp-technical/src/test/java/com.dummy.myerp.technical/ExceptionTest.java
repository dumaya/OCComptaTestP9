package com.dummy.myerp.technical;

import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;
import com.dummy.myerp.technical.exception.TechnicalException;
import org.junit.Assert;
import org.junit.Test;

public class ExceptionTest {

    @Test
    public void functionalExceptionTest() {
        FunctionalException functionalException = new FunctionalException(new Throwable("throw"));
        Assert.assertEquals(functionalException.getCause().getMessage(),"throw");

        FunctionalException functionalException1 = new FunctionalException("throw");
        Assert.assertEquals(functionalException1.getMessage(),"throw");

        FunctionalException functionalException2 = new FunctionalException("throw", new Throwable("bla"));
        Assert.assertEquals(functionalException2.getMessage(),"throw");
        Assert.assertEquals(functionalException2.getCause().getMessage(),"bla");

    }

    @Test
    public void notFoundExceptionTest() {
        NotFoundException notFoundException = new NotFoundException(new Throwable("throw"));
        Assert.assertEquals(notFoundException.getCause().getMessage(),"throw");

        NotFoundException notFoundException1 = new NotFoundException("throw");
        Assert.assertEquals(notFoundException1.getMessage(),"throw");

        NotFoundException notFoundException2 = new NotFoundException("throw", new Throwable("bla"));
        Assert.assertEquals(notFoundException2.getMessage(),"throw");
        Assert.assertEquals(notFoundException2.getCause().getMessage(),"bla");

    }

    @Test
    public void technicalExceptionTest() {
        TechnicalException technicalException = new TechnicalException(new Throwable("throw"));
        Assert.assertEquals(technicalException.getCause().getMessage(),"throw");

        TechnicalException technicalException1 = new TechnicalException("throw");
        Assert.assertEquals(technicalException1.getMessage(),"throw");

        TechnicalException technicalException2 = new TechnicalException("throw", new Throwable("bla"));
        Assert.assertEquals(technicalException2.getMessage(),"throw");
        Assert.assertEquals(technicalException2.getCause().getMessage(),"bla");

    }
}