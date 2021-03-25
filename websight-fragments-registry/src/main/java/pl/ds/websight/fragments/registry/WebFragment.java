package pl.ds.websight.fragments.registry;

/**
 * Implement to register Web Fragment.
 */
public interface WebFragment {

    /**
     * Returns key string used to query Web Fragments.
     */
    String getKey();

    /**
     * Returns fragment.
     */
    String getFragment();

    /**
     * Return ranking to order the Web Fragment in query by key results. Lowest first.<br>
     * If priority is duplicated order is random (depends on registration order).
     */
    int getRanking();
}
