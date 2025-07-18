package org.kehrbusch.procedure;

import org.junit.Test;
import org.kehrbusch.entities.AddressDomain;
import org.kehrbusch.entities.Result;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ResultTest {
    @Test
    public void testThatAddAddressIsWorking(){
        Result result = new Result(" , ", "IT");

        result.addAddress(new AddressDomain("123", "1234", "test", "test", "Test", 4, true));
        result.addAddress(new AddressDomain("123", "123", "test", "test", "Test", 0, true));
        result.addAddress(new AddressDomain("123", "1234", "test", "test", "Test", 2, true));

        //assertThat(result.getMinFalseChars(), is(4));
        assertThat(result.getAddresses().size(), is(3));
        assertThat(result.getAddresses().get(0).getFalseChars(), is(0));
        assertThat(result.getAddresses().get(0).getZip(), is("123"));
        assertThat(result.getAddresses().get(1).getFalseChars(), is(2));
        assertThat(result.getAddresses().get(2).getFalseChars(), is(4));
    }

    @Test
    public void testThatAddAddressIsWorkingForCombinationOfEndedNotEnded(){
        Result result = new Result(" , ", "IT");

        result.addAddress(new AddressDomain("123", "1234","test","test","Test", 4, true));
        result.addAddress(new AddressDomain("123", "123","test","test","Test", 0, false));
        result.addAddress(new AddressDomain("123", "12345","test","test","Test",0,true));

        assertThat(result.getAddresses().size(), is(3));
        assertThat(result.getAddresses().get(0).getFalseChars(), is(0));
        assertThat(result.getAddresses().get(0).getZip(), is("12345"));
        assertThat(result.getAddresses().get(1).getFalseChars(), is(0));
        assertThat(result.getAddresses().get(1).getZip(), is("123"));
        assertThat(result.getAddresses().get(2).getFalseChars(), is(4));
        assertThat(result.getAddresses().get(2).getZip(), is("1234"));
    }

    @Test
    public void testThatAddAddressIsWorkingWithReversedEndedAndNotEnded(){
        Result result = new Result(" , ", "IT");

        result.addAddress(new AddressDomain("123", "1234","test","test","Test", 4, true));
        result.addAddress(new AddressDomain("123", "123","test","test","Test", 0, true));
        result.addAddress(new AddressDomain("123", "12345","test","test","Test",0,false));

        assertThat(result.getAddresses().size(), is(3));
        assertThat(result.getAddresses().get(0).getFalseChars(), is(0));
        assertThat(result.getAddresses().get(0).getZip(), is("123"));
        assertThat(result.getAddresses().get(1).getFalseChars(), is(0));
        assertThat(result.getAddresses().get(1).getZip(), is("12345"));
        assertThat(result.getAddresses().get(2).getFalseChars(), is(4));
        assertThat(result.getAddresses().get(2).getZip(), is("1234"));
    }

    @Test
    public void testThatContainsWorks(){
        List<AddressDomain> addressDomains = new ArrayList<>();
        AddressDomain addressDomain = new AddressDomain("123", "1", "test", "test", "it", 0, true);
        AddressDomain addressDomain1 = new AddressDomain("123", "1","test", "test1", "de", 0, true);

        addressDomains.add(addressDomain);

        if (!addressDomains.contains(addressDomain1)){
            addressDomains.add(addressDomain1);
        }

        assertThat(addressDomains.size(), is(1));
    }
}
