/*
 * Copyright 2019 Cyface GmbH
 *
 * This file is part of the Cyface SDK for Android.
 *
 * The Cyface SDK for Android is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The Cyface SDK for Android is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the Cyface SDK for Android. If not, see <http://www.gnu.org/licenses/>.
 */
package de.cyface.dataprocessor;

import de.cyface.data.Event;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

import static de.cyface.data.ByteSizes.LONG_BYTES;
import static de.cyface.data.ByteSizes.SHORT_BYTES;
import static de.cyface.data.Event.EventType.*;

public class CyfaceEventsDeserializer {

    /**
     * The charset used to parse Strings (e.g. for JSON data)
     */
    public final static String DEFAULT_CHARSET = "UTF-8";

    /**
     * Deserializes a the fixed length part of a single Event from an array of bytes in Cyface events binary format.
     *
     * @param bytes The bytes array to deserialize the event part from.
     * @return partialEvent - Each Event contains 3 entries keyed with "timestamp", "eventType", "value" with the appropriate values.
     * The timestamp is a <code>long</code>, eventType is a <code>short</code> and the value is a <code>String</code>.
     * As the value has a variable length this method only loads the everything but the value and sets this for now to Null.
     */
    protected static FixedLengthEventPart deserializeEventFixedLengthPart(final byte[] bytes) {

        ByteBuffer buffer = ByteBuffer
                .wrap(Arrays.copyOfRange(bytes, 0, LONG_BYTES + SHORT_BYTES + SHORT_BYTES));

        // readout order is important, its a byte buffer dude
        long timestamp = buffer.order(ByteOrder.BIG_ENDIAN).getLong();
        short serializedEventType = buffer.order(ByteOrder.BIG_ENDIAN).getShort();
        short valueBytesLength = buffer.order(ByteOrder.BIG_ENDIAN).getShort();
        final Event.EventType eventType = deserializeEventType(serializedEventType);

        return new FixedLengthEventPart(timestamp, eventType, valueBytesLength);
    }

    /**
     * Deserializes a single event from an already deserialized {@link FixedLengthEventPart} and an array of bytes in Cyface events binary format of the remaining part of the Event.
     *
     * @param partialEvent the already deserialized {@code FixedLengthEventPart} of the Event
     * @param valueBytes        The bytes array to deserialize the remaining variable event part from.
     * @return events - Each Event contains 3 entries keyed with "timestamp", "eventType", "value" with the appropriate values.
     * The timestamp is a <code>long</code>, eventType is a <code>short</code> and the value is a <code>String</code>.
     * This method deserialized the value part.
     */
    protected static Event deserializeEvent(final FixedLengthEventPart partialEvent, final byte[] valueBytes) throws UnsupportedEncodingException {

        final String value = new String(valueBytes, DEFAULT_CHARSET);
        return new Event(1L, partialEvent.eventType, partialEvent.timestamp, value);
    }

    /**
     * Converts the {@param serializedEventType} back to it's actual {@link Event.EventType} as defined in
     * {@code #EVENT_TRANSFER_FILE_FORMAT_VERSION}.
     * <p>
     * <b>Attention:</b> Do not break the compatibility in here without increasing the
     * {@code #EVENT_TRANSFER_FILE_FORMAT_VERSION}.
     *
     * @param serializedEventType the serialized value of the actual {@code Event.EventType}
     * @return the deserialized {@code EventType}
     */
    public static Event.EventType deserializeEventType(final short serializedEventType) {
        switch (serializedEventType) {
            case 1:
                return LIFECYCLE_START;
            case 2:
                return LIFECYCLE_STOP;
            case 3:
                return LIFECYCLE_RESUME;
            case 4:
                return LIFECYCLE_PAUSE;
            case 5:
                return MODALITY_TYPE_CHANGE;
            default:
                throw new IllegalArgumentException("Unknown EventType short representation: " + serializedEventType);
        }
    }

    public static class FixedLengthEventPart {
        long timestamp;
        Event.EventType eventType;
        short valueByteLength;

        public FixedLengthEventPart(long timestamp, Event.EventType eventType, short valueByteLength) {
            this.timestamp = timestamp;
            this.eventType = eventType;
            this.valueByteLength = valueByteLength;
        }
    }
}
