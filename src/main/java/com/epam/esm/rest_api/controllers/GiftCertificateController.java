package com.epam.esm.rest_api.controllers;

import com.epam.esm.certificate_service.entities.GiftCertificate;
import com.epam.esm.certificate_service.entities.Tag;
import com.epam.esm.certificate_service.service.GiftCertificateService;
import com.epam.esm.certificate_service.service.TagService;
import com.epam.esm.rest_api.dto.CertificateDTO;
import com.epam.esm.rest_api.dto.Mapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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

    @GetMapping("/certificates")
    public CollectionModel<CertificateDTO> getAllCertificates(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "5") int size) {
        List<GiftCertificate> certificates = giftCertificateService.getAllGiftCertificates(size,
                (page - 1) * size);

        List<CertificateDTO> dtoList = certificates.stream().map(mapper::toCertificateDto)
                .collect(Collectors.toList());
        Link link = linkTo(methodOn(GiftCertificateController.class).getAllCertificates(page, size)).withSelfRel();

        return CollectionModel.of(dtoList).add(link);
    }

    @GetMapping(value = "/certificates/ascDate")
    public CollectionModel<CertificateDTO> getCertificatesAscDate(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "5") int size) {
        List<GiftCertificate> certificates = giftCertificateService.sortGiftCertificatesByDateAsc(size,
                (page - 1) * size);

        List<CertificateDTO> dtoList = certificates.stream().map(mapper::toCertificateDto)
                .collect(Collectors.toList());
        Link link = linkTo(methodOn(GiftCertificateController.class).getCertificatesAscDate(page, size)).withSelfRel();

        return CollectionModel.of(dtoList).add(link);
    }

    @GetMapping(value = "/certificates/descDate")
    public CollectionModel<CertificateDTO> getCertificatesDescDate(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "5") int size) {
        List<GiftCertificate> certificates = giftCertificateService.sortGiftCertificatesByDateDesc(size,
                (page - 1) * size);

        List<CertificateDTO> dtoList = certificates.stream().map(mapper::toCertificateDto)
                .collect(Collectors.toList());
        Link link = linkTo(methodOn(GiftCertificateController.class).getCertificatesDescDate(page, size)).withSelfRel();

        return CollectionModel.of(dtoList).add(link);
    }

    @GetMapping(value = "/certificates/ascName")
    public CollectionModel<CertificateDTO> getCertificatesAscName(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "5") int size) {
        List<GiftCertificate> certificates = giftCertificateService.sortGiftCertificatesByNameAsc(size,
                (page - 1) * size);

        List<CertificateDTO> dtoList = certificates.stream().map(mapper::toCertificateDto)
                .collect(Collectors.toList());
        Link link = linkTo(methodOn(GiftCertificateController.class).getCertificatesAscName(page, size)).withSelfRel();

        return CollectionModel.of(dtoList).add(link);
    }

    @GetMapping(value = "/certificates/descName")
    public CollectionModel<CertificateDTO> getCertificatesDescName(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "5") int size) {
        List<GiftCertificate> certificates = giftCertificateService.sortGiftCertificatesByNameDesc(size,
                (page - 1) * size);

        List<CertificateDTO> dtoList = certificates.stream().map(mapper::toCertificateDto)
                .collect(Collectors.toList());
        Link link = linkTo(methodOn(GiftCertificateController.class).getCertificatesDescName(page, size)).withSelfRel();

        return CollectionModel.of(dtoList).add(link);
    }

    @DeleteMapping(value = "/certificates/{id}")
    public String deleteCertificate(@PathVariable long id) {
        giftCertificateService.deleteGiftCertificate(id);
        return "Certificate with id = " + id + " was deleted";
    }

    @PostMapping(value = "/certificates")
    public CertificateDTO addCertificate(@RequestBody GiftCertificate giftCertificate) {
        giftCertificateService.addGiftCertificate(giftCertificate);
        return mapper.toCertificateDto(giftCertificateService.getGiftCertificateByName(giftCertificate.getName()));
    }

    @PutMapping(value = "/certificates")
    public CertificateDTO updateCertificate(@RequestBody GiftCertificate giftCertificate) {
        giftCertificateService.updateGiftCertificate(giftCertificate);
        return getCertificateById(giftCertificate.getId());
    }

    @GetMapping(value = "/certificates/findByTag/{name}")
    public CollectionModel<CertificateDTO> getGiftCertificatesByTag(@PathVariable String name) {
        Tag tag = tagService.findByName(name);
        List<GiftCertificate> certificates = tag.getCertificates();

        List<CertificateDTO> dtoList = certificates.stream().map(mapper::toCertificateDto)
                .collect(Collectors.toList());
        Link link = linkTo(methodOn(GiftCertificateController.class).getGiftCertificatesByTag(name)).withSelfRel();

        return CollectionModel.of(dtoList).add(link);
    }

    @GetMapping(value = "/certificates/findByTags")
    public CollectionModel<CertificateDTO> getGiftCertificatesByTags(@RequestBody Tag... tags) {
        List<GiftCertificate> certificateList = giftCertificateService.getCertificatesByTags(tags);

        List<CertificateDTO> dtoList = certificateList.stream().map(mapper::toCertificateDto)
                .collect(Collectors.toList());
        Link link = linkTo(methodOn(GiftCertificateController.class).getGiftCertificatesByTags(tags)).withSelfRel();

        return CollectionModel.of(dtoList).add(link);
    }
}
