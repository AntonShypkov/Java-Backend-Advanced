package com.epam.advanced.java.data_formats.utils;

import com.epam.advanced.java.Message;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.specific.SpecificData;

import static java.lang.System.currentTimeMillis;

public record AvroMessageUtils() {

    public static GenericRecord buildAvroRecord (String message){
        Message avroMessage = new Message();
        avroMessage.setMessage(message);
        avroMessage.setMessageTimpestamp(currentTimeMillis());
        return avroMessage;
    }

    public static Message convertAvroRecord (GenericRecord record){
        return (Message) SpecificData.get().deepCopy(record.getSchema(), record);
    }
}
