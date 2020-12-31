package pl.ds.websight.fragments.registry.impl;

import org.apache.sling.api.SlingHttpServletRequest;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.ds.websight.fragments.registry.ConditionalWebFragment;
import pl.ds.websight.fragments.registry.WebFragment;
import pl.ds.websight.fragments.registry.WebFragmentRegistry;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

@Component(immediate = true)
public class WebFragmentRegistryImpl implements WebFragmentRegistry {

    private static final Logger LOG = LoggerFactory.getLogger(WebFragmentRegistryImpl.class);

    private Map<String, List<WebFragment>> webFragmentsByKey = new HashMap<>();

    @Reference(
            service = WebFragment.class,
            cardinality = ReferenceCardinality.MULTIPLE,
            policy = ReferencePolicy.DYNAMIC)
    private synchronized void bindWebFragment(WebFragment webFragment) {
        log("Binding Web Fragment", webFragment);
        List<WebFragment> webFragments = webFragmentsByKey.computeIfAbsent(webFragment.getKey(), k -> new ArrayList<>());
        webFragments.add(webFragment);
        webFragments.sort(Comparator.comparingInt(WebFragment::getRanking));
    }

    private synchronized void unbindWebFragment(WebFragment webFragment) {
        log("Unbinding Web Fragment", webFragment);
        webFragmentsByKey.get(webFragment.getKey()).remove(webFragment);
    }

    public synchronized List<String> getFragments(String key, SlingHttpServletRequest request) {
        return webFragmentsByKey.getOrDefault(key, emptyList()).stream()
                .filter(webFragment -> isApplicable(webFragment, request))
                .map(WebFragment::getFragment)
                .collect(Collectors.toList());
    }

    private boolean isApplicable(WebFragment webFragment, SlingHttpServletRequest request) {
        if (webFragment instanceof ConditionalWebFragment) {
            return ((ConditionalWebFragment) webFragment).isApplicable(request);
        }
        return true;
    }

    private void log(String message, WebFragment webFragment) {
        String logFormat = message + " type = {}, key = {}, fragment = {}, ranking = {}";
        LOG.info(logFormat, webFragment.getClass().getName(), webFragment.getKey(), webFragment.getFragment(), webFragment.getRanking());
    }
}
