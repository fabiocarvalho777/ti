/**
 * JAXB does not support Maps. It handles Lists great, but
 * Maps are not supported directly. They also require use of a XmlAdapter to
 * map the maps into beans that JAXB can use.
 */
@XmlJavaTypeAdapter(value = com.tamoino.webservices.IdEntryGroupDTOMapAdapter.class, type = java.util.HashMap.class)
package com.tamoino.webservices;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

