package pl.ds.websight.fragments.registry.impl;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import pl.ds.websight.fragments.registry.WebFragmentRegistry;
import pl.ds.websight.rest.framework.RestAction;
import pl.ds.websight.rest.framework.RestActionResult;
import pl.ds.websight.rest.framework.annotations.SlingAction;

import java.util.List;

import static pl.ds.websight.rest.framework.annotations.SlingAction.HttpMethod.GET;

@Component
@SlingAction(GET)
public class GetWebFragmentsRestAction implements RestAction<GetWebFragmentsRestModel, List<String>> {

    @Reference
    private WebFragmentRegistry webFragmentRegistry;

    @Override
    public RestActionResult<List<String>> perform(GetWebFragmentsRestModel model) {
        return RestActionResult.success(webFragmentRegistry.getFragments(model.getKey(), model.getRequest()));
    }
}
