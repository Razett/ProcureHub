package com.glkids.procurehub.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.hibernate.proxy.HibernateProxy;

import java.io.IOException;

public class HibernateProxySerializer extends StdSerializer<HibernateProxy> {

    public HibernateProxySerializer() {
        super(HibernateProxy.class);
    }

    @Override
    public void serialize(HibernateProxy value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (value == null) {
            provider.defaultSerializeNull(gen);
            return;
        }
        provider.defaultSerializeValue(value.getHibernateLazyInitializer().getImplementation(), gen);
    }
}
