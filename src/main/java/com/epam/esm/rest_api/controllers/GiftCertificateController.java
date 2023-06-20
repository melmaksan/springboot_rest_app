package com.epam.esm.rest_api.controllers;

import com.epam.esm.certificate_service.entities.GiftCertificate;
import com.epam.esm.certificate_service.entities.Tag;
import com.epam.esm.certificate_service.service.GiftCertificateService;
import com.epam.esm.certificate_service.service.TagService;
import com.epam.esm.rest_api.dto.CertificateDTO;
import com.epam.esm.rest_api.dto.Mapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.epam.esm.rest_api.Constants.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/certificates", produces = MediaTypes.HAL_JSON_VALUE)
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;
    private final TagService tagService;
    private final Mapper mapper;

    public GiftCertificateController(GiftCertificateService giftCertificateService, TagService tagService, Mapper mapper) {
        this.giftCertificateService = giftCertificateService;
        this.tagService = tagService;
        this.mapper = mapper;
    }

    @GetMapping(value = "/{id}")
    public CertificateDTO getCertificateById(@PathVariable long id) {
        return mapper.toCertificateDto(giftCertificateService.getGiftCertificateById(id));
    }

    @GetMapping(value = "/findByName/{name}")
    public CertificateDTO getGiftCertificateByName(@PathVariable String name) {
        return mapper.toCertificateDto(giftCertificateService.getGiftCertificateByName(name));
    }

    @GetMapping(value = "/findByPart/{part}")
    public CollectionModel<CertificateDTO> getGiftCertificatesByPart(@PathVariable String part) {
        List<CertificateDTO> dtoList = giftCertificateService.getGiftCertificatesByPart(part)
                .stream().map(mapper::toCertificateDto).collect(Collectors.toList());

        Link link = linkTo(methodOn(GiftCertificateController.class).getGiftCertificatesByPart(part)).withSelfRel();

        return CollectionModel.of(dtoList).add(link);
    }

    @GetMapping
    public List<CertificateDTO> getAllCertificates(
            @RequestParam(value = PAGE, required = false, defaultValue = DEFAULT_CERTIFICATE_PAGE_NUMBER) int page,
            @RequestParam(value = "size", required = false, defaultValue = DEFAULT_CERTIFICATE_PAGE_SIZE) int size) {
        return giftCertificateService.getAllGiftCertificates(size,
                (page - 1) * size).stream().map(mapper::toCertificateDto).collect(Collectors.toList());
    }

    @GetMapping(value = "/ascDate")
    public CollectionModel<CertificateDTO> getCertificatesAscDate(
            @RequestParam(value = PAGE, required = false, defaultValue = DEFAULT_CERTIFICATE_PAGE_NUMBER) int page,
            @RequestParam(value = "size", required = false, defaultValue = DEFAULT_CERTIFICATE_PAGE_SIZE) int size) {
        List<CertificateDTO> dtoList = giftCertificateService.sortGiftCertificatesByDateAsc(size,
                (page - 1) * size).stream().map(mapper::toCertificateDto).collect(Collectors.toList());

        Link link = linkTo(methodOn(GiftCertificateController.class).getCertificatesAscDate(page, size)).withSelfRel();

        return CollectionModel.of(dtoList).add(link);
    }

    @GetMapping(value = "/descDate")
    public CollectionModel<CertificateDTO> getCertificatesDescDate(
            @RequestParam(value = PAGE, required = false, defaultValue = DEFAULT_CERTIFICATE_PAGE_NUMBER) int page,
            @RequestParam(value = SIZE, required = false, defaultValue = DEFAULT_CERTIFICATE_PAGE_SIZE) int size) {
        List<CertificateDTO> dtoList = giftCertificateService.sortGiftCertificatesByDateDesc(size,
                (page - 1) * size).stream().map(mapper::toCertificateDto).collect(Collectors.toList());

        Link link = linkTo(methodOn(GiftCertificateController.class).getCertificatesDescDate(page, size)).withSelfRel();

        return CollectionModel.of(dtoList).add(link);
    }

    @GetMapping(value = "/ascName")
    public CollectionModel<CertificateDTO> getCertificatesAscName(
            @RequestParam(value = PAGE, required = false, defaultValue = DEFAULT_CERTIFICATE_PAGE_NUMBER) int page,
            @RequestParam(value = SIZE, required = false, defaultValue = DEFAULT_CERTIFICATE_PAGE_SIZE) int size) {
        List<CertificateDTO> dtoList = giftCertificateService.sortGiftCertificatesByNameAsc(size,
                (page - 1) * size).stream().map(mapper::toCertificateDto).collect(Collectors.toList());

        Link link = linkTo(methodOn(GiftCertificateController.class).getCertificatesAscName(page, size)).withSelfRel();

        return CollectionModel.of(dtoList).add(link);
    }

    @GetMapping(value = "/descName")
    public CollectionModel<CertificateDTO> getCertificatesDescName(
            @RequestParam(value = PAGE, required = false, defaultValue = DEFAULT_CERTIFICATE_PAGE_NUMBER) int page,
            @RequestParam(value = SIZE, required = false, defaultValue = DEFAULT_CERTIFICATE_PAGE_SIZE) int size) {
        List<CertificateDTO> dtoList = giftCertificateService.sortGiftCertificatesByNameDesc(size,
                (page - 1) * size).stream().map(mapper::toCertificateDto).collect(Collectors.toList());

        Link link = linkTo(methodOn(GiftCertificateController.class).getCertificatesDescName(page, size)).withSelfRel();

        return CollectionModel.of(dtoList).add(link);
    }

    @DeleteMapping(value = "/{id}")
    public String deleteCertificate(@PathVariable long id) {
        giftCertificateService.deleteGiftCertificate(id);
        return "Certificate with id = " + id + " was deleted";
    }

    @PostMapping
    public CertificateDTO addCertificate(@RequestBody GiftCertificate giftCertificate) {
        giftCertificateService.addGiftCertificate(giftCertificate);
        return mapper.toCertificateDto(giftCertificateService.getGiftCertificateByName(giftCertificate.getName()));
    }

    @PutMapping
    public CertificateDTO updateCertificate(@RequestBody GiftCertificate giftCertificate) {
        giftCertificateService.updateGiftCertificate(giftCertificate);
        return getCertificateById(giftCertificate.getId());
    }

    @GetMapping(value = "/findByTag/{name}")
    public CollectionModel<CertificateDTO> getGiftCertificatesByTag(@PathVariable String name) {
        Tag tag = tagService.findByName(name);
        List<CertificateDTO> dtoList = tag.getCertificates().stream().map(mapper::toCertificateDto)
                .collect(Collectors.toList());

        Link link = linkTo(methodOn(GiftCertificateController.class).getGiftCertificatesByTag(name)).withSelfRel();

        return CollectionModel.of(dtoList).add(link);
    }

    @GetMapping(value = "/findByTags")
    public CollectionModel<CertificateDTO> getGiftCertificatesByTags(@RequestBody Tag... tags) {
        List<CertificateDTO> dtoList = giftCertificateService.getCertificatesByTags(tags)
                .stream().map(mapper::toCertificateDto).collect(Collectors.toList());

        Link link = linkTo(methodOn(GiftCertificateController.class).getGiftCertificatesByTags(tags)).withSelfRel();

        return CollectionModel.of(dtoList).add(link);
    }
}
