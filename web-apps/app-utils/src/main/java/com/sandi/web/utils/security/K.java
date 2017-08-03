package com.sandi.web.utils.security;

// Referenced classes of package com.ai.appframe2.complex.util.e:
//RC2
public final class K {

    private K() throws Exception { }

    /**
     * 加密
     * @param plain
     * @return
     * @throws Exception
     */
    public static String j(String plain) throws Exception {
        RC2 rc2 = new RC2();
        return rc2.encrypt_rc2_array_base64(plain.getBytes(), key);
    }

    /**
     * 解密
     * @param cipher
     * @return
     * @throws Exception
     */
    public static String k(String cipher) throws Exception {
        RC2 rc2 = new RC2();
        return rc2.decrypt_rc2_array_base64(cipher.getBytes(), key);
    }

    /**
     * 获取字符串中的加密部分并解密，如果没有含有{RC2}，则返回原字符串
     * @param cipher
     * @return
     * @throws Exception
     */
    public static String k_s(String cipher) throws Exception {
        String rtn = null;
        if(cipher != null && cipher.lastIndexOf("{RC2}") != -1)
            rtn = k(cipher.substring(5));
        else
            rtn = cipher;
        return rtn;
    }

    public static void main2(String args[]) throws Exception {
        String a = k_s("{RC2}RcAeFXsjJHfGNA==");
        System.out.println(a);
    }

    public static void main(String args[]) throws Exception {
        String a = k_s("{RC2}6z6vqXNSSUg=");
        System.out.println(a);
        String b = j("tiger");
        System.out.println("----jiami---"+b);
        String c = k(b);
        System.out.println(c);
        System.out.println("----"+j("cnB%3Ms5"));
        String m = j("ftpu0319");
        System.out.println(m);
        //X2U+22bpgSk=
    }

    private static byte key[] = {
            97, 105, 95, 110, 106, 95, 114, 100
    };

}

