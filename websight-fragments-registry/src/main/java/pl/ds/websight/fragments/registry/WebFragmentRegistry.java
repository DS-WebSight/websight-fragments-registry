package pl.ds.websight.fragments.registry;

import org.apache.sling.api.SlingHttpServletRequest;

import java.util.List;

/**
 * Allows to get registered Web Fragments.
 */
public interface WebFragmentRegistry {

    /**
     * Returns registered Web Fragments sorted by ranking (lowest first).
     */
    List<String> getFragments(String key, SlingHttpServletRequest request);
}
