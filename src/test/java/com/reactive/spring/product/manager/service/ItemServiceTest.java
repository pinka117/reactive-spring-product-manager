package com.reactive.spring.product.manager.service;

import com.reactive.spring.product.manager.model.Item;
import com.reactive.spring.product.manager.repository.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;

import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


@RunWith(SpringRunner.class)
@ComponentScan(basePackages = "com.reactive.spring.product.manager")
@ContextConfiguration(classes = ItemService.class)
public class ItemServiceTest {
    @MockBean
    private MongoTemplate mongoTemplate;
    @MockBean
    private ItemRepository itemRepository;
    @Autowired
    private ItemService itemService;
    private Flux<Item> fi;
    private Item item;

    @Before
    public void setUp() {
        item = new Item("id1", "name1", "location1", System.currentTimeMillis());
        LinkedList<Item> li = new LinkedList<>();
        li.add(item);
        this.fi = Flux.fromIterable(li);
    }

    @Test
    public void findEmptyTest() {
        assertNull(itemService.findAll());
    }

    @Test
    public void findOneTest() {
        given(itemRepository.findAll()).
                willReturn(fi);
        assertEquals(fi, itemService.findAll());
        verify(itemRepository).findAll();
    }

    @Test
    public void AddTest() {
        addToSteam();
        verify(itemRepository).save(item);
    }

    @Test
    public void findBeforeOkTest() {
        addToSteam();
        Long futureDate = System.currentTimeMillis() + 20L;
        given(itemRepository.findByTimestampLessThanEqualOrderByTimestampDesc(futureDate)).
                willReturn(fi);
        assertEquals(fi, itemService.findBeforeTimestamp(futureDate));
        verify(itemRepository).findByTimestampLessThanEqualOrderByTimestampDesc(futureDate);
    }

    @Test
    public void findBeforeNotFoundTest() {

        Long futureDate = System.currentTimeMillis() + 20L;
        given(itemRepository.findByTimestampLessThanEqualOrderByTimestampDesc(futureDate)).
                willReturn(null);
        assertNull(itemService.findBeforeTimestamp(futureDate));
        verify(itemRepository).findByTimestampLessThanEqualOrderByTimestampDesc(futureDate);
    }

    @Test
    public void findAfterOkTest() {
        addToSteam();
        Long pastDate = 1L;
        given(itemRepository.findByTimestampGreaterThan(pastDate)).
                willReturn(fi);
        assertEquals(fi, itemService.findAfterTimestamp(pastDate));
        verify(itemRepository).findByTimestampGreaterThan(pastDate);
    }

    @Test
    public void findAfterNotFoundTest() {
        Long pastDate = 1L;
        given(itemRepository.findByTimestampGreaterThan(pastDate)).
                willReturn(null);
        assertNull(itemService.findAfterTimestamp(pastDate));
        verify(itemRepository).findByTimestampGreaterThan(pastDate);
    }

    public void addToSteam() {
        itemService.add(item);
    }

}
