package com.person.lsj.stock.utils;

public class TokenUtils {


    public static void main(String[] args) {
        generateToken();
    }

    public static String generateToken() {
        return generateToken("1718862365");
    }

    public static String generateToken(String tokenServerTime) {
        S s = new S();
        s.S[1] = Integer.parseInt(tokenServerTime);
        Integer[] buffer = toBuffer(s);
        String token = encode(buffer);
        return token;
    }

    public static Integer[] toBuffer(S input) {

        Integer[] result = new Integer[43];
        for (int s = -1, v = 0, f = input.S.length; v < f; v++) {
            int[] u = input.base_field;
            for (int l = input.S[v], p = u[v], d = s += p; --p >= 0; ) {
                result[d] = l & 255;
                --d;
                l >>= 8;
            }
        }
        return result;
    }

    public static String encode(Integer[] input) {
        String result = "";
        int r = functionX(input);
        int[] e = new int[2 + input.length];
        e[0] = 3;
        e[1] = r;
        functionG(input, +0, e, 2, r);
        String token = functionW(e);
        return token;
    }

    public static Integer functionX(Integer[] input) {
        int t = 0;
        int e = 0;
        for (int i = 0; i < input.length; i++) {
            e = (e << 5) - e + input[i];
        }
        return Math.toIntExact(e & 255);
    }

    public static void functionG(Integer[] input, int a, int[] result, int i, int u) {
        for (; a < input.length; ) {
            result[i++] = input[a++] ^ u & 255;
            u = ~(u * 131);
        }
    }

    public static String functionW(int[] n) {
        StringBuffer buffer = new StringBuffer();
        String m = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_";
        for (int i = 0; i < n.length; ) {
            int l = n[i++] << 16 | n[i++] << 8 | n[i++];
            buffer.append(m.charAt(l >> 18));
            buffer.append(m.charAt(l >> 12 & 63));
            buffer.append(m.charAt(l >> 6 & 63));
            buffer.append(m.charAt(l & 63));
        }

        return buffer.toString();
    }

    static class S {
        public int[] S;
        public int[] base_field;

        public S() {
            S = new int[18];
            S[0] = 338364971;
            S[1] = 1718862365;
            S[2] = 1718783865;
            S[3] = 513001802;
            S[4] = 1;
            S[5] = 10;
            S[6] = 5;
            S[7] = 12;
            S[13] = 3748;
            S[16] = 17;
            S[17] = 3;
            base_field = new int[]{4, 4, 4, 4, 1, 1, 1, 3, 2, 2, 2, 2, 2, 2, 2, 4, 2, 1};
        }

    }
}
