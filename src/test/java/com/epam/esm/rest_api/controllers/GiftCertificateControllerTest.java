package com.epam.esm.rest_api.controllers;

import com.epam.esm.certificate_service.entities.GiftCertificate;
import com.epam.esm.certificate_service.entities.Tag;
import com.epam.esm.certificate_service.entities.User;
import com.epam.esm.certificate_service.service.GiftCertificateService;
import com.epam.esm.certificate_service.service.TagService;
import com.epam.esm.rest_api.dto.Mapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class GiftCertificateControllerTest {

    private MockMvc mockMvc;
    @Mock
    private TagService tagService;
    @Mock
    private GiftCertificateService certificateService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Mapper mapper = new Mapper();
    private GiftCertificate certificate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(new GiftCertificateController(certificateService, tagService, mapper)).build();

        certificate = new GiftCertificate();
        certificate.setId(1);
        certificate.setName("certificate");
        certificate.setDuration(10);
        certificate.setPrice(100);
        certificate.setDescription("test certificate");
        certificate.setCreateDate(LocalDateTime.now());
        certificate.setLastUpdateDate(LocalDateTime.now());
    }

    @Test
    void getCertificateById() throws Exception {
        when(certificateService.getGiftCertificateById(anyLong())).thenReturn(certificate);

        this.mockMvc.perform(get("/api/certificates/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(certificate.getName())));
    }

    @Test
    void getGiftCertificateByName() throws Exception {
        when(certificateService.getGiftCertificateByName(anyString())).thenReturn(certificate);

        this.mockMvc.perform(get("/api/certificates/findByName/{name}", "certificate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(certificate.getName())));
    }

    @Test
    void getGiftCertificatesByPart() throws Exception {
        List<GiftCertificate> certificateList = getCertificateList();

        when(certificateService.getGiftCertificatesByPart(anyString())).thenReturn(certificateList);

        this.mockMvc.perform(get("/api/certificates/findByPart/{part}", "test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(certificateList.size())))
                .andDo(print());
    }

    @Test
    void getAllCertificates() throws Exception {
        List<GiftCertificate> certificateList = getCertificateList();

        when(certificateService.getAllGiftCertificates(5, 0)).thenReturn(certificateList);

        this.mockMvc.perform(get("/api/certificates"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(certificateList.size())))
                .andDo(print());
    }

    private List<GiftCertificate> getCertificateList() {
        List<GiftCertificate> list = new ArrayList<>();
        GiftCertificate secondCertificate = new GiftCertificate();
        secondCertificate.setId(2);
        secondCertificate.setName("second");
        secondCertificate.setDescription("test");
        secondCertificate.setPrice(200);
        secondCertificate.setDuration(20);
        secondCertificate.setCreateDate(LocalDateTime.now());
        secondCertificate.setLastUpdateDate(LocalDateTime.now());

        list.add(certificate);
        list.add(secondCertificate);
        return list;
    }

    @Test
    void getCertificatesAscDate() throws Exception {
        List<GiftCertificate> list = getCertificateList()
                .stream().sorted(Comparator.comparing(GiftCertificate::getCreateDate)).collect(Collectors.toList());

        when(certificateService.sortGiftCertificatesByDateAsc(5, 0)).thenReturn(list);

        this.mockMvc.perform(get("/api/certificates/ascDate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(list.size())))
                .andDo(print());
    }

    @Test
    void getCertificatesDescDate() throws Exception {
        List<GiftCertificate> list = getCertificateList()
                .stream().sorted(Comparator.comparing(GiftCertificate::getCreateDate).reversed())
                .collect(Collectors.toList());

        when(certificateService.sortGiftCertificatesByDateDesc(5, 0)).thenReturn(list);

        this.mockMvc.perform(get("/api/certificates/descDate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(list.size())))
                .andDo(print());
    }

    @Test
    void getCertificatesAscName() throws Exception {
        List<GiftCertificate> list = getCertificateList()
                .stream().sorted(Comparator.comparing(GiftCertificate::getName)).collect(Collectors.toList());

        when(certificateService.sortGiftCertificatesByNameAsc(5, 0)).thenReturn(list);

        this.mockMvc.perform(get("/api/certificates/ascName"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(list.size())))
                .andDo(print());
    }

    @Test
    void getCertificatesDescName() throws Exception {
        List<GiftCertificate> list = getCertificateList().stream()
                .sorted(Comparator.comparing(GiftCertificate::getName).reversed()).collect(Collectors.toList());

        when(certificateService.sortGiftCertificatesByNameDesc(5, 0)).thenReturn(list);

        this.mockMvc.perform(get("/api/certificates/descName"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(list.size())))
                .andDo(print());
    }

    @Test
    void deleteCertificate() throws Exception {
        doNothing().when(certificateService).deleteGiftCertificate(anyLong());

        this.mockMvc.perform(delete("/api/certificates/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("Certificate with id = " + 1L + " was deleted"))
                .andDo(print());
    }

    @Test
    void addCertificate() throws Exception {
        doNothing().when(certificateService).addGiftCertificate(certificate);
        when(certificateService.getGiftCertificateByName(anyString())).thenReturn(certificate);

        this.mockMvc.perform(post("/api/certificates")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(certificate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(certificate.getName())))
                .andDo(print());
    }

    @Test
    void updateCertificate() throws Exception {
        doNothing().when(certificateService).updateGiftCertificate(certificate);
        when(certificateService.getGiftCertificateById(anyLong())).thenReturn(certificate);

        this.mockMvc.perform(put("/api/certificates")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(certificate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(certificate.getName())));
    }

    @Test
    void getGiftCertificatesByTag() throws Exception {
        Tag tag = new Tag("test_tag");
        tag.setId(5);
        tag.setCertificates(getCertificateList());

        when(tagService.findByName(anyString())).thenReturn(tag);

        this.mockMvc.perform(get("/api/certificates/findByTag/{name}", "test_tag"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(tag.getCertificates().size())))
                .andDo(print());
    }

    @Test
    void getGiftCertificatesByTags() throws Exception {
        Tag[] tags = new Tag[]{new Tag("test_tag"), new Tag("second_tag")};

        when(certificateService.getCertificatesByTags(tags)).thenReturn(getCertificateList());

        this.mockMvc.perform(get("/api/certificates/findByTags")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tags)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(getCertificateList().size())))
                .andDo(print());
    }
}