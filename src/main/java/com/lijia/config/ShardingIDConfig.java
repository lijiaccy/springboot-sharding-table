package com.lijia.config;

    import com.dangdang.ddframe.rdb.sharding.keygen.DefaultKeyGenerator;
    import com.dangdang.ddframe.rdb.sharding.keygen.KeyGenerator;

    public class ShardingIDConfig implements KeyGenerator{
        @Override
        public Number generateKey() {
            DefaultKeyGenerator defaultKeyGenerator = new DefaultKeyGenerator();
            return defaultKeyGenerator.generateKey();
        }
    }
