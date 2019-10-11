package de.cyface.data;

/**
 * This class encapsulates some constants required to serialize measurement data into the Cyface binary format.
 *
 * @author Klemens Muthmann
 * @author Armin Schnabel
 * @version 1.4.0
 * @since 2.0.0
 */
public final class ByteSizes {
    /**
     * Since our current API Level does not support <code>Long.Bytes</code>.
     */
    public final static int LONG_BYTES = Long.SIZE / Byte.SIZE;
    /**
     * Since our current API Level does not support <code>Integer.Bytes</code>.
     */
    final static int INT_BYTES = Integer.SIZE / Byte.SIZE;
    /**
     * Since our current API Level does not support <code>Double.Bytes</code>.
     */
    final static int DOUBLE_BYTES = Double.SIZE / Byte.SIZE;
    /**
     * Since our current API Level does not support <code>Short.Bytes</code>.
     */
    public final static int SHORT_BYTES = Short.SIZE / Byte.SIZE;

    /**
     * A constant with the number of bytes for one uncompressed geo location entry in the Cyface binary format.
     */
    public final static int BYTES_IN_ONE_GEO_LOCATION_ENTRY = ByteSizes.LONG_BYTES + 3 * ByteSizes.DOUBLE_BYTES
            + ByteSizes.INT_BYTES;

    public final static int BYTES_IN_ONE_POINT_ENTRY = ByteSizes.LONG_BYTES + 3 * ByteSizes.DOUBLE_BYTES;

    /**
     * A constant with the number of bytes for the fixed length "head" part of one uncompressed event entry in the Cyface event binary format.
     * <p>
     * This part contains information about the variable "tail" of the event entry.
     */
    public final static int BYTES_IN_FIXED_LENGTH_PART_OF_ONE_EVENT_ENTRY = ByteSizes.LONG_BYTES + 2 * ByteSizes.SHORT_BYTES;
}