package com.ljq.mydemo.util;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Md5 {
    private int A = 0;
    private int B = 0;
    private int C = 0;
    private int D = 0;
    private static final int S11 = 7;
    private static final int S12 = 12;
    private static final int S13 = 17;
    private static final int S14 = 22;
    private static final int S21 = 5;
    private static final int S22 = 9;
    private static final int S23 = 14;
    private static final int S24 = 20;
    private static final int S31 = 4;
    private static final int S32 = 11;
    private static final int S33 = 16;
    private static final int S34 = 23;
    private static final int S41 = 6;
    private static final int S42 = 10;
    private static final int S43 = 15;
    private static final int S44 = 21;
    private static final String[] strHexDigits = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
    private int[] uintBuffer16 = new int[16];
    private byte[] remainBuffer64 = new byte[64];
    private int remainBufferCount;
    private long totalBytes;

    private int F(int x, int y, int z) {
        return x & y | ~x & z;
    }

    private int G(int x, int y, int z) {
        return x & z | y & ~z;
    }

    private int H(int x, int y, int z) {
        return x ^ y ^ z;
    }

    private int I(int x, int y, int z) {
        return y ^ (x | ~z);
    }

    private int FF(int a, int b, int c, int d, int mj, int s, int ti) {
        a = a + this.F(b, c, d) + mj + ti;
        a = a << s | a >>> 32 - s;
        a += b;
        return a;
    }

    private int GG(int a, int b, int c, int d, int mj, int s, int ti) {
        a = a + this.G(b, c, d) + mj + ti;
        a = a << s | a >>> 32 - s;
        a += b;
        return a;
    }

    private int HH(int a, int b, int c, int d, int mj, int s, int ti) {
        a = a + this.H(b, c, d) + mj + ti;
        a = a << s | a >>> 32 - s;
        a += b;
        return a;
    }

    private int II(int a, int b, int c, int d, int mj, int s, int ti) {
        a = a + this.I(b, c, d) + mj + ti;
        a = a << s | a >>> 32 - s;
        a += b;
        return a;
    }

    private byte[] intToBytes(int n) {
        byte[] b = new byte[4];

        for(int i = 0; i < 4; ++i) {
            b[i] = (byte)(n >>> i * 8 & 255);
        }

        return b;
    }

    public Md5() {
        this.Initialize();
    }

    public Md5(int nSpecialCode) {
        this.Initialize();
        this.D = nSpecialCode;
    }

    private void Initialize() {
        this.A = 1732584193;
        this.B = -271733879;
        this.C = -1732584194;
        this.D = 271733878;
        this.totalBytes = 0L;
        this.remainBufferCount = 0;
    }

    private void InternalTransformBlock64(byte[] inputBuffer, int inputOffset) {
        int a;
        for(a = 0; a < this.uintBuffer16.length; ++a) {
            this.uintBuffer16[a] = inputBuffer[a * 4 + inputOffset] & 255 | (inputBuffer[a * 4 + inputOffset + 1] & 255) << 8 | (inputBuffer[a * 4 + inputOffset + 2] & 255) << 16 | (inputBuffer[a * 4 + inputOffset + 3] & 255) << 24;
        }

        a = this.A;
        int b = this.B;
        int c = this.C;
        int d = this.D;
        a = this.FF(a, b, c, d, this.uintBuffer16[0], 7, -680876943);
        d = this.FF(d, a, b, c, this.uintBuffer16[1], 12, -389564590);
        c = this.FF(c, d, a, b, this.uintBuffer16[2], 17, 606105811);
        b = this.FF(b, c, d, a, this.uintBuffer16[3], 22, -1044525340);
        a = this.FF(a, b, c, d, this.uintBuffer16[4], 7, -176418907);
        d = this.FF(d, a, b, c, this.uintBuffer16[5], 12, 1200080422);
        c = this.FF(c, d, a, b, this.uintBuffer16[6], 17, -1473231337);
        b = this.FF(b, c, d, a, this.uintBuffer16[7], 22, -45705976);
        a = this.FF(a, b, c, d, this.uintBuffer16[8], 7, 1770035417);
        d = this.FF(d, a, b, c, this.uintBuffer16[9], 12, -1958414422);
        c = this.FF(c, d, a, b, this.uintBuffer16[10], 17, -42053);
        b = this.FF(b, c, d, a, this.uintBuffer16[11], 22, -1990404164);
        a = this.FF(a, b, c, d, this.uintBuffer16[12], 7, 1804603693);
        d = this.FF(d, a, b, c, this.uintBuffer16[13], 12, -40341090);
        c = this.FF(c, d, a, b, this.uintBuffer16[14], 17, -1502002289);
        b = this.FF(b, c, d, a, this.uintBuffer16[15], 22, 1236535328);
        a = this.GG(a, b, c, d, this.uintBuffer16[1], 5, -165796510);
        d = this.GG(d, a, b, c, this.uintBuffer16[6], 9, -1069501632);
        c = this.GG(c, d, a, b, this.uintBuffer16[11], 14, 643717713);
        b = this.GG(b, c, d, a, this.uintBuffer16[0], 20, -373897302);
        a = this.GG(a, b, c, d, this.uintBuffer16[5], 5, -701558691);
        d = this.GG(d, a, b, c, this.uintBuffer16[10], 9, 38016083);
        c = this.GG(c, d, a, b, this.uintBuffer16[15], 14, -660478335);
        b = this.GG(b, c, d, a, this.uintBuffer16[4], 20, -405537848);
        a = this.GG(a, b, c, d, this.uintBuffer16[9], 5, 568446438);
        d = this.GG(d, a, b, c, this.uintBuffer16[14], 9, -1019803690);
        c = this.GG(c, d, a, b, this.uintBuffer16[3], 14, -187363961);
        b = this.GG(b, c, d, a, this.uintBuffer16[8], 20, 1163531501);
        a = this.GG(a, b, c, d, this.uintBuffer16[13], 5, -1444681467);
        d = this.GG(d, a, b, c, this.uintBuffer16[2], 9, -51403784);
        c = this.GG(c, d, a, b, this.uintBuffer16[7], 14, 1735328473);
        b = this.GG(b, c, d, a, this.uintBuffer16[12], 20, -1926607734);
        a = this.HH(a, b, c, d, this.uintBuffer16[5], 4, -378558);
        d = this.HH(d, a, b, c, this.uintBuffer16[8], 11, -2022574463);
        c = this.HH(c, d, a, b, this.uintBuffer16[11], 16, 1839030562);
        b = this.HH(b, c, d, a, this.uintBuffer16[14], 23, -35309556);
        a = this.HH(a, b, c, d, this.uintBuffer16[1], 4, -1530992060);
        d = this.HH(d, a, b, c, this.uintBuffer16[4], 11, 1272893353);
        c = this.HH(c, d, a, b, this.uintBuffer16[7], 16, -155497632);
        b = this.HH(b, c, d, a, this.uintBuffer16[10], 23, -1094730640);
        a = this.HH(a, b, c, d, this.uintBuffer16[13], 4, 681279174);
        d = this.HH(d, a, b, c, this.uintBuffer16[0], 11, -358537222);
        c = this.HH(c, d, a, b, this.uintBuffer16[3], 16, -722521979);
        b = this.HH(b, c, d, a, this.uintBuffer16[6], 23, 76029189);
        a = this.HH(a, b, c, d, this.uintBuffer16[9], 4, -640364487);
        d = this.HH(d, a, b, c, this.uintBuffer16[12], 11, -421815835);
        c = this.HH(c, d, a, b, this.uintBuffer16[15], 16, 530742520);
        b = this.HH(b, c, d, a, this.uintBuffer16[2], 23, -995338651);
        a = this.II(a, b, c, d, this.uintBuffer16[0], 6, -198630844);
        d = this.II(d, a, b, c, this.uintBuffer16[7], 10, 1126891415);
        c = this.II(c, d, a, b, this.uintBuffer16[14], 15, -1416354905);
        b = this.II(b, c, d, a, this.uintBuffer16[5], 21, -57434055);
        a = this.II(a, b, c, d, this.uintBuffer16[12], 6, 1700485571);
        d = this.II(d, a, b, c, this.uintBuffer16[3], 10, -1894986606);
        c = this.II(c, d, a, b, this.uintBuffer16[10], 15, -1051523);
        b = this.II(b, c, d, a, this.uintBuffer16[1], 21, -2054922799);
        a = this.II(a, b, c, d, this.uintBuffer16[8], 6, 1873313359);
        d = this.II(d, a, b, c, this.uintBuffer16[15], 10, -30611744);
        c = this.II(c, d, a, b, this.uintBuffer16[6], 15, -1560198380);
        b = this.II(b, c, d, a, this.uintBuffer16[13], 21, 1309151649);
        a = this.II(a, b, c, d, this.uintBuffer16[4], 6, -145523070);
        d = this.II(d, a, b, c, this.uintBuffer16[11], 10, -1120210379);
        c = this.II(c, d, a, b, this.uintBuffer16[2], 15, 718787259);
        b = this.II(b, c, d, a, this.uintBuffer16[9], 21, -343485551);
        this.A += a;
        this.B += b;
        this.C += c;
        this.D += d;
        this.totalBytes += 64L;
    }

    private int TransformBlock(byte[] inputBuffer, int inputOffset, int inputCount, byte[] outputBuffer, int outputOffset) {
        int inputOffsetEnd;
        if (this.remainBufferCount > 0) {
            inputOffsetEnd = Math.min(64 - this.remainBufferCount, inputCount);
            System.arraycopy(inputBuffer, inputOffset, this.remainBuffer64, this.remainBufferCount, inputOffsetEnd);
            inputOffset += inputOffsetEnd;
            inputCount -= inputOffsetEnd;
            this.remainBufferCount += inputOffsetEnd;
        }

        if (this.remainBufferCount >= 64) {
            this.InternalTransformBlock64(this.remainBuffer64, 0);
            this.remainBufferCount = 0;
        }

        if (inputCount > 0) {
            inputOffsetEnd = inputOffset + inputCount;
            if (inputOffset < inputOffsetEnd) {
                for(int i = inputOffset; i <= inputOffsetEnd - 64; i += 64) {
                    this.InternalTransformBlock64(inputBuffer, i);
                }
            }

            this.remainBufferCount = inputCount % 64;
            if (this.remainBufferCount > 0) {
                System.arraycopy(inputBuffer, inputOffsetEnd - this.remainBufferCount, this.remainBuffer64, 0, this.remainBufferCount);
            }
        }

        if (outputBuffer != null && outputBuffer.length >= 16) {
            System.arraycopy(this.intToBytes(this.A), 0, outputBuffer, outputOffset, 4);
            System.arraycopy(this.intToBytes(this.B), 0, outputBuffer, outputOffset + 4, 4);
            System.arraycopy(this.intToBytes(this.C), 0, outputBuffer, outputOffset + 8, 4);
            System.arraycopy(this.intToBytes(this.D), 0, outputBuffer, outputOffset + 12, 4);
            return 16;
        } else {
            return 0;
        }
    }

    private byte[] HashFinal() {
        byte[] returnBuffer = new byte[16];
        byte[] finalBuffer;
        if (this.remainBufferCount < 56) {
            finalBuffer = this.remainBuffer64;
            finalBuffer[this.remainBufferCount] = -128;

            for(int i = this.remainBufferCount + 1; i < 64 - this.remainBufferCount - 8; ++i) {
                finalBuffer[i] = 0;
            }
        } else {
            finalBuffer = new byte[128];
            System.arraycopy(this.remainBuffer64, 0, finalBuffer, 0, this.remainBufferCount);
            finalBuffer[this.remainBufferCount] = -128;
        }

        this.totalBytes += (long)this.remainBufferCount;
        long totalBits = this.totalBytes * 8L;
        int i = finalBuffer.length - 1;
        finalBuffer[i--] = (byte)((int)(totalBits >>> 56 & 255L));
        finalBuffer[i--] = (byte)((int)(totalBits >>> 48 & 255L));
        finalBuffer[i--] = (byte)((int)(totalBits >>> 40 & 255L));
        finalBuffer[i--] = (byte)((int)(totalBits >>> 32 & 255L));
        finalBuffer[i--] = (byte)((int)(totalBits >>> 24 & 255L));
        finalBuffer[i--] = (byte)((int)(totalBits >>> 16 & 255L));
        finalBuffer[i--] = (byte)((int)(totalBits >>> 8 & 255L));
        finalBuffer[i--] = (byte)((int)(totalBits & 255L));

        for(int idx = 0; idx < finalBuffer.length; idx += 64) {
            this.InternalTransformBlock64(finalBuffer, idx);
        }

        System.arraycopy(this.intToBytes(this.A), 0, returnBuffer, 0, 4);
        System.arraycopy(this.intToBytes(this.B), 0, returnBuffer, 4, 4);
        System.arraycopy(this.intToBytes(this.C), 0, returnBuffer, 8, 4);
        System.arraycopy(this.intToBytes(this.D), 0, returnBuffer, 12, 4);
        return returnBuffer;
    }

    private byte[] ComputeHash(byte[] data) {
        this.Initialize();
        this.TransformBlock(data, 0, data.length, (byte[])null, 0);
        return this.HashFinal();
    }

    private byte[] ComputeHash(String filePathName, long fileLength) {
        if (filePathName != null && FileUtils.isExist(filePathName)) {
            FileInputStream fin = null;

            Object var6;
            try {
                this.Initialize();
                byte[] buffer = new byte[1024];
                //int readCount = false;
                long size = 0L;
                fin = new FileInputStream(filePathName);

                while(true) {
                    int readCount;
                    if ((readCount = fin.read(buffer)) > 0) {
                        this.TransformBlock(buffer, 0, readCount, (byte[])null, 0);
                        size += (long)readCount;
                        if (size < fileLength) {
                            continue;
                        }
                    }

                    byte[] var9 = this.HashFinal();
                    return var9;
                }
            } catch (Exception var19) {
                var19.printStackTrace();
                var6 = null;
            } finally {
                try {
                    if (fin != null) {
                        fin.close();
                    }
                } catch (IOException var18) {
                    var18.printStackTrace();
                }

            }

            return (byte[])var6;
        } else {
            return null;
        }
    }

    private byte[] ComputeHashQuick(String filePathName, int blockCount, int blockLength) {
        if (filePathName != null && FileUtils.isExist(filePathName)) {
            if (blockCount <= 0) {
                return this.ComputeHash(filePathName, FileUtils.getFileLength(filePathName));
            } else if (blockLength <= 0) {
                return null;
            } else {
                RandomAccessFile accessWriteFile = null;

                Object var6;
                try {
                    this.Initialize();
                    byte[] buffer = new byte[blockLength];
                    long fileLen = FileUtils.getFileLength(filePathName);
                    accessWriteFile = new RandomAccessFile(filePathName, "r");
                    accessWriteFile.seek(0L);
                    int readCount = accessWriteFile.read(buffer, 0, blockLength);
                    this.TransformBlock(buffer, 0, readCount, (byte[])null, 0);
                    if (blockCount > 1) {
                        if (blockCount > 2) {
                            long startPos = 0L;
                            long blockOffset = fileLen / (long)(blockCount - 1);

                            for(int i = 0; i < blockCount - 2; ++i) {
                                accessWriteFile.seek(0L);
                                startPos += blockOffset;
                                accessWriteFile.seek(startPos);
                                readCount = accessWriteFile.read(buffer, 0, blockLength);
                                this.TransformBlock(buffer, 0, readCount, (byte[])null, 0);
                                startPos += (long)readCount;
                                if (startPos >= fileLen) {
                                    break;
                                }
                            }
                        }

                        if (fileLen > (long)blockLength) {
                            accessWriteFile.seek(0L);
                            accessWriteFile.seek(fileLen - (long)blockLength);
                            readCount = accessWriteFile.read(buffer, 0, blockLength);
                            this.TransformBlock(buffer, 0, readCount, (byte[])null, 0);
                        }
                    }

                    byte[] var25 = this.HashFinal();
                    return var25;
                } catch (Exception var22) {
                    var22.printStackTrace();
                    var6 = null;
                } finally {
                    try {
                        if (accessWriteFile != null) {
                            accessWriteFile.close();
                            accessWriteFile = null;
                        }
                    } catch (IOException var21) {
                        var21.printStackTrace();
                    }

                }

                return (byte[])var6;
            }
        } else {
            return null;
        }
    }

    private String Convert2String(byte[] md5Data) {
        if (md5Data == null) {
            return "0000";
        } else {
            StringBuffer sBuffer = new StringBuffer();

            for(int i = 0; i < md5Data.length; ++i) {
                int tmpByte = md5Data[i];
                if (tmpByte < 0) {
                    tmpByte += 256;
                }

                String tmpStr = strHexDigits[tmpByte / 16] + strHexDigits[tmpByte % 16];
                sBuffer.append(tmpStr);
            }

            return sBuffer.toString();
        }
    }

    public String ComputeMd5(String strFilePathName) {
        byte[] md5Value = this.ComputeHash(strFilePathName, FileUtils.getFileLength(strFilePathName));
        return this.Convert2String(md5Value);
    }

    public String ComputeMd5(byte[] data) {
        byte[] md5Value = this.ComputeHash(data);
        return this.Convert2String(md5Value);
    }

    public String ComputeFileMd5(String strFilePathName) {
        String retValue = null;
        long fileLen = FileUtils.getFileLength(strFilePathName);
        if (fileLen > 3072L) {
            retValue = this.Convert2String(this.ComputeHashQuick(strFilePathName, 3, 1024));
        } else {
            retValue = this.Convert2String(this.ComputeHash(strFilePathName, fileLen));
        }

        return retValue;
    }
}
