package de.cyface.dataprocessor;

import de.cyface.dataprocessor.impl.CyfaceDataProcessorInMemoryImpl;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static de.cyface.dataprocessor.CyfaceDataProcessorInMemoryTest.printOutData;
import static de.cyface.dataprocessor.CyfaceDataProcessorInMemoryTest.printOutHeaderInfoFromRawBinary;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

public class UnpackTest {

    FileInputStream fileInputStream;
    CyfaceDataProcessorInMemoryImpl proc = null;



    @Test
    public void testDeserializeAccelerationFile() throws AbstractCyfaceDataProcessor.CyfaceCompressedDataProcessorException, IOException {
        final String file = this.getClass().getResource("/16.cyfa").getFile();
        final long length = new File(file).length();
        fileInputStream = new FileInputStream(file);
        try {
            proc = new CyfaceDataProcessorInMemoryImpl(fileInputStream, false);
            proc.setLength(length);
            proc.uncompressAndPrepare();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        printOutHeaderInfoFromRawBinary(proc);

        byte[] individualBytes = proc.getUncompressedBinaryAsArray();
        assertEquals(length, individualBytes.length);

        printOutData(proc);
    }
}