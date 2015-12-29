package sgf.gateway.model.metrics.factory.impl;

import sgf.gateway.dao.security.UserRepository;
import sgf.gateway.model.metrics.FileDownload;
import sgf.gateway.model.security.User;

public class AddUserTransformStep implements TransformStep {

    private final UserRepository userRepository;

    public AddUserTransformStep(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Override
    public FileDownload transform(FileDownload fileDownload) {

        if (fileDownload.getUserOpenId() != null) {
            this.addUserDetails(fileDownload);
        }

        return fileDownload;
    }

    private void addUserDetails(FileDownload fileDownload) {

        User user = this.userRepository.findUserByOpenid(fileDownload.getUserOpenId());

        fileDownload.setUserIdentifier(user.getIdentifier());
        fileDownload.setUserUsername(user.getUserName());
        fileDownload.setUserEmail(user.getEmail());
        fileDownload.setUserFirstName(user.getFirstName());
        fileDownload.setUserLastName(user.getLastName());
    }
}
