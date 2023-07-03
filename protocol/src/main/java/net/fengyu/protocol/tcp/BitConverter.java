package net.fengyu.protocol.tcp;

/**
 * @author tcl
 */
public class BitConverter {
    // | 表示按位或

    public static int byteArrayToInt(byte[] bytes, int offset) {
        if (bytes == null || bytes.length <= 0) {
            throw new IllegalArgumentException("bytes array is null");
        }
        return fromBytes(bytes[offset], bytes[1 + offset], bytes[2 + offset], bytes[3 + offset]);
    }

    public static int fromBytes(byte b1, byte b2, byte b3, byte b4) {
        return b1 << 24 | (b2 & 0xFF) << 16 | (b3 & 0xFF) << 8 | (b4 & 0xFF);
    }

    public static byte[] intToByteArray(int i) {
        return new byte[]{(byte) (i >> 24), (byte) (i >> 16), (byte) (i >> 8), (byte) i};
    }

    public static long byteArrayToLong(byte[] bytes, int offset) {
        if (bytes == null || bytes.length <= 0) {
            throw new IllegalArgumentException("bytes array is null");
        }
        return fromBytes(bytes[offset], bytes[1 + offset], bytes[2 + offset], bytes[3 + offset], bytes[4 + offset],
                bytes[5 + offset], bytes[6 + offset], bytes[7 + offset]);
    }

    public static long fromBytes(byte b1, byte b2, byte b3, byte b4, byte b5, byte b6, byte b7, byte b8) {
        return (b1 & 0xFFL) << 56 | (b2 & 0xFFL) << 48 | (b3 & 0xFFL) << 40 | (b4 & 0xFFL) << 32 | (b5 & 0xFFL) << 24
                | (b6 & 0xFFL) << 16 | (b7 & 0xFFL) << 8 | (b8 & 0xFFL);
    }

    public static byte[] longToByteArray(long l) {
        byte[] result = new byte[8];
        for (int i = 7; i >= 0; i--) {
            result[i] = (byte) (l & 0xffL);
            l >>= 8;
        }
        return result;
    }

    public static byte[] concat(byte[]... arrays) {
        int length = 0;
        for (byte[] array : arrays) {
            if (null == array) {
                continue;
            }
            length += array.length;
        }
        byte[] result = new byte[length];
        int pos = 0;
        for (byte[] array : arrays) {
            if (null == array) {
                continue;
            }
            System.arraycopy(array, 0, result, pos, array.length);
            pos += array.length;
        }
        return result;
    }

}
