package dev.dex.fcpeuro.model;

import dev.dex.fcpeuro.entity.*;
import lombok.*;

import java.util.*;

@Getter
@NoArgsConstructor
public class PartResponse {
    private Integer id;
    private String title;
    private String url;
    private String sku;
    private Integer fcpEuroId;
    private Integer quantity;
    private Double price;
    private String quality;
    private List<String> mfgNumbers;
    private Boolean kit;
    private String madeIn;
    private List<String> img;
    private Boolean universal;
    private Brand brand;
    private List<String> oeNumbers;
    private String categoryBot;
    private List<KitPartResponse> kitParts;
    private String productInformationHtml;

    public PartResponse(Part part) {
        this.id = part.getId();
        this.title = part.getTitle();
        this.url = part.getUrl();
        this.sku = part.getSku();
        this.fcpEuroId = part.getFcpEuroId();
        this.quantity = part.getQuantity();
        this.price = part.getPrice();
        this.quality = part.getQuality();
        this.mfgNumbers = part.getMfgNumbers();
        this.kit = part.getKit();
        this.madeIn = part.getMadeIn();
        this.img = part.getImg();
        this.universal = part.getUniversal();
        this.brand = part.getBrand();
        this.oeNumbers = part.getOeNumbers().stream()
                .map(OENumber::getOeNumber)
                .toList();
        this.categoryBot = part.getCategoryBot().getCategoryBot();
        this.kitParts = part.getKitParts().stream()
                .map(KitPartResponse::new)
                .toList();
        this.productInformationHtml = part.getProductInformationHtml();
    }
}
