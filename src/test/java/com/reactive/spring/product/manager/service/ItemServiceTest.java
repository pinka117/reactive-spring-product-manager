package com.reactive.spring.product.manager.service;

import com.reactive.spring.product.manager.model.Item;
import com.reactive.spring.product.manager.repository.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.LinkedList;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


@RunWith(SpringRunner.class)
@ComponentScan(basePackages = "com.reactive.spring.product.manager")
@ContextConfiguration(classes = ItemService.class)
public class ItemServiceTest {

    @MockBean
    private ItemRepository itemRepository;
    @Autowired
    private ItemService itemService;
    private Flux<Item> fi;
    private Item item;
    private Mono<Item> empty;
    private Item emptyId;
    private Mono<Item> mono;

    @Before
    public void setUp() {
        item = new Item("id1", "name1", "location1");
        LinkedList<Item> li = new LinkedList<>();
        li.add(item);
        this.mono = Mono.just(item);
        this.fi = Flux.fromIterable(li);
        this.empty = Mono.empty();
        this.emptyId = new Item();
        this.emptyId.setName("pippo");
        this.emptyId.setLocation("pluto");
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
    public void AddNoIdTestOk() {
        given(itemRepository.findById(anyString())).
                willReturn(empty);
        addEmptyId();
        assertFalse(emptyId.getId().equals(""));

    }

    @Test
    public void AddNoIdTestFound() {
        Mono<Item> monoItem = Mono.just(item);
        given(itemRepository.findById(Integer.toString(1))).
                willReturn(monoItem);
        given(itemRepository.findById(Integer.toString(2))).
                willReturn(empty);
        addEmptyId();
        assertTrue(emptyId.getId().equals("2"));

    }

    private void addEmptyId() {
        itemService.add(emptyId);
        verify(itemRepository).save(emptyId);
    }

    @Test
    public void SearchOkTest() {
        addToSteam();
        given(itemRepository.findById("id1")).
                willReturn(mono);
        Item r = itemService.search("id1");
        assertEquals("id1", r.getId());
        assertEquals("name1", r.getName());
        assertEquals("location1", r.getLocation());

    }

    @Test
    public void SearchNotFoundTest() {
        addToSteam();
        given(itemRepository.findById("id1")).
                willReturn(null);
        Item r = itemService.search("id1");
        assertNull(r);


    }



    public void addToSteam() {
        itemService.add(item);
    }


}
