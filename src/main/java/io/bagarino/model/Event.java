/**
 * This file is part of bagarino.
 *
 * bagarino is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * bagarino is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with bagarino.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.bagarino.model;

import io.bagarino.datamapper.ConstructorAnnotationRowMapper.Column;
import io.bagarino.model.transaction.PaymentProxy;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class Event {
    private final int id;
    private final String shortName;
    private final String description;
    private final String location;
    private final String latitude;
    private final String longitude;
    private final Date begin;
    private final Date end;
    private final BigDecimal regularPrice;
    private final String currency;
    private final int availableSeats;
    private final boolean vatIncluded;
    private final BigDecimal vat;
    private final List<PaymentProxy> allowedPaymentProxies;


    public Event(@Column("id") int id,
                 @Column("short_name") String shortName,
                 @Column("description") String description,
                 @Column("location") String location,
                 @Column("latitude") String latitude,
                 @Column("longitude") String longitude,
                 @Column("start_ts") Date begin,
                 @Column("end_ts") Date end,
                 @Column("regular_price") BigDecimal regularPrice,
                 @Column("currency") String currency,
                 @Column("available_seats") int availableSeats,
                 @Column("vat_included") boolean vatIncluded,
                 @Column("vat") BigDecimal vat,
                 @Column("allowed_payment_proxies") String allowedPaymentProxies) {
        this.id = id;
        this.shortName = shortName;
        this.description = description;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.begin = begin;
        this.end = end;
        this.regularPrice = regularPrice;
        this.currency = currency;
        this.availableSeats = availableSeats;
        this.vatIncluded = vatIncluded;
        this.vat = vat;
        this.allowedPaymentProxies = Arrays.stream(allowedPaymentProxies.split(","))
                .filter(StringUtils::isNotBlank)
                .map(PaymentProxy::valueOf)
                .collect(Collectors.toList());
    }

    public Long getRegularPriceInCents(){
        return regularPrice.multiply(new BigDecimal(100)).longValue();
    }
}
