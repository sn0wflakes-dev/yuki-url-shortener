package snowf.urls.api;

import org.junit.jupiter.api.Test;
import snowf.urls.api.utils.Base62Util;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class Base62Tests {

    private final BigInteger URL_ID = BigInteger.valueOf(100302);

    @Test
    public void encodeAndDecodeUrlId() {
        String encodedUrlId = Base62Util.encode(URL_ID);
        long decodedUrlId = Base62Util.decode(encodedUrlId);

        // Encoded id shouldn't null
        assertNotNull(encodedUrlId);

        // Decoded id should same as raw URL_ID
        assertEquals(URL_ID.longValue(), decodedUrlId);
    }
}
