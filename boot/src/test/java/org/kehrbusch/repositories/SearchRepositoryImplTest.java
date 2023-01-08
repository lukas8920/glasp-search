package org.kehrbusch.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kehrbusch.entities.Address;
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
        Address a1 = new Address("123", "123", "Test", null, "IT", 0, false);
        Address a2 = new Address("123", "124", "Test", null, "IT", 0, false);
        Address a3 = new Address("123", "125", "Test", null, "IT", 0, false);
        Address a4 = new Address("123", "126", "Test", null, "IT", 0, false);
        Address a5 = new Address("123", "127", "Test", null, "IT", 0, false);
        Address a6 = new Address("123", "128", "Test", null, "IT", 0, false);
        Address a7 = new Address("123", "129", "Test", null, "IT", 0, false);
        Address a8 = new Address("123", "130", "Test", null, "IT", 0, false);
        Address a9 = new Address("123", "131", "Test", null, "IT", 0, false);
        Address a10 = new Address("123", "132", "Test", null, "IT", 0, false);

        Address[] addresses = {a1, a2, a3, a4, a5, a6, a7, a8, a9, a10};
        Arrays.stream(addresses).forEach(address -> this.searchRepository.persist(address));

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
