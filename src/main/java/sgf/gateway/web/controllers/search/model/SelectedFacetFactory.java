package sgf.gateway.web.controllers.search.model;

import sgf.gateway.search.api.Constraint;
import sgf.gateway.search.api.Constraints;
import sgf.gateway.search.api.Facet;
import sgf.gateway.search.core.ConstraintImpl;
import sgf.gateway.search.core.ConstraintsImpl;
import sgf.gateway.search.core.FacetImpl;

import java.util.List;

public class SelectedFacetFactory {

    public Facet build(final String facetName, List<String> constraintValues) {

        Facet newFacet = new FacetImpl(facetName);

        Constraints constraints = new ConstraintsImpl();

        for (String string : constraintValues) {
            Constraint constraintImpl = new ConstraintImpl(newFacet, string);

            constraints.add(constraintImpl);
        }

        newFacet.setConstraints(constraints);

        return newFacet;
    }
}
