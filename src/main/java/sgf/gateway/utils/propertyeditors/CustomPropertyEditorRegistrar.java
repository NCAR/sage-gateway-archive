package sgf.gateway.utils.propertyeditors;

import org.safehaus.uuid.UUID;
import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;
import sgf.gateway.model.metadata.CadisResolutionType;
import sgf.gateway.model.metadata.DataType;
import sgf.gateway.model.metadata.DatasetProgress;
import sgf.gateway.model.security.GroupDataType;

public class CustomPropertyEditorRegistrar implements PropertyEditorRegistrar {

    @Override
    public void registerCustomEditors(PropertyEditorRegistry registry) {

        registry.registerCustomEditor(UUID.class, new UUIDPropertyEditor());
        registry.registerCustomEditor(DataType.class, new DataTypePropertyEditor());
        registry.registerCustomEditor(DatasetProgress.class, new DatasetProgressPropertyEditor());
        registry.registerCustomEditor(CadisResolutionType.class, new CadisResolutionTypePropertyEditor());
        registry.registerCustomEditor(GroupDataType.class, new GroupDataTypePropertyEditor());
    }

}
