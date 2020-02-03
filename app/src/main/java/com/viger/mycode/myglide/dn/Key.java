package com.viger.mycode.myglide.dn;

import java.security.MessageDigest;

public interface Key {
    void updateDiskCacheKey(MessageDigest md);

    byte[] getKeyBytes();
}
