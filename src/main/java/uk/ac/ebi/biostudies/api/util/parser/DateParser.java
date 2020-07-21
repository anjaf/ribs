package uk.ac.ebi.biostudies.api.util.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.jayway.jsonpath.ReadContext;
import org.apache.lucene.document.DateTools;
import uk.ac.ebi.biostudies.api.util.Constants;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static uk.ac.ebi.biostudies.api.util.Constants.NA;
import static uk.ac.ebi.biostudies.api.util.Constants.PUBLIC;
import static uk.ac.ebi.biostudies.api.util.Constants.RELEASE_DATE;

public class DateParser extends AbstractParser {
    @Override
    public String parse(Map<String, Object> valueMap, JsonNode submission, ReadContext jsonPathContext) {
        long releaseDateLong = 0L;
        long creationDateLong = 0L;

        if (submission.has(Constants.Fields.CREATION_TIME)) {
            creationDateLong = Long.parseLong(submission.get(Constants.Fields.CREATION_TIME).asText()) * 1000;
        } else if (submission.has(Constants.Fields.CREATION_TIME_FULL)) {
            creationDateLong = Instant.from(DateTimeFormatter.ISO_INSTANT.parse(submission.get(Constants.Fields.CREATION_TIME_FULL).asText())).toEpochMilli();
        }
        valueMap.put(Constants.Fields.CREATION_TIME, creationDateLong);

        if (submission.has(Constants.Fields.MODIFICATION_TIME)) {
            long modificationTimeLong = Long.parseLong(submission.get(Constants.Fields.MODIFICATION_TIME).asText()) * 1000;
            valueMap.put(Constants.Fields.MODIFICATION_TIME, modificationTimeLong);
            valueMap.put(Constants.Facets.MODIFICATION_YEAR_FACET, DateTools.timeToString(modificationTimeLong, DateTools.Resolution.YEAR));
        } else if (submission.has(Constants.Fields.MODIFICATION_TIME_FULL)) {
            long modificationTimeLong = Instant.from(DateTimeFormatter.ISO_INSTANT.parse(submission.get(Constants.Fields.MODIFICATION_TIME_FULL).asText())).toEpochMilli();
            valueMap.put(Constants.Fields.MODIFICATION_TIME, modificationTimeLong);
            valueMap.put(Constants.Facets.MODIFICATION_YEAR_FACET, DateTools.timeToString(modificationTimeLong, DateTools.Resolution.YEAR));
        }

        if (submission.has(Constants.Fields.RELEASE_TIME)) {
            if (!submission.get(Constants.Fields.RELEASE_TIME).asText().equals("-1")) {
                releaseDateLong = Long.parseLong(submission.get(Constants.Fields.RELEASE_TIME).asText()) * 1000;
            } else if (String.valueOf(valueMap.get(Constants.Fields.ACCESS)).contains(PUBLIC)) {
                releaseDateLong = Long.parseLong(submission.get(Constants.Fields.MODIFICATION_TIME).asText()) * 1000;
            }
        } else if (submission.has(Constants.Fields.RELEASE_TIME_FULL)) {
            if (!submission.get(Constants.Fields.RELEASE_TIME_FULL).asText().equals("-1")) {
                Instant instant = Instant.from(DateTimeFormatter.ISO_INSTANT.parse(submission.get(Constants.Fields.RELEASE_TIME_FULL).asText()));
                releaseDateLong = (instant.getEpochSecond() < 0) ? 0 : instant.toEpochMilli();
            } else if (String.valueOf(valueMap.get(Constants.Fields.ACCESS)).contains(PUBLIC)) {
                releaseDateLong = (long) valueMap.get(Constants.Fields.MODIFICATION_TIME);
            }
        }

        if (releaseDateLong == 0L && !String.valueOf(valueMap.get(Constants.Fields.ACCESS)).contains(PUBLIC)) {
            releaseDateLong = Long.MAX_VALUE;
        }
        valueMap.put(Constants.Fields.RELEASE_TIME, releaseDateLong);
        valueMap.put(RELEASE_DATE, DateTools.timeToString(releaseDateLong, DateTools.Resolution.DAY));
        valueMap.put(Constants.Facets.RELEASED_YEAR_FACET, (releaseDateLong == Long.MAX_VALUE || releaseDateLong == 0) ? NA : DateTools.timeToString(releaseDateLong, DateTools.Resolution.YEAR));
        return "";
    }
}
