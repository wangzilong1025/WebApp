package com.sandi.web.utils.security;

/**
 * Created by lijie9 on 2016/1/16.
 */
import java.security.InvalidKeyException;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public final class RC2 {

    public RC2() {
        sKey = new int[64];
    }

    private void makeKey(byte userkey[])
            throws InvalidKeyException {
        if (userkey == null)
            throw new InvalidKeyException("用户名为空");
        int len = userkey.length;
        if (len > 128)
            throw new InvalidKeyException("用户名长度大于128");
        int sk[] = new int[128];
        for (int i = 0; i < len; i++)
            sk[i] = userkey[i];

        for (int i = len; i < 128; i++)
            sk[i] = S_BOX[sk[i - len] + sk[i - 1] & 255];

        len = 128;
        sk[128 - len] = S_BOX[sk[128 - len] & 255];
        for (int i = 127 - len; i >= 0; i--)
            sk[i] = S_BOX[sk[i + len] ^ sk[i + 1]];

        for (int i = 63; i >= 0; i--)
            sKey[i] = (sk[i * 2 + 1] << 8 | sk[i * 2]) & 65535;

    }

    private void blockEncrypt(byte in[], int inOff, byte out[], int outOff) {
        int w0 = in[inOff++] & 255 | (in[inOff++] & 255) << 8;
        int w1 = in[inOff++] & 255 | (in[inOff++] & 255) << 8;
        int w2 = in[inOff++] & 255 | (in[inOff++] & 255) << 8;
        int w3 = in[inOff++] & 255 | (in[inOff] & 255) << 8;
        int j = 0;
        for (int i = 0; i < 16; i++) {
            w0 = w0 + (w1 & ~w3) + (w2 & w3) + sKey[j++] & 65535;
            w0 = w0 << 1 | w0 >>> 15;
            w1 = w1 + (w2 & ~w0) + (w3 & w0) + sKey[j++] & 65535;
            w1 = w1 << 2 | w1 >>> 14;
            w2 = w2 + (w3 & ~w1) + (w0 & w1) + sKey[j++] & 65535;
            w2 = w2 << 3 | w2 >>> 13;
            w3 = w3 + (w0 & ~w2) + (w1 & w2) + sKey[j++] & 65535;
            w3 = w3 << 5 | w3 >>> 11;
            if (i == 4 || i == 10) {
                w0 += sKey[w3 & 63];
                w1 += sKey[w0 & 63];
                w2 += sKey[w1 & 63];
                w3 += sKey[w2 & 63];
            }
        }

        out[outOff++] = (byte) w0;
        out[outOff++] = (byte) (w0 >>> 8);
        out[outOff++] = (byte) w1;
        out[outOff++] = (byte) (w1 >>> 8);
        out[outOff++] = (byte) w2;
        out[outOff++] = (byte) (w2 >>> 8);
        out[outOff++] = (byte) w3;
        out[outOff] = (byte) (w3 >>> 8);
    }

    private void blockDecrypt(byte in[], int inOff, byte out[], int outOff) {
        int w0 = in[inOff + 0] & 255 | (in[inOff + 1] & 255) << 8;
        int w1 = in[inOff + 2] & 255 | (in[inOff + 3] & 255) << 8;
        int w2 = in[inOff + 4] & 255 | (in[inOff + 5] & 255) << 8;
        int w3 = in[inOff + 6] & 255 | (in[inOff + 7] & 255) << 8;
        int j = 63;
        for (int i = 15; i >= 0; i--) {
            w3 = (w3 >>> 5 | w3 << 11) & 65535;
            w3 = w3 - (w0 & ~w2) - (w1 & w2) - sKey[j--] & 65535;
            w2 = (w2 >>> 3 | w2 << 13) & 65535;
            w2 = w2 - (w3 & ~w1) - (w0 & w1) - sKey[j--] & 65535;
            w1 = (w1 >>> 2 | w1 << 14) & 65535;
            w1 = w1 - (w2 & ~w0) - (w3 & w0) - sKey[j--] & 65535;
            w0 = (w0 >>> 1 | w0 << 15) & 65535;
            w0 = w0 - (w1 & ~w3) - (w2 & w3) - sKey[j--] & 65535;
            if (i == 11 || i == 5) {
                w3 = w3 - sKey[w2 & 63] & 65535;
                w2 = w2 - sKey[w1 & 63] & 65535;
                w1 = w1 - sKey[w0 & 63] & 65535;
                w0 = w0 - sKey[w3 & 63] & 65535;
            }
        }

        out[outOff++] = (byte) w0;
        out[outOff++] = (byte) (w0 >>> 8);
        out[outOff++] = (byte) w1;
        out[outOff++] = (byte) (w1 >>> 8);
        out[outOff++] = (byte) w2;
        out[outOff++] = (byte) (w2 >>> 8);
        out[outOff++] = (byte) w3;
        out[outOff] = (byte) (w3 >>> 8);
    }

    private byte rc2_encrypt_chr(byte plain) {
        int src = plain;
        for (int i = 0; i < 64; i++) {
            src += sKey[i] >> 8 & 255;
            src ^= sKey[i] & 255;
            src = (src << 5 & 224) + (src >> 3 & 31);
            if (i % 16 == 4 || (i % 16 == 5) | (i % 16 == 14)) {
                src ^= sKey[i] >> 8 & 255;
                src -= sKey[i] & 255;
            }
        }

        return (byte) src;
    }

    private byte rc2_decrypt_chr(byte ciper) {
        int src = ciper;
        for (int i = 63; i >= 0; i--) {
            if (i % 16 == 4 || (i % 16 == 5) | (i % 16 == 14)) {
                src += sKey[i] & 255;
                src ^= sKey[i] >> 8 & 255;
            }
            src = (src << 3 & 248) + (src >> 5 & 7);
            src ^= sKey[i] & 255;
            src -= sKey[i] >> 8 & 255;
        }

        return (byte) src;
    }

    public byte[] encrypt_rc2_array(byte plain[], byte key[])
            throws Exception {
        byte in[] = new byte[8];
        byte out[] = new byte[8];
        byte p[] = null;
        if (plain == null || plain.length == 0)
            throw new Exception("明文为空");
        makeKey(key);
        int j = plain.length / 8;
        int i;
        for (i = 0; i < j; i++) {
            for (int k = 0; k < 8; k++)
                in[k] = plain[i * 8 + k];

            blockEncrypt(in, 0, out, 0);
            for (int k = 0; k < 8; k++)
                plain[i * 8 + k] = out[k];

        }

        int leftLen = plain.length - i * 8;
        p = new byte[leftLen];
        for (int d = 0; d < leftLen; d++)
            p[d] = plain[i * 8 + d];

        if (p != null && p.length > 0) {
            for (int m = 0; m < leftLen; m++) {
                byte chr_in = p[m];
                p[m] = rc2_encrypt_chr(chr_in);
            }

        }
        byte rtn[] = new byte[plain.length];
        for (int g = 0; g < i * 8; g++)
            rtn[g] = plain[g];

        for (int g = 0; g < p.length; g++)
            rtn[i * 8 + g] = p[g];

        return rtn;
    }

    public byte[] decrypt_rc2_array(byte cipher[], byte key[])
            throws Exception {
        byte in[] = new byte[8];
        byte out[] = new byte[8];
        byte p[] = null;
        if (cipher == null || cipher.length == 0)
            throw new Exception("密文为空");
        makeKey(key);
        int j = cipher.length / 8;
        int i;
        for (i = 0; i < j; i++) {
            for (int k = 0; k < 8; k++)
                in[k] = cipher[i * 8 + k];

            blockDecrypt(in, 0, out, 0);
            for (int k = 0; k < 8; k++)
                cipher[i * 8 + k] = out[k];

        }

        int leftLen = cipher.length - i * 8;
        p = new byte[leftLen];
        for (int d = 0; d < leftLen; d++)
            p[d] = cipher[i * 8 + d];

        if (p != null && p.length > 0) {
            for (int m = 0; m < leftLen; m++) {
                byte chr_in = p[m];
                p[m] = rc2_decrypt_chr(chr_in);
            }

        }
        byte rtn[] = new byte[cipher.length];
        for (int g = 0; g < i * 8; g++)
            rtn[g] = cipher[g];

        for (int g = 0; g < p.length; g++)
            rtn[i * 8 + g] = p[g];

        return rtn;
    }

    public String encrypt_rc2_array_base64(byte plain[], byte key[])
            throws Exception {
        byte rtn[] = encrypt_rc2_array(plain, key);
        return (new BASE64Encoder()).encode(rtn);
    }

    public String decrypt_rc2_array_base64(byte cipher[], byte key[])
            throws Exception {
        byte aa[] = (new BASE64Decoder()).decodeBuffer(new String(cipher));
        byte rtn[] = decrypt_rc2_array(aa, key);
        return new String(rtn);
    }

    private static final int S_BOX[] = {
            217, 120, 249, 196, 25, 221, 181, 237, 40, 233,
            253, 121, 74, 160, 216, 157, 198, 126, 55, 131,
            43, 118, 83, 142, 98, 76, 100, 136, 68, 139,
            251, 162, 23, 154, 89, 245, 135, 179, 79, 19,
            97, 69, 109, 141, 9, 129, 125, 50, 189, 143,
            64, 235, 134, 183, 123, 11, 240, 149, 33, 34,
            92, 107, 78, 130, 84, 214, 101, 147, 206, 96,
            178, 28, 115, 86, 192, 20, 167, 140, 241, 220,
            18, 117, 202, 31, 59, 190, 228, 209, 66, 61,
            212, 48, 163, 60, 182, 38, 111, 191, 14, 218,
            70, 105, 7, 87, 39, 242, 29, 155, 188, 148,
            67, 3, 248, 17, 199, 246, 144, 239, 62, 231,
            6, 195, 213, 47, 200, 102, 30, 215, 8, 232,
            234, 222, 128, 82, 238, 247, 132, 170, 114, 172,
            53, 77, 106, 42, 150, 26, 210, 113, 90, 21,
            73, 116, 75, 159, 208, 94, 4, 24, 164, 236,
            194, 224, 65, 110, 15, 81, 203, 204, 36, 145,
            175, 80, 161, 244, 112, 57, 153, 124, 58, 133,
            35, 184, 180, 122, 252, 2, 54, 91, 37, 85,
            151, 49, 45, 93, 250, 152, 227, 138, 146, 174,
            5, 223, 41, 16, 103, 108, 186, 201, 211, 0,
            230, 207, 225, 158, 168, 44, 99, 22, 1, 63,
            88, 226, 137, 169, 13, 56, 52, 27, 171, 51,
            255, 176, 187, 72, 12, 95, 185, 177, 205, 46,
            197, 243, 219, 71, 229, 165, 156, 119, 10, 166,
            32, 104, 254, 127, 193, 173
    };
    private int sKey[];

}

