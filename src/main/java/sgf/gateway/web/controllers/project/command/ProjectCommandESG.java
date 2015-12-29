package sgf.gateway.web.controllers.project.command;

import org.hibernate.validator.constraints.NotBlank;
import sgf.gateway.validation.data.TitleCharacters;
import sgf.gateway.validation.groups.Data;
import sgf.gateway.validation.groups.Persistence;
import sgf.gateway.validation.groups.Required;
import sgf.gateway.validation.groups.Type;
import sgf.gateway.validation.persistence.ActivityProjectNameUnique;

import javax.validation.GroupSequence;

@GroupSequence({Required.class, Type.class, Data.class, Persistence.class, ProjectCommandESG.class})
public class ProjectCommandESG {

    @NotBlank(groups = Required.class, message = "Title is required.")
    @TitleCharacters(groups = Data.class, message = "Title may only contain the letters a-z, A-Z, numbers, and the characters: _ - . space ( ) ?")
    @ActivityProjectNameUnique(groups = Persistence.class, message = "Title must be unique.")
    private String title;

    @NotBlank(groups = Required.class, message = "Description is required.")
    private String description;

    public String getTitle() {

        return this.title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public String getDescription() {

        return this.description;
    }

    public void setDescription(String description) {

        this.description = description;
    }
}
