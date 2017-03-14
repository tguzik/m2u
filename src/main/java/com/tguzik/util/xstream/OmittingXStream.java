package com.tguzik.util.xstream;

import java.util.Objects;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.mapper.Mapper;
import com.thoughtworks.xstream.mapper.MapperWrapper;

public class OmittingXStream extends XStream {
    @Override
    protected MapperWrapper wrapMapper( MapperWrapper next ) {
        return new OmittingMapperWrapper( next );
    }

    private static class OmittingMapperWrapper extends MapperWrapper {
        public OmittingMapperWrapper( Mapper wrapped ) {
            super( wrapped );
        }

        @Override
        public boolean shouldSerializeMember( @SuppressWarnings("rawtypes") Class definedIn, String fieldName ) {
            if ( Objects.equals( Object.class, definedIn ) ) {
                return false;
            }

            return super.shouldSerializeMember( definedIn, fieldName );
        }
    }
}
