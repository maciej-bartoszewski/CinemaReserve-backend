package com.cinemareserve.cinemareserve.enitity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "ticket_types")
public class TicketType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_type_id")
    private Long ticketTypeId;

    @Column(name = "ticket_name_pl")
    private String ticketNamePl;

    @Column(name = "ticket_name_en")
    private String ticketNameEn;

    @Column(name = "price_multiplier")
    private Double priceMultiplier;
}
