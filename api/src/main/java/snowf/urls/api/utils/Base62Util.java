package snowf.urls.api.utils;

import java.math.BigInteger;

public class Base62Util {

    public static final String ALPHABET = "7MNoVm0ia3s5gIcLBp48ZSq9Xr6jKhlJkbvRteuWCdYDnfEOGwyHAFP2zTUxQ1";
    public final static BigInteger BASE = BigInteger.valueOf(62);

    public static String encode(BigInteger urlId) {
        if (urlId.equals(BigInteger.ZERO)) {
            return "0";
        }

        StringBuilder sb = new StringBuilder();

        while (urlId.compareTo(BigInteger.ZERO) > 0) {
            BigInteger[] divMod = urlId.divideAndRemainder(BASE);
            urlId = divMod[0];
            sb.append(ALPHABET.charAt(divMod[1].intValue()));
        }

        return sb.reverse().toString();
    }

    public static long decode(String encodedId) {
        if (encodedId == null || encodedId.isEmpty()) {
            throw new IllegalArgumentException("Encoded URL can't be null or empty");
        }

        long result = 0;
        for (char c : encodedId.toCharArray()) {
            int value = ALPHABET.indexOf(c);

            if (value == -1) {
                throw new IllegalArgumentException("Invalid encoded char");
            }

            result = Math.multiplyExact(result, BASE.intValue());
            result = Math.addExact(result, value);
        }

        return result;
    }

}
