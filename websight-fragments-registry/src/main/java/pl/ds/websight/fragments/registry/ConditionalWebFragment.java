package pl.ds.websight.fragments.registry;

import org.apache.sling.api.SlingHttpServletRequest;

/**
 * Extension of WebFragment providing conditional check
 */
public interface ConditionalWebFragment extends WebFragment {

    /**
     * Checks if web fragment is applicable in the context of request. It controls if web fragment
     * is returned by RestAction. It can be used to provide a condition that
     * controls e.g displaying fragment in html.
     * @param request
     * @return true if provided condition is met, false otherwise
     */
    boolean isApplicable(SlingHttpServletRequest request);
}
