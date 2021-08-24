package com.camel_rabbitmq.services.smpp;

import com.cloudhopper.commons.util.HexUtil;
import org.jboss.netty.buffer.BigEndianHeapChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffer;

public class BufferHelper {
    static public ChannelBuffer createBuffer(byte[] bytes) throws Exception {
        return new BigEndianHeapChannelBuffer(bytes);
    }

    static public ChannelBuffer createBuffer(String hexString) throws Exception {
        return createBuffer(HexUtil.toByteArray(hexString));
    }

    static public byte[] createByteArray(ChannelBuffer buffer) throws Exception {
        byte[] bytes = new byte[buffer.readableBytes()];
        // temporarily read bytes from the buffer
        buffer.getBytes(buffer.readerIndex(), bytes);
        return bytes;
    }

    static public String createHexString(ChannelBuffer buffer) throws Exception {
        return HexUtil.toHexString(createByteArray(buffer));

    }
}
