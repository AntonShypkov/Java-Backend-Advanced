package com.epam.advanced.java.utils;

import com.google.protobuf.Timestamp;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static com.google.protobuf.util.Timestamps.fromMillis;
import static java.lang.System.currentTimeMillis;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DateTimeUtils {

    public static Timestamp nowTime() {
        return fromMillis(currentTimeMillis());
    }

    public static LocalDateTime timestampToLocalDateTime(Timestamp timestamp){
        var instantTime = Instant.ofEpochSecond(timestamp.getSeconds());
        return LocalDateTime.ofInstant(instantTime, ZoneId.systemDefault());
    }

    public static LocalDateTime milliSecondsToLocalDateTime(long millis){
        var instantTime = Instant.ofEpochMilli(millis);
        return LocalDateTime.ofInstant(instantTime, ZoneId.systemDefault());
    }
}
