package sgf.gateway.web.controllers;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import sgf.gateway.service.feedback.FeedbackRequest;
import sgf.gateway.validation.groups.Data;
import sgf.gateway.validation.groups.Persistence;
import sgf.gateway.validation.groups.Required;
import sgf.gateway.validation.groups.Type;

import javax.validation.GroupSequence;

@GroupSequence({Required.class, Type.class, Data.class, Persistence.class, FeedbackCommand.class})
public class FeedbackCommand implements FeedbackRequest {

    private String name;
    @Email(groups = Data.class, message = "Email Address is not a valid email address")
    private String email;
    private String phone;

    @NotBlank(groups = Required.class, message = "Feedback is required")
    private String feedback;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
