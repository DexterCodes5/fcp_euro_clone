package dev.dex.fcpeuro.model;

import dev.dex.fcpeuro.entity.kitpart.*;
import lombok.*;

@Getter
public class KitPartResponse {
    private Integer id;
    private Integer kitId;
    private PartResponse part;
    private Integer quantity;

    public KitPartResponse(KitPart kitPart) {
        this.id = kitPart.getId();
        this.kitId = kitPart.getKitId();
        this.part = new PartResponse(kitPart.getPart());
        this.quantity = kitPart.getQuantity();
    }
}
