package sgf.gateway.utils.propertyeditors;

import org.safehaus.uuid.UUID;
import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;
import sgf.gateway.dao.metadata.*;
import sgf.gateway.dao.security.GroupRepository;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.ScienceKeyword;
import sgf.gateway.model.metadata.Topic;
import sgf.gateway.model.metadata.activities.project.Project;
import sgf.gateway.model.metadata.citation.ResponsibleParty;
import sgf.gateway.model.metadata.citation.ResponsiblePartyRole;
import sgf.gateway.model.security.Group;
import sgf.gateway.utils.text.MicrosoftCharacterCleaner;

public class CustomDataBindingRegistrar implements PropertyEditorRegistrar {

    private final DatasetRepository datasetRepository;
    private final ProjectRepository projectRepository;
    private final TopicRepository topicRepository;
    private final GroupRepository groupRepository;
    private final ResponsiblePartyRepository responsiblePartyRepository;
    private final MetadataRepository metadataRepository;

    public CustomDataBindingRegistrar(DatasetRepository datasetRepository, ProjectRepository projectRepository, TopicRepository topicRepository,
                                      GroupRepository groupRepository, ResponsiblePartyRepository responsiblePartyRepository, MetadataRepository metadataRepository) {

        this.datasetRepository = datasetRepository;
        this.projectRepository = projectRepository;
        this.topicRepository = topicRepository;
        this.groupRepository = groupRepository;
        this.responsiblePartyRepository = responsiblePartyRepository;
        this.metadataRepository = metadataRepository;
    }

    @Override
    public void registerCustomEditors(PropertyEditorRegistry registry) {

        StringPropertyEditor stringPropertyEditor = new StringPropertyEditor(new MicrosoftCharacterCleaner());
        registry.registerCustomEditor(String.class, stringPropertyEditor);

        registry.registerCustomEditor(UUID.class, new UUIDPropertyEditor());
        registry.registerCustomEditor(Dataset.class, new DatasetPropertyEditor(this.datasetRepository));
        registry.registerCustomEditor(Project.class, new ProjectPropertyEditor(this.projectRepository));
        registry.registerCustomEditor(Topic.class, new TopicPropertyEditor(this.topicRepository));
        registry.registerCustomEditor(Group.class, new GroupPropertyEditor(this.groupRepository));

        registry.registerCustomEditor(ResponsiblePartyRole.class, new ResponsiblePartyRolePropertyEditor());
        registry.registerCustomEditor(ScienceKeyword.class, new ScienceKeywordPropertyEditor(this.metadataRepository));
        registry.registerCustomEditor(ResponsibleParty.class, new ResponsiblePartyPropertyEditor(this.responsiblePartyRepository));
    }
}
