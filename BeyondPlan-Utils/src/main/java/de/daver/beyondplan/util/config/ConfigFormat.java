package de.daver.beyondplan.util.config;

import de.daver.beyondplan.util.Parser;
import de.daver.beyondplan.util.Serializer;
import de.daver.beyondplan.util.config.format.JSONFormat;


public interface ConfigFormat extends Serializer<Cache>, Parser<Cache> {

    ConfigFormat JSON = new JSONFormat();
    ConfigFormat XML = null;
    ConfigFormat PROPERTIES = null;
    ConfigFormat YML = null;

    String serializeList(CacheList cacheList);
    CacheList parseList(String string);
}
