/**
 * This file is part of alf.io.
 *
 * alf.io is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * alf.io is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with alf.io.  If not, see <http://www.gnu.org/licenses/>.
 */
package alfio.repository;

import alfio.manager.support.CheckInStatistics;
import alfio.model.Event;
import alfio.model.EventAndOrganizationId;
import alfio.model.EventStatisticView;
import alfio.model.PriceContainer;
import ch.digitalfondue.npjt.*;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@QueryRepository
public interface EventRepository {

    @Query("select * from event where id = :eventId")
    Event findById(@Bind("eventId") int eventId);

    @Query("select display_name from event where id = :eventId")
    String getDisplayNameById(@Bind("eventId") int eventId);

    @Query("select id, org_id from event where id = :eventId")
    EventAndOrganizationId findEventAndOrganizationIdById(@Bind("eventId") int eventId);

    @Query("select * from event where id = :eventId")
    Optional<Event> findOptionalById(@Bind("eventId") int eventId);

    @Query("select exists(select 1 from event where id = :eventId)")
    boolean existsById(@Bind("eventId") int eventId);

    @Query("select exists(select 1 from event where short_name = :eventName)")
    boolean existsByShortName(@Bind("eventName") String eventName);

    @Query("select id, org_id from event where id = :eventId")
    Optional<EventAndOrganizationId> findOptionalEventAndOrganizationIdById(@Bind("eventId") int eventId);
    
    @Query("select org_id from event where id = :eventId")
    int findOrganizationIdByEventId(@Bind("eventId") int eventId);

    default ZoneId getZoneIdByEventId(int eventId) {
        return TimeZone.getTimeZone(getTimeZoneByEventId(eventId)).toZoneId();
    }

    @Query("select currency from event where id = :eventId")
    String getEventCurrencyCode(@Bind("eventId") int eventId);

    @Query("select time_zone from event where id = :eventId")
    String getTimeZoneByEventId(@Bind("eventId") int eventId);

    @Query("select * from event where short_name = :eventName")
    Event findByShortName(@Bind("eventName") String eventName);

    @Query("select * from event where short_name = :eventName")
    Optional<Event> findOptionalByShortName(@Bind("eventName") String eventName);

    @Query("select private_key from event where id = :eventId")
    String getPrivateKey(@Bind("eventId") int eventId);

    @Query("select id, org_id from event where short_name = :eventName")
    Optional<EventAndOrganizationId> findOptionalEventAndOrganizationIdByShortName(@Bind("eventName") String eventName);

    @Query("select locales from event where short_name = :eventName")
    Optional<Integer> findLocalesByShortName(@Bind("eventName") String eventName);

    @Query("select * from event order by front_page desc, start_ts asc")
    List<Event> findAll();
   
    @Query("select * from event where org_id in (:organizationIds)")
    List<Event> findByOrganizationIds(@Bind("organizationIds") Collection<Integer> organizationIds);

    @Query("insert into event(short_name, type, display_name, website_url, external_url, website_t_c_url, website_p_p_url, image_url, file_blob_id, location, latitude, longitude, start_ts, end_ts, time_zone, regular_price_cts, currency, available_seats, vat_included, vat, allowed_payment_proxies, private_key, org_id, locales, vat_status, src_price_cts, version, status) " +
            "values(:shortName, :type, :displayName, :websiteUrl, :externalUrl, :termsUrl, :privacyUrl, :imageUrl, :fileBlobId, :location, :latitude, :longitude, :start_ts, :end_ts, :time_zone, 0, :currency, :available_seats, :vat_included, :vat, :paymentProxies, :privateKey, :organizationId, :locales, :vatStatus, :srcPriceCts, :version, :status)")
    @AutoGeneratedKey("id")
    AffectedRowCountAndKey<Integer> insert(@Bind("shortName") String shortName,
                                           @Bind("type") Event.EventType type,
                                           @Bind("displayName") String displayName,
                                           @Bind("websiteUrl") String websiteUrl,
                                           @Bind("externalUrl") String externalUrl,
                                           @Bind("termsUrl") String termsUrl,
                                           @Bind("privacyUrl") String privacyPolicyUrl,
                                           @Bind("imageUrl") String imageUrl,
                                           @Bind("fileBlobId") String fileBlobId,
                                           @Bind("location") String location,
                                           @Bind("latitude") String latitude,
                                           @Bind("longitude") String longitude,
                                           @Bind("start_ts") ZonedDateTime begin,
                                           @Bind("end_ts") ZonedDateTime end,
                                           @Bind("time_zone") String timeZone,
                                           @Bind("currency") String currency,
                                           @Bind("available_seats") int available_seats,
                                           @Bind("vat_included") boolean vat_included,
                                           @Bind("vat") BigDecimal vat,
                                           @Bind("paymentProxies") String allowedPaymentProxies,
                                           @Bind("privateKey") String privateKey,
                                           @Bind("organizationId") int orgId,
                                           @Bind("locales") int locales,
                                           @Bind("vatStatus") PriceContainer.VatStatus vatStatus,
                                           @Bind("srcPriceCts") int srcPriceCts,
                                           @Bind("version") String version,
                                           @Bind("status") Event.Status status);

    @Query("update event set status = :status where id = :id")
    int updateEventStatus(@Bind("id") int id, @Bind("status") Event.Status status);

    @Query("update event set display_name = :displayName, website_url = :websiteUrl, external_url = :externalUrl, website_t_c_url = :termsUrl, website_p_p_url = :privacyUrl, image_url = :imageUrl, file_blob_id = :fileBlobId, " +
            "location = :location, latitude = :latitude, longitude = :longitude, start_ts = :start_ts, " +
            "end_ts = :end_ts, time_zone = :time_zone, org_id = :organizationId, locales = :locales, front_page = :frontPage, category_id = :categoryId where id = :id")
    int updateHeader(@Bind("id") int id,
                     @Bind("displayName") String displayName,
                     @Bind("websiteUrl") String websiteUrl,
                     @Bind("externalUrl") String externalUrl,
                     @Bind("termsUrl") String termsUrl,
                     @Bind("privacyUrl") String privacyPolicyUrl,
                     @Bind("imageUrl") String imageUrl,
                     @Bind("fileBlobId") String fileBlobId,
                     @Bind("location") String location,
                     @Bind("latitude") String latitude,
                     @Bind("longitude") String longitude,
                     @Bind("start_ts") ZonedDateTime begin,
                     @Bind("end_ts") ZonedDateTime end,
                     @Bind("time_zone") String timeZone,
                     @Bind("organizationId") int organizationId,
                     @Bind("locales") int locales,
                     @Bind("frontPage") String frontPage,
                     @Bind("categoryId") Integer categoryId);

    @Query("update event set currency = :currency, available_seats = :available_seats, vat_included = :vat_included, vat = :vat, allowed_payment_proxies = :paymentProxies, vat_status = :vatStatus, src_price_cts = :srcPriceCts where id = :eventId")
    int updatePrices(@Bind("currency") String currency,
                     @Bind("available_seats") int available_seats, @Bind("vat_included") boolean vat_included,
                     @Bind("vat") BigDecimal vat, @Bind("paymentProxies") String allowedPaymentProxies, @Bind("eventId") int eventId,
                     @Bind("vatStatus") PriceContainer.VatStatus vatStatus,
                     @Bind("srcPriceCts") int srcPriceCts);

    @Query("select a.* from event a inner join tickets_reservation on event_id_fk = a.id where tickets_reservation.id = :reservationId")
    Event findByReservationId(@Bind("reservationId") String reservationId);

    @Query("update event set display_name = short_name where id = :eventId and display_name is null")
    int fillDisplayNameIfRequired(@Bind("eventId") int eventId);

    @Query("select count(*) from event where short_name = :shortName")
    Integer countByShortName(@Bind("shortName") String shortName);

    @Query("select id from event where end_ts > :now")
    List<Integer> findAllActiveIds(@Bind("now") ZonedDateTime now);

    @Query("select * from event where end_ts > :now")
    List<Event> findAllActives(@Bind("now") ZonedDateTime now);

    @Query("update event set available_seats = :newValue where id = :eventId")
    int updateAvailableSeats(@Bind("eventId") int eventId, @Bind("newValue") int newValue);

    @Query("select * from event where short_name = :name for update")
    Optional<Event> findOptionalByShortNameForUpdate(@Bind("name") String shortName);

    @Query("select * from events_statistics where id in (:ids)")
    List<EventStatisticView> findStatisticsFor(@Bind("ids") Set<Integer> integers);

    @Query("select * from events_statistics where id = :eventId")
    EventStatisticView findStatisticsFor(@Bind("eventId") int eventId);

    @Query("select available_seats from events_statistics where id = :eventId")
    Integer countExistingTickets(@Bind("eventId") int eventId);

    @Query(value = "update event set status = 'DISABLED' where org_id in (select org_id from j_user_organization where user_id in (:userIds)) returning id", type = QueryType.MODIFYING_WITH_RETURN)
    List<Integer> disableEventsForUsers(@Bind("userIds") Collection<Integer> userIds);

    @Query("select coalesce(sum(final_price_cts),0) from tickets_reservation where event_id_fk = :eventId and status = 'COMPLETE'")
    long getGrossIncome(@Bind("eventId") int eventId);

    @Query("select count(*) as total_attendees, COALESCE(SUM(CASE WHEN status = 'CHECKED_IN' THEN 1 ELSE 0 END), 0) as checked_in, CURRENT_TIMESTAMP as last_update from ticket where event_id = :eventId and status in("+TicketRepository.CONFIRMED+")")
    CheckInStatistics retrieveCheckInStatisticsForEvent(@Bind("eventId") int eventId);


}
