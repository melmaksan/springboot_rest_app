package com.epam.esm.rest_api.controllers;

import com.epam.esm.certificate_service.entities.GiftCertificate;
import com.epam.esm.certificate_service.entities.Tag;
import com.epam.esm.certificate_service.service.GiftCertificateService;
import com.epam.esm.certificate_service.service.TagService;
import com.epam.esm.rest_api.dto.CertificateDTO;
import com.epam.esm.rest_api.dto.Mapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;
    private final TagService tagService;
    private final Mapper mapper;

    public GiftCertificateController(GiftCertificateService giftCertificateService, TagService tagService, Mapper mapper) {
        this.giftCertificateService = giftCertificateService;
        this.tagService = tagService;
        this.mapper = mapper;
    }

    @GetMapping(value = "/certificates/{id}")
    public CertificateDTO getCertificateById(@PathVariable long id) {
        return mapper.toCertificateDto(giftCertificateService.getGiftCertificateById(id));
    }

    @GetMapping(value = "/certificates/findByName/{name}")
    public CertificateDTO getGiftCertificateByName(@PathVariable String name) {
        return mapper.toCertificateDto(giftCertificateService.getGiftCertificateByName(name));
    }

    @GetMapping(value = "/certificates/findByPart/{part}")
    public List<CertificateDTO> getGiftCertificatesByPart(@PathVariable String part) {
        return giftCertificateService.getGiftCertificatesByPart(part)
                .stream().map(mapper::toCertificateDto).collect(Collectors.toList());
    }

    @GetMapping(value = "/certificates/findByTag/{name}")
    public List<CertificateDTO> getGiftCertificatesByTag(@PathVariable String name) {
        Tag tag = tagService.findByName(name);
        return tag.getCertificates().stream().map(mapper::toCertificateDto).collect(Collectors.toList());
    }


    @GetMapping("/certificates")
    public List<CertificateDTO> getAllCertificates() {
        return giftCertificateService.getAllGiftCertificates()
                .stream().map(mapper::toCertificateDto).collect(Collectors.toList());
    }

    @GetMapping(value = "/certificates/ascDate")
    public List<CertificateDTO> getCertificatesAscDate() {
        return giftCertificateService.sortGiftCertificatesByDateAsc()
                .stream().map(mapper::toCertificateDto).collect(Collectors.toList());
    }

    @GetMapping(value = "/certificates/descDate")
    public List<CertificateDTO> getCertificatesDescDate() {
        return giftCertificateService.sortGiftCertificatesByDateDesc()
                .stream().map(mapper::toCertificateDto).collect(Collectors.toList());
    }

    @GetMapping(value = "/certificates/ascName")
    public List<CertificateDTO> getCertificatesAscName() {
        return giftCertificateService.sortGiftCertificatesByNameAsc()
                .stream().map(mapper::toCertificateDto).collect(Collectors.toList());
    }

    @GetMapping(value = "/certificates/descName")
    public List<CertificateDTO> getCertificatesDescName() {
        return giftCertificateService.sortGiftCertificatesByNameDesc()
                .stream().map(mapper::toCertificateDto).collect(Collectors.toList());
    }

    @DeleteMapping(value = "/certificates/{id}")
    public String deleteCertificate(@PathVariable long id) {
        giftCertificateService.deleteGiftCertificate(id);
        return "Certificate with id = " + id + " was deleted";
    }

    @PostMapping(value = "/certificates")
    public CertificateDTO addCertificate(@RequestBody GiftCertificate giftCertificate) {
        giftCertificateService.addGiftCertificate(giftCertificate);
        return mapper.toCertificateDto(giftCertificate);
    }

    @PutMapping(value = "/certificates")
    public CertificateDTO updateCertificate(@RequestBody GiftCertificate giftCertificate) {
        giftCertificateService.updateGiftCertificate(giftCertificate);
        return getCertificateById(giftCertificate.getId());
    }
}
