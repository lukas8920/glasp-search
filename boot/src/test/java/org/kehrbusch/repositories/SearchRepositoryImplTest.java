package org.kehrbusch.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kehrbusch.entities.AddressDomain;
import org.kehrbusch.entities.loader.CharElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchRepositoryImplTest {
    @Autowired
    SearchRepository searchRepository;

    @Test
    public void testThatGetRootsWorks(){
        AddressDomain a1 = new AddressDomain("123", "123", "Test", null, "IT", 0, false);
        AddressDomain a2 = new AddressDomain("123", "124", "Test", null, "IT", 0, false);
        AddressDomain a3 = new AddressDomain("123", "125", "Test", null, "IT", 0, false);
        AddressDomain a4 = new AddressDomain("123", "126", "Test", null, "IT", 0, false);
        AddressDomain a5 = new AddressDomain("123", "127", "Test", null, "IT", 0, false);
        AddressDomain a6 = new AddressDomain("123", "128", "Test", null, "IT", 0, false);
        AddressDomain a7 = new AddressDomain("123", "129", "Test", null, "IT", 0, false);
        AddressDomain a8 = new AddressDomain("123", "130", "Test", null, "IT", 0, false);
        AddressDomain a9 = new AddressDomain("123", "131", "Test", null, "IT", 0, false);
        AddressDomain a10 = new AddressDomain("123", "132", "Test", null, "IT", 0, false);

        AddressDomain[] addressDomains = {a1, a2, a3, a4, a5, a6, a7, a8, a9, a10};
        Arrays.stream(addressDomains).forEach(address -> this.searchRepository.persist(address));

        List<CharElement> roots = this.searchRepository.getRoots();

        assertThat(roots.size(), is(10));

        List<CharElement> children1 = roots.get(1).getChildNodes().get(0)
                .getChildNodes();
        assertThat(children1.size(), is(7));
        List<String> data1 = children1.stream().map(CharElement::getNodeValue).collect(Collectors.toList());
        assertThat(data1.contains("3"), is(true));
        assertThat(data1.contains("4"), is(true));
        assertThat(data1.contains("5"), is(true));
        assertThat(data1.contains("6"), is(true));
        assertThat(data1.contains("7"), is(true));
        assertThat(data1.contains("8"), is(true));
        assertThat(data1.contains("9"), is(true));

        List<CharElement> children2 = roots.get(1).getChildNodes().get(1)
                .getChildNodes();
        assertThat(children2.size(), is(3));
        List<String> data2 = children2.stream().map(CharElement::getNodeValue).collect(Collectors.toList());
        assertThat(data2.contains("0"), is(true));
        assertThat(data2.contains("1"), is(true));
        assertThat(data2.contains("2"), is(true));
    }
}
