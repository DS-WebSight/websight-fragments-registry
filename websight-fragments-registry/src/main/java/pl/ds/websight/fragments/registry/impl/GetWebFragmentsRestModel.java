package pl.ds.websight.fragments.registry.impl;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import pl.ds.websight.request.parameters.support.annotations.RequestParameter;

import javax.validation.constraints.NotNull;

@Model(adaptables = SlingHttpServletRequest.class)
public class GetWebFragmentsRestModel {

    @Self
    private SlingHttpServletRequest request;

    @NotNull
    @RequestParameter
    private String key;

    public String getKey() {
        return key;
    }

    public SlingHttpServletRequest getRequest() {
        return request;
    }
}
