package com.yourlife.your.life.controller.finance;

import com.yourlife.your.life.model.dto.finance.entry.EntryPostDTO;
import com.yourlife.your.life.model.dto.finance.entry.EntryPutDTO;
import com.yourlife.your.life.model.entity.finance.Entry;
import com.yourlife.your.life.service.finance.EntryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@DisplayName("EntryController")
public class EntryControllerTest {

    @Mock
    private EntryService entryService;

    @InjectMocks
    private EntryController entryController;

    private Entry entryMock;

    @BeforeEach
    public void setUp() {
        entryMock = Entry
                .builder()
                .name("Payment for the project")
                .value(1000)
                .description("Payment for your ze's grocery project")
                .deleted(false)
                .build();
    }

    @Test
    @DisplayName("Save - Creating new record successfully!")
    void testSaveEntry() {
        EntryPostDTO entryPostDTO = EntryPostDTO
                                        .builder()
                                        .name("Payment for the project")
                                        .value(1000)
                                        .description("Payment for your ze's grocery project")
                                        .build();

        when(entryService.save(entryPostDTO)).thenReturn(entryMock);

        ResponseEntity<Entry> responseEntity = entryController.saveEntry(entryPostDTO);

        assertEquals(HttpStatus.CREATED,responseEntity.getStatusCode());
        assertEquals(entryMock,responseEntity.getBody());
    }

    @Test
    @DisplayName("GetAll - Searching multiple records at once")
    void testGetAllEntryByUser(){
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry());
        entries.add(new Entry());

        when(entryService.findAllByUser()).thenReturn(entries);

        ResponseEntity<List<Entry>> responseEntity = entryController.getAllEntryByUser();

        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(2, Objects.requireNonNull(responseEntity.getBody()).size());
    }

    @Test
    @DisplayName("Updated - Changing a record")
    void testUpdateEntry(){
        EntryPutDTO entryPutDTO = EntryPutDTO
                                    .builder()
                                    .name("Payment for the project")
                                    .value(1000)
                                    .description("Payment for your ze's grocery project")
                                    .build();

        when(entryService.update("67a782cbf1c9cc32ec877f00", entryPutDTO)).thenReturn(entryMock);

        ResponseEntity<Entry> responseEntity = entryController.updateEntry(entryPutDTO,"67a782cbf1c9cc32ec877f00");

        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(entryMock,responseEntity.getBody());
    }

    @Test
    @DisplayName("Deleted - Deleting a record")
    void testDeleteEntry(){
        ResponseEntity<Void> responseEntity = entryController.deleteEntry("67a782cbf1c9cc32ec877f00");
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
    }
}
