package sgf.gateway.web.controllers.search.model;

import sgf.gateway.search.api.Facet;

import java.util.List;

public class FacetModel {

    private Facet facet;
    private List<ConstraintModel> constraintModels;

    public FacetModel(Facet facet, List<ConstraintModel> constraintModels) {
        this.facet = facet;
        this.constraintModels = constraintModels;
    }

    public String getName() {
        return this.facet.getName();
    }

    public List<ConstraintModel> getConstraints() {
        return this.constraintModels;
    }
}
